package ru.news.endpoints;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;

@RestController
@RequestMapping("sendEmail")
public class MailEndpoint {
    private JavaMailSender emailSender = getJavaMailSender();

    /**
     * Обработчик POST - отправка электронного письма
     * REST используется в системе для отправки писем пользователям
     * Технический долг - доработать метод метрикой разделения логики для разных типов писем
     *
     * @param request     - текущий http request
     * @param recipient   - адрес email получателя
     * @param subject     - тема письма
     * @param approweLink - ссылка на подтверждение аккаунта для email
     * @return возвращает статус отправки электронного письма (JSON)
     * @throws MessagingException
     * @throws IOException
     */
    @PostMapping
    public HashMap<String, String> sendHtmlEmail(HttpServletRequest request,
                                                 String recipient,
                                                 String subject,
                                                 String approweLink) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        String htmlMsg = getApproweEmail(approweLink);
        message.setContent(htmlMsg, "text/html; charset=utf-8");
        helper.setTo(recipient);
        helper.setSubject(subject);
        this.emailSender.send(message);
        return new HashMap<String, String>() {{
            put("status","OK");
            put("message","Почта успешно отправлена!");
        }};
    }

    /**
     * Получение JavaMail обработчика с параметрами учетной записи отправителя (временное решение)
     * Технический долг - перенести захардкоженные параметры доступа к почтовому серверу в файл параметров
     *
     * @return возвращает сконфигурированный JavaMailSender
     */
    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("helpmedo.ru@gmail.com");
        mailSender.setPassword("322-322-322-322");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.socketFactory.port","587");
        props.put("mail.smtp.socketFactory.fallback","false");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.starttls.required", "true");
        return mailSender;
    }

    /**
     * Получение заготовки письма из файла ресурсов
     *
     * @param approweLink - ссылка подтверждения учетной записи
     * @return возвращает строку html для вставки в email
     * @throws IOException
     */
    private String getApproweEmail(String approweLink) throws IOException {
        File resource = new ClassPathResource("templates/approweEmail.html").getFile();
        String message = new String(Files.readAllBytes(resource.toPath()));
        return  message.replace("_approwelink_", approweLink);
    }
}