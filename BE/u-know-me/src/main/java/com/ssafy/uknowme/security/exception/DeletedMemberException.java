package com.ssafy.uknowme.security.exception;

import org.springframework.security.core.AuthenticationException;

public class DeletedMemberException extends AuthenticationException {

    public DeletedMemberException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DeletedMemberException(String msg) {
        super(msg);
    }
}
