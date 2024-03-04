package sda.catalogue.sdacataloguerestapi.core.CustomResponse;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private HttpStatusCode status;
    private String message;
    private T result;


//    private ApiResponse(HttpStatusCode status, String message, T result) {
//        this.status = status;
//        this.message = message;
//        this.result = result;
//    }
}