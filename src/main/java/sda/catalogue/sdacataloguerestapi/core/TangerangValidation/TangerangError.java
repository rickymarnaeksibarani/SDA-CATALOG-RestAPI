package sda.catalogue.sdacataloguerestapi.core.TangerangValidation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TangerangError {
    private final HttpStatusCode status;
    private final String message;
    private final Map<String, List<String>> errors;

    public TangerangError(HttpStatusCode status, String message, Map<String, List<String>> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
