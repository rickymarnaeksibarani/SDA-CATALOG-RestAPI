package sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FeedbackDTO {
    @NotEmpty
    @NotNull
    private String personalNumber;

    @NotNull
    private Long rate;

    @NotEmpty
    @NotNull
    private String comment;
}
