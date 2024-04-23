package ru.study.corporatesettlemen.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private String HttpMethodType;
    public ServiceException(String errorMessage, Throwable err){
        super(errorMessage, err);
}

}