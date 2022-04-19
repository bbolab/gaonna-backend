package com.bbolab.gaonna.api.security.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2ProviderNotMatchingException extends AuthenticationException {
    public OAuth2ProviderNotMatchingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2ProviderNotMatchingException(String msg) {
        super(msg);
    }
}
