package com.PanikaSos.PS_springB.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ServiceExceptions extends Exception{

    private HttpStatus httpStatus;
    public ServiceExceptions(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
