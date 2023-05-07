package com.example.demo_data.sevices.ex;

public class NoTeacherException extends ServicesException{
    public NoTeacherException() {
        super();
    }

    public NoTeacherException(String message) {
        super(message);
    }

    public NoTeacherException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTeacherException(Throwable cause) {
        super(cause);
    }

    protected NoTeacherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
