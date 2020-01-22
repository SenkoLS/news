package ru.news.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCreator {
    private Status status;
    private String message;

    public MessageCreator(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static enum Status {
        OK,
        FAIL,
        EXPIRED
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
}
