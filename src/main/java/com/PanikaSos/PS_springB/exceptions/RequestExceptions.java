package com.PanikaSos.PS_springB.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RequestExceptions extends RuntimeException{

    private HttpStatus httpStatus;

    public RequestExceptions(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus=httpStatus;
    }
}
