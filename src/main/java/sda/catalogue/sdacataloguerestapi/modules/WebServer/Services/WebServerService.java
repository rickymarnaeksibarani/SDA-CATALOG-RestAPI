package sda.catalogue.sdacataloguerestapi.modules.WebServer.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories.WebServerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class WebServerService {
    @Autowired
    private WebServerRepository webServerRepository;

    public PaginateResponse<List<WebServerEntity>> searchWebServer(String searchTerm, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<WebServerEntity> result = webServerRepository.findBySearchTerm(searchTerm, pageable);
        long total = webServerRepository.countBySearchTerm(searchTerm);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
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
