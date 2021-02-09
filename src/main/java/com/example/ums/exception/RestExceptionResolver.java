package com.example.ums.exception;

import com.example.ums.model.Response;
import com.example.ums.model.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UmsException.class})
    protected ResponseEntity<? extends Response> handleException(UmsException e, WebRequest request) {
        return new ResponseEntity<>(new ResponseBean<>(false, String.valueOf(e.getHttpStatus().value()), e.getMessage(), e.getData()), e.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<? extends Response> handleUnknownException(Exception e, WebRequest request) {
        return new ResponseEntity<>(new ResponseBean<>(false, "500", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
