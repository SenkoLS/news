package ru.news.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.news.service.MailSender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

@RestController
@RequestMapping("sendEmail")
public class MailEndpoint {

    @Autowired
    private MailSender mailSender;

    @PostMapping
    public HashMap<String, String> sendHtmlEmail(HttpServletRequest request,
                                                 String recipient,
                                                 String subject,
                                                 String approweLink) throws MessagingException, IOException {

        String message = getApproweEmail(approweLink);
        mailSender.send(recipient, subject, approweLink);

        return new HashMap<String, String>() {{
            put("status", "OK");
            put("message", "Почта успешно отправлена!");
        }};
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
        return message.replace("_approwelink_", approweLink);
    }
}