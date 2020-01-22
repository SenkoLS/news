package ru.news.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class StartPageController {
    @GetMapping("/")
    public void getStartPageEmpty(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse, Model model) throws IOException {
        if (httpServletRequest.getSession().getAttribute("user" +
                httpServletRequest.getSession().getId()) != null) {
            //httpServletResponse.sendRedirect(httpServletRequest.getRequestURL() + "main");
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURL() + "adminpanel");
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURL() + "hello");
        }
    }
}
