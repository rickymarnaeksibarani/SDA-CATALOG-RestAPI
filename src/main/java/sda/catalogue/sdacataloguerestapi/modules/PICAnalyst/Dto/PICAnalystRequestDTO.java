package sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Dto;

import lombok.Data;

@Data
public class PICAnalystRequestDTO {
    private Integer page;
    private String searchTerm;
    private Integer size;

    PICAnalystRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
}
