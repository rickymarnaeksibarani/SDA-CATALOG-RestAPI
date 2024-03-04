package sda.catalogue.sdacataloguerestapi.core;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> constrainViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<String>builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<String>> apiException(ResponseStatusException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(ApiResponse.<String>builder()
                        .message(exception.getReason())
                        .status(exception.getStatusCode())
                        .build());
    }
}
