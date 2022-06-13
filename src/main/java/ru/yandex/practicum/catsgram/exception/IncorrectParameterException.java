package ru.yandex.practicum.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {
    String parameter;

    public IncorrectParameterException(String parameter, String message) {
        super(message);
        this.parameter = parameter;
    }
}
