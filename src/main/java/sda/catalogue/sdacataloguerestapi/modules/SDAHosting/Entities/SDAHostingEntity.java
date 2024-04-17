package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;

import java.awt.font.TextHitInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_sda_hosting")
public class SDAHostingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sda_hosting")
    private Long idSDAHosting;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "sda_hosting")
    private String sdaHosting;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "sdaHostingEntity")
    private List<WebAppEntity> sdaHostingEntities;

    public class FormattedSDAHostingEntity{
        private long idSDAHosting;
        private String sdaHosting;

        public FormattedSDAHostingEntity(long idSDAHosting, String sdaHosting){
            this.idSDAHosting = idSDAHosting;
            this.sdaHosting = sdaHosting;
        }
    }


}
