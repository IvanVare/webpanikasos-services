package com.PanikaSos.PS_springB.exceptions;

import com.PanikaSos.PS_springB.exceptions.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RequestExceptions.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestExceptions exception){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(exception.getMessage())
                .status(exception.getHttpStatus())
                .build();
        return new ResponseEntity<>(errorDTO, exception.getHttpStatus());
    }

    @ExceptionHandler(value = ServiceExceptions.class)
    public ResponseEntity<ErrorDTO> serviceExceptionHandler(ServiceExceptions exceptions){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(exceptions.getMessage())
                .status(exceptions.getHttpStatus())
                .build();
        return new ResponseEntity<>(errorDTO,exceptions.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {stringObjectMap.put(fieldError.getField(),fieldError.getDefaultMessage());});
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stringObjectMap);
    }





}
