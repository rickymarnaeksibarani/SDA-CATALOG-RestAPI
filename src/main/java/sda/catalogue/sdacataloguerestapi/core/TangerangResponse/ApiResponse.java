package sda.catalogue.sdacataloguerestapi.core.TangerangResponse;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiResponse<T> {
    private final HttpStatusCode status;
    private final String message;
    private final T result;


    public ApiResponse(HttpStatusCode status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}