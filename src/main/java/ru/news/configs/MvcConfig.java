package ru.news.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //Spring обработчик REST запросов
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/hello").setViewName("startpage");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/reg").setViewName("reg");
    }
}
