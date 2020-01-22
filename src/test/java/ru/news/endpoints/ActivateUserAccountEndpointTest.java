package ru.news.endpoints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.testng.Assert;
import ru.news.domains.User;
import ru.news.util.MessageCreator;
import ru.news.repos.UserRepo;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivateUserAccountEndpointTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    ActivateUserAccountEndpoint auac;

    @Test
    public void testActivateUserAccount() {
        //prepare
        MockitoAnnotations.initMocks(this);
        when(userRepo.existsByTokenEquals("test")).thenReturn(Boolean.TRUE);
        User user = new User();
        user.setRegistration_date(ZonedDateTime.now());
        when(userRepo.findByTokenEquals("test")).thenReturn(user);

        //positive case testing
        MessageCreator messageCreator = auac.activateUserAccount("test");
        Assert.assertEquals(messageCreator.getStatus().toString(),"OK");
        System.out.println("\n1 - Positive case is pased");

        //negative case testing
        messageCreator = auac.activateUserAccount("test1");
        Assert.assertEquals(messageCreator.getStatus().toString(),"FAIL");
        System.out.println("1 - Negative case is pased");

        //expired case testing
        user.setRegistration_date(user.getRegistration_date().minusHours(25));
        messageCreator = auac.activateUserAccount("test");
        Assert.assertEquals(messageCreator.getStatus().toString(),"EXPIRED");
        System.out.println("1 - Expired case is pased\n");
    }
}