package sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class CommentDTO {

    @NotEmpty
    @NotNull
    private String personalNumber;

    @NotEmpty
    @NotNull
    private String comment;
}
