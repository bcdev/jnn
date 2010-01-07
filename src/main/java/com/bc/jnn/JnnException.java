package com.bc.jnn;

public class JnnException extends Exception {
    public JnnException(String message) {
        super(message);
    }

    public JnnException(String message, Throwable cause) {
        super(message, cause);
    }
}
