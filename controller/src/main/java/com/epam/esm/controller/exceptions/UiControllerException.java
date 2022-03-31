package com.epam.esm.controller.exceptions;

public class UiControllerException extends Exception{
    public UiControllerException() {
        super();
    }

    public UiControllerException(String message) {
        super(message);
    }

    public UiControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UiControllerException(Throwable cause) {
        super(cause);
    }
}
