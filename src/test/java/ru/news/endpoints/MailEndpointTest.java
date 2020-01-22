package ru.news.endpoints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.testng.Assert;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class MailEndpointTest {

    @Mock
    public HttpServletRequest httpServletRequest;
    @InjectMocks
    MailEndpoint mailEndpoint;

    @Test
    public void testSendHtmlEmail() throws IOException, MessagingException {
        //prepare
        MockitoAnnotations.initMocks(this);
        HashMap<String,String> hashMap = new HashMap<String, String>() {{
            put("status","OK");
            put("message","Почта успешно отправлена!");
        }};

        //testing
        Assert.assertEquals(
                mailEndpoint.
                        sendHtmlEmail(httpServletRequest,
                                "helpmedo.ru@gmail.com",
                                "Unit test of MailController",
                                "helpmedo.ru@gmail.com"),hashMap);

    }
}