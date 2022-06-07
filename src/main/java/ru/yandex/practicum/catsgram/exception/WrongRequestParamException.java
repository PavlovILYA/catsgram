package ru.yandex.practicum.catsgram.exception;

public class WrongRequestParamException extends RuntimeException {
    public WrongRequestParamException() {
        super();
    }

    public WrongRequestParamException(String message) {
        super(message);
    }
}
