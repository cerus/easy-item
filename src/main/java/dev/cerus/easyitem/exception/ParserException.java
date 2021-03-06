package dev.cerus.easyitem.exception;

public class ParserException extends Exception {

    public ParserException() {
    }

    public ParserException(final String message) {
        super(message);
    }

    public ParserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParserException(final Throwable cause) {
        super(cause);
    }

    public ParserException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
