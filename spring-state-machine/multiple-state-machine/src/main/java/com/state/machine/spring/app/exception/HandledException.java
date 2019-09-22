package com.state.machine.spring.app.exception;

import java.util.Arrays;

public abstract class HandledException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HandledException(String message) {
        super(message);
    }

    public HandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandledException(String messageformat, Object... messageFormatArguments) {
        super(formatMessage(messageformat, messageFormatArguments), getCause(messageFormatArguments));
    }

    private static String formatMessage(String messageformat, Object... messageFormatArguments) {
        if (messageFormatArguments.length == 0) {
            return messageformat;
        } else {
            boolean lastArgumentIsException = messageFormatArguments[messageFormatArguments.length - 1] instanceof Exception;
            if (messageFormatArguments.length == 1 && lastArgumentIsException) {
                return messageformat;
            } else if (!lastArgumentIsException) {
                return String.format(messageformat, messageFormatArguments);
            } else {
                Object[] args = Arrays.copyOfRange(messageFormatArguments, 0, messageFormatArguments.length - 1);
                return String.format(messageformat, args);
            }
        }
    }

    private static Exception getCause(Object... messageFormatArguments) {
        if (messageFormatArguments.length == 0) {
            return null;
        } else {
            boolean lastArgumentIsException = messageFormatArguments[messageFormatArguments.length - 1] instanceof Exception;
            return lastArgumentIsException ? (Exception)messageFormatArguments[messageFormatArguments.length - 1] : null;
        }
    }
}
