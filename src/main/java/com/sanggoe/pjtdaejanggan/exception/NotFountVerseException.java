package com.sanggoe.pjtdaejanggan.exception;

public class NotFountVerseException extends RuntimeException {
    public NotFountVerseException() {
        super();
    }
    public NotFountVerseException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFountVerseException(String message) {
        super(message);
    }
    public NotFountVerseException(Throwable cause) {
        super(cause);
    }
}