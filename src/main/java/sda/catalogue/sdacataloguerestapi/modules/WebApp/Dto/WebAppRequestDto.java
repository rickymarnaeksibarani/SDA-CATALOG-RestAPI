package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Dto.DocumentUploadDTO;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebAppRequestDto {

    private Integer page;

    private Integer size;

    private String searchTerm;


    private long idWebApp;
    private String applicationName;
//    private String role;
    private SapIntegration sapIntegration;
    private String categoryApp;
    private String description;
    private String functionApplication;
    private String address;
    private BusinessImpactPriority businessImpactPriority;
    private Status status;
    private String applicationSourceFe;
    private String applicationSourceBe;
    private String ipDatabase;
    private Long sdaHostingEntity;
    private List<Status> filterByStatus;
    private List<DocumentDto> documentation;
}
