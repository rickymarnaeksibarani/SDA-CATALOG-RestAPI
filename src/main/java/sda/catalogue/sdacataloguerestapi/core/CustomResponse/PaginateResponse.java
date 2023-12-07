package sda.catalogue.sdacataloguerestapi.core.CustomResponse;

import lombok.Getter;

@Getter
public class PaginateResponse<T> {
    private final T data;
    private final int page;
    private final long total;

    public PaginateResponse(T data, int page, long total) {
        this.data = data;
        this.page = page;
        this.total = total;
    }
}
