package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import java.util.List;

@Repository
public interface WebAppRepository extends JpaRepository<WebAppEntity, Long> {

    /**
     * Custom query method to perform a case-insensitive search across multiple fields.
     *
     * @param searchTerm The term to search for.
     * @param pageable   The pagination information.
     * @return A page of WebAppEntity matching the search term.
     */
    @Query("SELECT w FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.functionApplication) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.dinas) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.mappingFunction) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.businessImpactPriority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.sdaCloud) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "ORDER BY w.updatedAt DESC")
    List<WebAppEntity> findBySearchTerm(String searchTerm, Pageable pageable);


    /**
     * Count the total number of records matching the search term.
     *
     * @param searchTerm The term to search for.
     * @return The total count of WebAppEntity records matching the search term.
     */
    @Query("SELECT COUNT(w) FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.functionApplication) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.dinas) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.mappingFunction) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.businessImpactPriority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.sdaCloud) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long countBySearchTerm(String searchTerm);
}
