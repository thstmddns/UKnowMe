package com.ssafy.uknowme.security.exception;

public class NoMatchedMemberException extends RuntimeException {
    public NoMatchedMemberException() {
        super();
    }

    public NoMatchedMemberException(String message) {
        super(message);
    }
}
