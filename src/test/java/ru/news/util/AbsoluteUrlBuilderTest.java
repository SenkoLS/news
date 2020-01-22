package ru.news.util;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.Assert;


public class AbsoluteUrlBuilderTest {
    private String testURL = "/main";

    @Test
    public void testGetUrl() {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setRequestURI(testURL);
        httpServletRequest.setServerPort(8080);
        Assert.assertEquals(AbsoluteUrlBuilder.getUrl(httpServletRequest, "/control"),
                "http://localhost:8080/control");
    }
}