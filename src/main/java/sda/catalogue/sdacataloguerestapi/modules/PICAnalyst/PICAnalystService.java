package sda.catalogue.sdacataloguerestapi.modules.PICAnalyst;

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

import java.util.UUID;

@Service
public class PICAnalystService {
    @Autowired
    private PICAnalystRepository picAnalystRepository;


    //Getting data PIC Analyst with search and pagination
    public PaginationUtil<PICAnalystEntity, PICAnalystEntity> getAllPICAnalystByPagination(PICAnalystRequestDTO searchRequest) {
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Specification<PICAnalystEntity> specs = Specification.where(null);
        Page<PICAnalystEntity> pagedResult = picAnalystRepository.findAll(specs, paging);
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
        boolean existsByPICAnalyst = picAnalystRepository.existsByPICAnalyst(request.getPersonalName());
        if (existsByPICAnalyst){
            throw new CustomRequestException("PIC Analyst already exists", HttpStatus.CONFLICT);
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
                .orElseThrow(() -> new CustomRequestException("PIC Developer does not exist", HttpStatus.NOT_FOUND));
        picAnalystRepository.delete(findData);
    }
}
