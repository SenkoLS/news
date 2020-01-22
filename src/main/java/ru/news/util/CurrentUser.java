package ru.news.util;

import ru.news.domains.User;

import javax.servlet.http.HttpServletRequest;

public class CurrentUser {

    /**
     * Getting current user
     *
     * @param httpServletRequest - current HttpRequest
     * @return current user
     */
    public static User getCurrentUser(HttpServletRequest httpServletRequest) {
        String userSessionAttr = "user" + httpServletRequest.getSession().getId();
        Object object = httpServletRequest.getSession().getAttribute(userSessionAttr);
        if (object instanceof User) {
            return (User) object;
        } else {
            return null;
        }
    }

    /**
     * Checking credentials the current user
     *
     * @param httpServletRequest - current HttpRequest
     * @param idUser             - the user id to be checked
     * @return true, if current user id equals idUser
     */
    public static Boolean checkIdUser(HttpServletRequest httpServletRequest, Long idUser) {
        return getCurrentUser(httpServletRequest).getId_user().equals(idUser);
    }
}
