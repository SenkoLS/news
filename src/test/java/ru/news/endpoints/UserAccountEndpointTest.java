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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountEndpointTest {
    //prepare
    @Mock
    private UserRepo userRepo;

    @Mock
    private User currentUser;
    @InjectMocks
    UserAccountEndpoint uac;

    @Test
    public void getUser(){

    }

    @Test
    public void updateUserInfo() {
    }

    @Test
    public void deleteUser() {
        //positive case testing
        when(userRepo.deleteByLoginEquals(currentUser.getLogin())).thenReturn(Boolean.TRUE);
        MessageCreator messageCreator = uac.deleteUser();
        Assert.assertEquals(messageCreator.getStatus().toString(),"OK");
        System.out.println("\n1 - Positive case of deleteUser is passed");

        //negative case testing
        when(userRepo.deleteByLoginEquals(currentUser.getLogin())).thenReturn(Boolean.FALSE);
        messageCreator = uac.deleteUser();
        Assert.assertEquals(messageCreator.getStatus().toString(),"FAIL");
        System.out.println("2 - Negative case of deleteUser is passed\n");
    }

    private void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
}