package com.ryzendee.cardservice.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() {
        super();
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
