package ru.news.exceptions;

public class PaginationError extends RuntimeException {
    private String message;

    public PaginationError(String errorMessage) {
        message = errorMessage;
    }

    @Override
    public String toString() {
        return "PaginationError{" +
                "message='" + message + '\'' +
                '}';
    }
}
