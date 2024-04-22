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

    public WebServerEntity getWebServerById(Long id_web_server) {
        WebServerEntity result = webServerRepository.findById(id_web_server).orElse(null);
        if (result == null) {
            throw new CustomRequestException("ID " + id_web_server + "not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public WebServerEntity createWebServer(WebServerDTO request) {
        boolean existsByWebServer = webServerRepository.existsByWebServer(request.getWebServer());
        if (existsByWebServer){
            throw new CustomRequestException("Web Server already exists", HttpStatus.CONFLICT);
        }
        WebServerEntity data = new WebServerEntity();
        data.setWebServerStatus(request.getWebServerStatus());
        data.setWebServer(request.getWebServer());
        return webServerRepository.save(data);
    }

    @Transactional
    public WebServerEntity updateWebServer(UUID uuid, WebServerDTO request) {
        WebServerEntity webServer = webServerRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomRequestException("WEB Server does not exists", HttpStatus.CONFLICT));
        webServer.setWebServer(request.getWebServer());
        webServer.setWebServerStatus(request.getWebServerStatus());
        return webServerRepository.save(webServer);
    }

    @Transactional
    public void deleteWebServer(UUID uuid) {
        WebServerEntity findData = webServerRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomRequestException("Web Server does not exist", HttpStatus.NOT_FOUND));
        webServerRepository.delete(findData);
    }
}
