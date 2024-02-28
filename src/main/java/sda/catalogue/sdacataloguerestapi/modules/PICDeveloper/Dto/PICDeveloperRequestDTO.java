package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto;

import lombok.Data;

@Data
public class PICDeveloperRequestDTO {
    private Integer page;

    private Integer size;

    PICDeveloperRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
}
