package org.donggle.backend.auth.exception;

import org.donggle.backend.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends BusinessException {
    public RefreshTokenNotFoundException() {
        super(null);
    }
    
    public RefreshTokenNotFoundException(final Throwable cause) {
        super(null, cause);
    }
    
    @Override
    public String getHint() {
        return "존재하지 않는 토큰입니다. 다시 로그인을 진행하세요.";
    }
    
    @Override
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
