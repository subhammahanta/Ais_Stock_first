package com.watsoo.device.management.exception;

import com.watsoo.device.management.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Create a Response object with appropriate status code and error message
          Response<?> response=new Response<>(HttpStatus.NOT_FOUND.value(),"ICCID not Found");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}
