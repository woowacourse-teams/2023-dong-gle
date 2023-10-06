package org.donggle.backend.exception.notfound;

import org.donggle.backend.exception.authentication.UnAuthenticationException;

public class AuthorizationHeaderNotFoundException extends UnAuthenticationException {
    public AuthorizationHeaderNotFoundException() {
        super(null);
    }

    @Override
    public String getHint() {
        return "Authorization 해더값이 존재하지 않습니다.";
    }
}
