package ru.news.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.news.domains.User;
import ru.news.domains.dto.CaptchaResponceDto;
import ru.news.domains.dto.EmailResponseDto;
import ru.news.util.MessageCreator;
import ru.news.repos.UserRepo;
import ru.news.util.AbsoluteUrlBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST регистрации учетной записи пользователя
 */
@RestController
@RequestMapping("/registration")
public class UserRegistrationEndpoint {
    @Autowired
    private UserRepo userRepo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.active}")
    private int recaptchaActive;

    //Отправитель запросов на REST
    @Autowired
    private RestTemplate restTemplate;

    //REST проверки google recaptcha
    private final static String CAPTCHA_URL =
            "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @PostMapping
    public MessageCreator registerUser(HttpServletRequest httpRequest,
                                       String userLogin,
                                       String userPassword,
                                       String firstName,
                                       String lastName,
                                       @RequestParam("g-recaptcha-response") String captchaResponce
    ) throws IOException {

        //Проверка google recaptcha, если 1, то используем капчу, если нет, то не используем (настройка в проперти)
        if (recaptchaActive == 1) {
            String url = String.format(CAPTCHA_URL, secret, captchaResponce);
            CaptchaResponceDto responceDto =
                    restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponceDto.class);
            if (!responceDto.isSuccess()) {
                return new MessageCreator(MessageCreator.Status.FAIL,
                        "Капча не прошла проверку!");
            }
        }

        //Если пользователя нет в базе, то сохраняем в базу и отправляем письмо на почту
        if (!userRepo.existsByLoginEquals(userLogin)) {
            User user = new User(userLogin, passwordEncoder().encode(userPassword), firstName, lastName,
                    0, passwordEncoder().encode(userLogin), ZonedDateTime.now());

            //Отправка почты для подтверждения аккаунта
            if (sendMail(httpRequest, user.getLogin(), user.getToken())) {
                //Сохранение пользователя в БД
                userRepo.save(user);
                return new MessageCreator(MessageCreator.Status.OK,
                        "Регистрация прошла успешно! Проверьте вашу электронную почту и подтвердите регистрацию.");
            } else {
                return new MessageCreator(MessageCreator.Status.FAIL,
                        "Ошибка отправки электронной почты для подтверждения учетной записи.");
            }
        } else {
            return new MessageCreator(MessageCreator.Status.FAIL,
                    "Данный логин зарегистрирован в системе!");
        }
    }

    /**
     * Отправка данных на REST отправки почты (sendEmail)
     *
     * @param httpRequest HTTP-запрос для получения URL и URI
     * @param recipient   Адрес почты получателя
     * @param token       Токен для ссылки подтверждения
     * @return Возвращает ответ от RESR (мапится JSON)
     * @throws IOException
     */
    private boolean sendMail(HttpServletRequest httpRequest, String recipient, String token) throws IOException {
        //Формируем ссылку на подтверждение аккаунта
        String approweLink = AbsoluteUrlBuilder.getUrl(httpRequest, "/activate?token=" + token);

        //Формируем URI REST sendEmail
        String uriRestEmail = "/sendEmail?recipient=" +
                recipient + "&subject=" +
                new String("Подтверждение учетной записи NEWS".getBytes(), "UTF-8") +
                "&approweLink=" + approweLink;

        //Формируем URL REST sendEmail
        String urlRestEmail = AbsoluteUrlBuilder.getUrl(httpRequest, uriRestEmail);

        //Отправляем запрос на REST sendEmail
        EmailResponseDto messageResponseFromEmail =
                restTemplate
                        .postForObject(urlRestEmail, Collections.emptyList(), EmailResponseDto.class);

        //Проверка ответа от REST sendEmail
        return messageResponseFromEmail.getStatus().equals("OK") ? true : false;
    }
}
