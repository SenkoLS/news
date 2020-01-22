package ru.news.exceptions;

public class CurrentUserError extends RuntimeException {
    private String message;

    public CurrentUserError(String errorMessage) {
        message = errorMessage;
    }

    @Override
    public String toString() {
        return "CurrentUserError{" +
                "message='" + message + '\'' +
                '}';
    }
}
