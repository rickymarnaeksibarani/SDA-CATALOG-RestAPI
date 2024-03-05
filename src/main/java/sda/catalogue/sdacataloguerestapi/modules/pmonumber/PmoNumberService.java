package sda.catalogue.sdacataloguerestapi.modules.pmonumber;

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
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.dto.PmoNumberDTO;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.dto.PmoNumberRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.entity.PmoNumberEntity;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.repository.PmoNumberRepository;

import java.util.UUID;

@Service
public class PmoNumberService {
    @Autowired
    private PmoNumberRepository pmoNumberRepository;

    //Getting data PMO Number with pagination
    public PaginationUtil<PmoNumberEntity, PmoNumberEntity> getAllPmoNumberByPagination(PmoNumberRequestDTO searchRequest) {
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Specification<PmoNumberEntity> specs = Specification.where(null);
        Page<PmoNumberEntity> pagedResult = pmoNumberRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, PmoNumberEntity.class);
    }

    //Get data PMO Number by UUID
    public PmoNumberEntity getPmoNumberByUuid(UUID uuid) {
        PmoNumberEntity result = pmoNumberRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID" + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data PMO Number
    public PmoNumberEntity createPMO(PmoNumberDTO request) {
        PmoNumberEntity data = new PmoNumberEntity();
        data.setPmoNumber(request.getPmoNumber());
        return pmoNumberRepository.save(data);
    }

    //Updating data PMO Number by UUID
    @Transactional
    public PmoNumberEntity updatePMO(UUID uuid, PmoNumberDTO request) {
        int result = pmoNumberRepository.findByUuidAndUpdate(uuid, request.getPmoNumber());
        PmoNumberEntity findData = pmoNumberRepository.findByUuid(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    //Deleting data PMO Number by UUID
    @Transactional
    public PmoNumberEntity deletePMO(UUID uuid) {
        PmoNumberEntity findData = pmoNumberRepository.findByUuid(uuid);
        int result = pmoNumberRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
