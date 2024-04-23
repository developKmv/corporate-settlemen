
package ru.study.corporatesettlemen.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.study.corporatesettlemen.api.ErrorMessageType;
import ru.study.corporatesettlemen.exception.ServiceException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {ServiceException.class})
    protected ResponseEntity<Object> apiError(RuntimeException ex, WebRequest request) {
        ErrorMessageType emt = new ErrorMessageType();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex instanceof ServiceException){
            ServiceException sexp = (ServiceException) ex;
            emt.setMessages(List.of(sexp.getMessage()));
            switch (sexp.getHttpMethodType()){
                case "Bad_Request":
                    status = HttpStatus.BAD_REQUEST;
                    break;
                case "Not_Found":
                    status = HttpStatus.NOT_FOUND;
                    break;
            }
        }

        return handleExceptionInternal(ex, emt,
                new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorMessageType emt = new ErrorMessageType();
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        emt.setMessages(Arrays.stream(ex.getDetailMessageArguments()).filter(o -> !o.toString().equals("")).collect(Collectors.toList()));
        return this.handleExceptionInternal(ex, emt, headers, HttpStatus.BAD_REQUEST, request);
    }
}
