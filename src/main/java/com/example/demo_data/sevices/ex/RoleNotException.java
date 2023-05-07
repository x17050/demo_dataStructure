package com.example.demo_data.sevices.ex;

public class RoleNotException extends ServicesException{
    public RoleNotException() {
        super();
    }

    public RoleNotException(String message) {
        super(message);
    }

    public RoleNotException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotException(Throwable cause) {
        super(cause);
    }

    protected RoleNotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
