package sda.catalogue.sdacataloguerestapi.core.TangerangValidation;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.ExceptionResponse;

@Getter
@Setter
public class TangerangRequestException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(TangerangRequestException.class);
    private final HttpStatus status;


    public TangerangRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ResponseEntity<ExceptionResponse> GlobalTangerangRequestException(String message, HttpStatus status) {
        logger.error("An error occurred: {}", getMessage());
        return new ResponseEntity<>(new ExceptionResponse(status, message), status);
    }
}
