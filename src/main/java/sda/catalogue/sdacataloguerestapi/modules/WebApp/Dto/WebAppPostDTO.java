package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebAppPostDTO {
    @NotNull
    @NotEmpty
    private String applicationName;

    @NotNull
    @NotEmpty
    private String categoryApp;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String functionApplication;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String businessImpactPriority;

    @NotNull
    @NotEmpty
    private String status;

    private String linkIOS;

    private String linkAndroid;

    private MultipartFile fileManifest;

    private MultipartFile fileIpa;

    private MultipartFile fileAndroid;

    @NotNull
    @NotEmpty
    private String applicationSourceFe;

    @NotNull
    @NotEmpty
    private String applicationSourceBe;

    @NotNull
    @NotEmpty
    private String ipDatabase;

    @NotNull
    private Long sdaHostingEntity;

    @NotNull
    private List<MultipartFile> documentUploadList;
}
