package sda.catalogue.sdacataloguerestapi.modules.PICAnalyst;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Dto.PICAnalystDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Dto.PICAnalystRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Repository.PICAnalystRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PICAnalystService {
    @Autowired
    private PICAnalystRepository picAnalystRepository;


    //Getting data PIC Analyst with search and pagination
    public PaginationUtil<PICAnalystEntity, PICAnalystEntity> getAllPICAnalystByPagination(PICAnalystRequestDTO searchRequest) {
        Specification<PICAnalystEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.or(
                                builder.like(builder.upper(root.get("personalName")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%"),
                                builder.like(builder.upper(root.get("personalNumber")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                        )
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<PICAnalystEntity> pagedResult = picAnalystRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, PICAnalystEntity.class);
    }


    //Getting data PIC Analyst by ID
    public PICAnalystEntity getPICAnalystByUUID(Long id_pic_analyst) {
        PICAnalystEntity result = picAnalystRepository.findById(id_pic_analyst).orElse(null);
        if (result == null){
            throw new CustomRequestException("ID" + id_pic_analyst + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data PIC Analyst
    public PICAnalystEntity createPICAnalyst(PICAnalystDTO request) {
        boolean existsBypersonalName = picAnalystRepository.existsByPersonalName(request.getPersonalName());
        if (existsBypersonalName){
            throw new CustomRequestException("PIC Analyst already exists, please check personal name", HttpStatus.CONFLICT);
        }

        PICAnalystEntity data = new PICAnalystEntity();
        data.setPersonalName(request.getPersonalName());
        data.setPersonalNumber(request.getPersonalNumber());
        return picAnalystRepository.save(data);
    }

    //Updating data PIC Analyst By UUID
    @Transactional
    public PICAnalystEntity updatePICAnalyst(UUID uuid, PICAnalystDTO request) {
        PICAnalystEntity picAnalyst = picAnalystRepository.findByUuid(uuid)
                .orElseThrow(()-> new CustomRequestException("PIC Analyst does not exists", HttpStatus.CONFLICT));
        picAnalyst.setPersonalNumber(request.getPersonalNumber());
        picAnalyst.setPersonalName(request.getPersonalName());
        return picAnalystRepository.save(picAnalyst);
    }


    //Deleting data PIC Analyst By UUID
    @Transactional
    public void deletePICAnalyst(UUID uuid){
        PICAnalystEntity findData = picAnalystRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomRequestException("PIC Analyst does not exist", HttpStatus.NOT_FOUND));
        picAnalystRepository.delete(findData);
    }
}
