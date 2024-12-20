package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ZooGlobalExceptionHandler {

    // ZooException yakalandığında çalışır
    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZooException(ZooException ex) {
        log.error("ZooException caught: {}", ex.getMessage());
        ZooErrorResponse errorResponse = new ZooErrorResponse(
                ex.getMessage(),
                ex.getHttpStatus().value(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    // Genel Exception yakalandığında çalışır
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleGeneralException(Exception ex) {
        log.error("General exception caught: {}", ex.getMessage());
        ZooErrorResponse errorResponse = new ZooErrorResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
