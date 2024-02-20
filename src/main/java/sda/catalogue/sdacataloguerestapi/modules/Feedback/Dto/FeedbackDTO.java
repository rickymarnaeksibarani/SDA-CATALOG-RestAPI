package sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.CommentEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class FeedbackDTO {
    @JsonProperty("id_feedback")
    private long idFeedback;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("personal_number")
    private String personalNumber;

    @JsonProperty("rate")
    private Long rate;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("feedBackEntity")
    private List<CommentEntity> commentEntityList;

    @JsonProperty("id_webapp")
    private WebAppEntity webAppEntity;
}
