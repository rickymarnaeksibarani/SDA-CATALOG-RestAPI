package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Services;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto.SDAHostingDTO;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto.SDAHostingRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SDAHostingService {

    @Autowired
    private SDAHostingRepository sdaHostingRepository;


    //Getting data PIC Developer with search and pagination
    public PaginationUtil<SDAHostingEntity, SDAHostingEntity> getAllSDAHostingByPagination(SDAHostingRequestDTO searchRequest) {
        Specification<SDAHostingEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("sdaHosting")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage()-1, searchRequest.getSize());
        Page<SDAHostingEntity> pagedResult = sdaHostingRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, SDAHostingEntity.class);
    }


    public SDAHostingEntity getSDAHostingByUuid(UUID uuid) {
        SDAHostingEntity result = sdaHostingRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public SDAHostingEntity createSDAHosting(SDAHostingDTO request) {
        SDAHostingEntity data = new SDAHostingEntity();
        data.setSdaHosting(request.getSdaHosting());
        return sdaHostingRepository.save(data);
    }

    @Transactional
    public SDAHostingEntity updateSDAHosting(UUID uuid, SDAHostingDTO request) {
        int result = sdaHostingRepository.findByUuidAndUpdate(uuid, request.getSdaHosting());
        if (result > 0) {
            return sdaHostingRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public SDAHostingEntity deleteSDAHosting(UUID uuid) {
        SDAHostingEntity findData = sdaHostingRepository.findByUuid(uuid);
        int result = sdaHostingRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
