package sda.catalogue.sdacataloguerestapi.modules.WebServer.Services;

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
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories.WebServerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class WebServerService {
    @Autowired
    private WebServerRepository webServerRepository;

    //Getting data Web Server with search and pagination
    public PaginationUtil<WebServerEntity, WebServerEntity> getAllWebServerByPagination(WebServerRequestDTO searchRequest) {
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Specification<WebServerEntity> specs = Specification.where(null);
        Page<WebServerEntity> pagedResult = webServerRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, WebServerEntity.class);
    }

    public WebServerEntity getWebServerByUuid(UUID uuid) {
        return webServerRepository.findByUuid(uuid);
    }

    public WebServerEntity createWebServer(WebServerDTO request) {
        WebServerEntity data = new WebServerEntity();
        data.setWebServer(request.getWebServer());
        return webServerRepository.save(data);
    }

    @Transactional
    public WebServerEntity updateWebServer(UUID uuid, WebServerDTO request) {
        int result = webServerRepository.findByUuidAndUpdate(uuid, request.getWebServer());
        WebServerEntity findData = webServerRepository.findByUuid(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public WebServerEntity deleteWebServer(UUID uuid) {
        WebServerEntity findData = webServerRepository.findByUuid(uuid);
        int result = webServerRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
