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


    public SDAHostingEntity getSDAHostingById(Long id_sda_hosting) {
        SDAHostingEntity result = sdaHostingRepository.findById(id_sda_hosting).orElse(null);
        if (result == null) {
            throw new CustomRequestException("ID " + id_sda_hosting + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public SDAHostingEntity createSDAHosting(SDAHostingDTO request) {
        boolean existsBySdaHosting = sdaHostingRepository.existsBySdaHosting(request.getSdaHosting());
        if (existsBySdaHosting){
            throw new CustomRequestException("SDA Hosting already exists", HttpStatus.CONFLICT);
        }
        SDAHostingEntity data = new SDAHostingEntity();
        data.setSdaHostingStatus(request.getSdaHostingStatus());
        data.setSdaHosting(request.getSdaHosting());
        data.setSdaHostingEntities(request.getSdaHostingEntities());
        return sdaHostingRepository.save(data);
    }

    @Transactional
    public SDAHostingEntity updateSDAHosting(UUID uuid, SDAHostingDTO request) {
        SDAHostingEntity sdaHosting = sdaHostingRepository.findByUuid(uuid)
                .orElseThrow(()-> new CustomRequestException("PIC Developer does not exists", HttpStatus.CONFLICT));
        sdaHosting.setSdaHosting(request.getSdaHosting());
        sdaHosting.setSdaHostingStatus(request.getSdaHostingStatus());
        sdaHosting.setSdaHostingEntities(request.getSdaHostingEntities());
        return sdaHostingRepository.save(sdaHosting);
    }

    @Transactional
    public void deleteSDAHosting(UUID uuid) {
        SDAHostingEntity findData = sdaHostingRepository.findByUuid(uuid).orElseThrow(()->new CustomRequestException("SDA Hosting does not exist", HttpStatus.NOT_FOUND));
        sdaHostingRepository.delete(findData);
    }
}
