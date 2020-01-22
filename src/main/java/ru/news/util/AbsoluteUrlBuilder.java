package ru.news.util;

import javax.servlet.http.HttpServletRequest;

public class AbsoluteUrlBuilder {
    public static String getUrl(HttpServletRequest httpRequest, String uri) {
        String url = String.valueOf(httpRequest.getRequestURL());
        String uri_ = String.valueOf(httpRequest.getRequestURI());
        return url.replace(uri_, uri);
    }
}
