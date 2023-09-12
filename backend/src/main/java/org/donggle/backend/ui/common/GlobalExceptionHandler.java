package org.donggle.backend.ui.common;

import lombok.extern.slf4j.Slf4j;
import org.donggle.backend.application.service.vendor.exception.VendorApiException;
import org.donggle.backend.exception.authentication.UnAuthenticationException;
import org.donggle.backend.exception.business.BusinessException;
import org.donggle.backend.exception.notfound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorWrapper> handleAuthenticationException(final UnAuthenticationException e) {
        log.warn("Exception from handleAuthenticationException = ", e);
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(e.getMessage(), e.getHint(), e.getErrorCode())
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorWrapper);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorWrapper> handleNotFoundException(final NotFoundException e) {
        log.warn("Exception from handleNotFoundException = ", e);
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(e.getMessage(), e.getHint(), e.getErrorCode())
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorWrapper);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorWrapper> handleBusinessException(final BusinessException e) {
        log.warn("Exception from handleBusinessException = ", e);
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(e.getMessage(), e.getHint(), e.getErrorCode())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorWrapper> handleIOException(final IOException e) {
        log.warn("Exception from handleIOException = ", e);
        final String hint = "파일을 읽는 데에 문제가 발생했습니다.";
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorWrapper> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("Exception from handleMethodArgumentNotValidException = ", e);
        final String errorFields = e.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField())
                .collect(Collectors.joining(", "));
        final String hint = "요청 바디가 잘못되었습니다. 요청 바디 필드를 다시 확인해주세요.";
        final ErrorWrapper errors = new ErrorWrapper(
                ErrorContent.of(errorFields, hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorWrapper> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.warn("Exception from handleMissingServletRequestParameterException = ", e);
        final String hint = "잘못된 요청입니다. 누락된 쿼리 파라미터 타입: " + e.getParameterName() +
                "예상되는 쿼리 파라미터 타입: " + e.getParameterType();
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorWrapper> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.warn("Exception from handleHttpRequestMethodNotSupportedException = ", e);
        final String hint = "잘못된 요청입니다. HTTP 메서드를 다시 확인해주세요. 입력한 HTTP 메서드: " + e.getMethod();
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.METHOD_NOT_ALLOWED.value())
        );
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(errorWrapper);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorWrapper> handleMissingPathVariableException(final MissingPathVariableException e) {
        log.warn("Exception from handleMissingPathVariableException = ", e);
        final String hint = "잘못된 요청입니다. 누락된 경로 변수: " + e.getVariableName();
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorWrapper> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.warn("Exception from handleMethodArgumentTypeMismatchException = ", e);
        final String hint = "잘못된 요청입니다. 잘못된 변수: " + e.getName() + "예상되는 변수 타입: " + e.getRequiredType();
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorWrapper> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.warn("Exception from handleHttpMessageNotReadableException = ", e);
        final String hint = "잘못된 요청입니다. 요청 바디를 다시 확인해주세요.";
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorWrapper> handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e) {
        log.warn("Exception from handleHttpMediaTypeNotSupportedException = ", e);
        final String hint = "잘못된 요청입니다. 지원하지 않는 미디어 타입입니다. 지원되는 미디어 타입: " + e.getSupportedMediaTypes();
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.BAD_REQUEST.value())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorWrapper);
    }

    @ExceptionHandler(VendorApiException.class)
    public ResponseEntity<ErrorWrapper> handleVendorApiException(final VendorApiException e) {
        log.warn("Exception from handleVendorApiException = ", e);
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(e.getMessage(), e.getHint(), e.getErrorCode())
        );
        return ResponseEntity
                .status(e.getErrorCode())
                .body(errorWrapper);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorWrapper> handleUnExpectedException(final Exception e) {
        log.error("Exception from handleUnExpectedException = ", e);
        final String hint = "서버에서 예상치 못한 문제가 발생하였습니다.";
        final ErrorWrapper errorWrapper = new ErrorWrapper(
                ErrorContent.of(hint, HttpStatus.INTERNAL_SERVER_ERROR.value())
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorWrapper);
    }
}
