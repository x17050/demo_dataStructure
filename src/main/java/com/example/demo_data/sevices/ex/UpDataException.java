package com.example.demo_data.sevices.ex;

public class UpDataException extends ServicesException{
    public UpDataException() {
        super();
    }

    public UpDataException(String message) {
        super(message);
    }

    public UpDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpDataException(Throwable cause) {
        super(cause);
    }

    protected UpDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
