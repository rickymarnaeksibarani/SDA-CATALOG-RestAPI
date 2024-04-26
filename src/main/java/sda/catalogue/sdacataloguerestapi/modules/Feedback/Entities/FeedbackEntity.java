package sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_feedback")
    public class FeedbackEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id_feedback")
        private long idFeedback;

        @UuidGenerator
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID uuid;

        @Column(name = "personal_number")
        private String personalNumber;

        @Column(name = "rate")
        private Long rate;

        @Column(name = "comment")
        private String comment;

        @CreationTimestamp
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "feedBackEntity", cascade = CascadeType.ALL)
        private List<CommentEntity> commentEntityList;

        @ManyToOne
        @JsonBackReference
        @JoinColumn(name = "id_webapp")
        private WebAppEntity webAppEntity;
    }
