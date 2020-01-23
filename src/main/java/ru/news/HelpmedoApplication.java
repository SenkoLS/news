package ru.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

@SpringBootApplication
public class HelpmedoApplication {
    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(HelpmedoApplication.class, args);
    }
}

