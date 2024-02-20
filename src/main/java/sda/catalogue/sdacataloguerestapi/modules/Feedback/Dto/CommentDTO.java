package sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class CommentDTO {

    @NotEmpty
    @NotNull
    private String personalNumber;

    @NotEmpty
    @NotNull
    private String comment;

    @NotEmpty
    @NotNull
    private Long feedBackEntity;
}
