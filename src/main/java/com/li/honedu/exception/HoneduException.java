package com.li.honedu.exception;

public class HoneduException extends RuntimeException {
    public HoneduException() {

    }

    public HoneduException(String message) {
        super(message);
    }
    public HoneduException(Throwable cause) {
        super(cause);
    }
    public HoneduException(String message, Throwable cause) {
        super(message, cause);
    }

}
