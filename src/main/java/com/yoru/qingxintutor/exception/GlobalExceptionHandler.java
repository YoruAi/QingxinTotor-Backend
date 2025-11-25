package com.yoru.qingxintutor.exception;

import com.yoru.qingxintutor.pojo.ApiResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.FileNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 自定义抛出或业务逻辑的参数校验错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, BusinessException.class})
    public ApiResult<Void> handleIllegalArgument(Exception e) {
        log.warn("Request with invalid argument: {}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }

    // 请求参数校验错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Void> handleValidationErrors(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Invalid input");
        log.warn("Request with invalid POST argument: {}", errorMessage);
        return ApiResult.error(errorMessage);
    }

    // 路径变量或请求参数校验错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<Void> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Invalid request parameter");
        log.warn("Constraint violation: {}", message);
        return ApiResult.error(message);
    }

    // 处理参数类型转换错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult<?> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String name = e.getName();
        String requiredType = e.getRequiredType().getSimpleName();
        log.warn("Request with invalid argument type: {}", e.getMessage());
        return ApiResult.error("Parameter '" + name + "' must be a valid " + requiredType);
    }

    // 处理其他绑定错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResult<?> handleBindException(BindException e) {
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        log.warn("Request with invalid binding argument: {}", e.getMessage());
        return ApiResult.error(errorMessage);
    }

    // 处理请求方法错误
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("Request with invalid method: {}", e.getMessage());
        return ApiResult.error(e.getMessage());
    }

    // 处理资源错误
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult<Void> handleNotFound(NoHandlerFoundException e) {
        log.warn("Request invalid url: {}", e.getMessage());
        return ApiResult.error("Request URL not exists");
    }

    // 处理资源错误
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({FileNotFoundException.class, NoResourceFoundException.class})
    public ApiResult<Void> handleFileNotFoundErrors(Exception e) {
        log.warn("Request invalid resource: {}", e.getMessage());
        return ApiResult.error("Resource not exists");
    }

    // 处理未知错误
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handleGeneric(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        return ApiResult.error("An unexpected error occurred! Please contact the administrator.");
    }
}