package ru.news.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.news.domains.User;
import ru.news.util.MessageCreator;
import ru.news.repos.UserRepo;

import javax.websocket.server.PathParam;
import java.time.ZonedDateTime;

/**
 * Рест активации учетной записи
 */
@RestController
@RequestMapping("activate")
public class ActivateUserAccountEndpoint {
    @Autowired
    private UserRepo userRepo;

    /**
     * Проверка токена учетной записи
     *
     * @param token - значение токена из ссылки отправленной по email
     * @return Возвращает статус активации: "OK" - успешная активация, "FAIL" - ошибка активации, "EXPIRED" - просрочена учетная запись
     */
    @GetMapping
    public MessageCreator activateUserAccount(@PathParam("token") String token) {
        if (userRepo.existsByTokenEquals(token)) {
            User user = userRepo.findByTokenEquals(token);
            if (compareCurrentZonedDateTime(user)) {
                user.setActive(1);
                userRepo.save(user);
                return new MessageCreator(MessageCreator.Status.OK,
                        "Здравствуйте, " + user.getFirst_name() + " " +
                                user.getLast_name() + "! Ваша учетная запись активирована.");
            } else {
                return new MessageCreator(MessageCreator.Status.EXPIRED,
                        "Здравствуйте! Ссылка подтверждения была просрочена. Вы можете запросить новую.");
            }
        } else {
            return new MessageCreator(MessageCreator.Status.FAIL,
                    "Пользователь не зарегистрирован!");
        }
    }

    /**
     * Проверка просроченности учетной записи
     *
     * @param user - Объект с данными пользователя
     * @return Возвращает true, если учетная запись не просрочена (с даты регистрации не прошло более 24 часов)
     */
    private boolean compareCurrentZonedDateTime(User user) {
        ZonedDateTime currentZoneDateTime = ZonedDateTime.now();
        return (user.getRegistration_date().plusHours(24)).isAfter(currentZoneDateTime) ? true : false;
    }
}
