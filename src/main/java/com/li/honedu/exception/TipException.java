package com.li.honedu.exception;

public class TipException extends HoneduException{
    public TipException() {

    }

    public TipException(String message) {
        super(message);
    }
    public TipException(Throwable cause) {
        super(cause);
    }
    public TipException(String message, Throwable cause) {
        super(message, cause);
    }
}
