package com.devsuperior.bds02.controller.exception;

import com.devsuperior.bds02.service.exception.DatabaseException;
import com.devsuperior.bds02.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandle {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErro> ResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                  HttpServletRequest httpServletRequest) {

        StandardErro standardErro = new StandardErro();

        standardErro.setTimestamp(Instant.now());
        standardErro.setStatus(HttpStatus.NOT_FOUND.value());
        standardErro.setError("Controller not found");
        standardErro.setMessage(resourceNotFoundException.getMessage());
        standardErro.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(404).body(standardErro);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardErro> databaseBadRequest(DatabaseException databaseException,
                                                           HttpServletRequest httpServletRequest) {
        StandardErro standardErro = new StandardErro();

        standardErro.setTimestamp(Instant.now());
        standardErro.setStatus(HttpStatus.BAD_REQUEST.value());
        standardErro.setError("Database exception");
        standardErro.setMessage(databaseException.getMessage());
        standardErro.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(400).body(standardErro);
    }
}
