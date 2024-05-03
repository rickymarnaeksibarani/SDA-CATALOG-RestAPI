package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "master_pic_developer")
public class PICDeveloperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pic_developer")
    private long idPicDeveloper;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "personal_name")
    private String personalName;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PICDeveloperEntity(Long id) {
        // Assuming you want to set the idPicDeveloper field with the provided id
        this.idPicDeveloper = id;
        // You might need to initialize other fields based on the id or provide additional logic
    }

}
