package ru.yandex.practicum.catsgram.model;

public class ErrorResponse {
    String error;

    public ErrorResponse(String parameter) {
        this.error = parameter;
    }

    public String getError() {
        return error;
    }

    public ErrorResponse setError(String error) {
        this.error = error;
        return this;
    }
}
