package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;

@Repository
public interface DocumentUploadRepository extends JpaRepository<DocumentUploadEntity,Long> {
}
