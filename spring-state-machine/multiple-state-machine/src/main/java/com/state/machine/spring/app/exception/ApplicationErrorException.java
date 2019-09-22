package com.state.machine.spring.app.exception;

public class ApplicationErrorException extends HandledException {
    private static final long serialVersionUID = 1L;

    public ApplicationErrorException(String message) {
        super(message);
    }

    public ApplicationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationErrorException(String messageformat, Object... messageFormatArguments) {
        super(messageformat, messageFormatArguments);
    }
}
