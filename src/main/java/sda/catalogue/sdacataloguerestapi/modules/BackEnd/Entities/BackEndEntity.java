package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_backend")
public class BackEndEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_backend")
    private long idBackEnd;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "back_end")
    private String backEnd;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
