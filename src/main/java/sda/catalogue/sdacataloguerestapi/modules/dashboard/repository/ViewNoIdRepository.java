package sda.catalogue.sdacataloguerestapi.modules.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ViewNoIdRepository<T, K> extends Repository<T, K> {
    long count();
    boolean existsById(K id);
    List<T> findAll();
    List<T> findAllById(Iterable<K> ids);
//    Page<T> findAll(Specification<T> spec, Pageable pageable);
    Optional<T> findById(K id);
}
