package sda.catalogue.sdacataloguerestapi.core.CustomResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private HttpStatusCode status;
    private String message;
    private T result;
    private PagingResponse paging;

    public ApiResponse(HttpStatusCode status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}