package org.donggle.backend.ui.common;

import lombok.extern.slf4j.Slf4j;
import org.donggle.backend.exception.authentication.AuthenticationException;
import org.donggle.backend.exception.business.BusinessException;
import org.donggle.backend.exception.notfound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(final AuthenticationException e) {
        log.warn("Exception from handleAuthenticationException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException e) {
        log.warn("Exception from handleNotFoundException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.warn("Exception from handleBusinessException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(final IOException e) {
        log.warn("Exception from handleIOException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse("잘못된 파일 입력입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("Exception from handleMethodArgumentNotValidException = ", e);
        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            final String fieldName = ((FieldError) error).getField();
            final String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.warn("Exception from handleMissingServletRequestParameterException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse(
                "잘못된 요청입니다. 누락된 쿼리 파라미터 타입: " + e.getParameterName() +
                        "예상되는 쿼리 파라미터 타입: " + e.getParameterType()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.warn("Exception from handleHttpRequestMethodNotSupportedException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse("잘못된 요청입니다. HTTP 메서드를 다시 확인해주세요. 입력한 HTTP 메서드: " + e.getMethod());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(errorResponse);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleMissingPathVariableException(final MissingPathVariableException e) {
        log.warn("Exception from handleMissingPathVariableException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse("잘못된 요청입니다. 누락된 경로 변수: " + e.getVariableName());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.warn("Exception from handleMethodArgumentTypeMismatchException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse(
                "잘못된 요청입니다. 잘못된 변수: " + e.getName() +
                        "예상되는 변수 타입: " + e.getRequiredType()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.warn("Exception from handleHttpMessageNotReadableException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse("잘못된 요청입니다. 요청 바디를 다시 확인해주세요.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnExpectedException(final Exception e) {
        log.error("Exception from handleUnExpectedException = ", e);
        final ErrorResponse errorResponse = new ErrorResponse("서버에서 예상치 못한 문제가 발생하였습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
