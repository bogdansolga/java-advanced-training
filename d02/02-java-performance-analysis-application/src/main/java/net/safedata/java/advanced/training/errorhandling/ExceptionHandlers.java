package net.safedata.java.advanced.training.errorhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleRuntimeException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest()
                             .body("Something happened");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUglyExceptions(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.internalServerError()
                             .body("Oops");
    }
}
