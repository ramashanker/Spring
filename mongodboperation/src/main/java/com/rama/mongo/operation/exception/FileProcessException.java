package com.rama.mongo.operation.exception;

public class FileProcessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileProcessException() {
    }

    public FileProcessException(final String message) {
        super(message);
    }

    public FileProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileProcessException(final Throwable cause) {
        super(cause);
    }

    protected FileProcessException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
