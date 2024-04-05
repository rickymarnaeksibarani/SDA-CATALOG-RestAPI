package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterRequest {
    private String searchTerm;

    @Enumerated(EnumType.STRING)
    private List<Status> filterByStatus;
}
