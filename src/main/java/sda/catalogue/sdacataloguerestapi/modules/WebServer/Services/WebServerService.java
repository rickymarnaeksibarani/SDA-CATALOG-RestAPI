package sda.catalogue.sdacataloguerestapi.modules.WebServer.Services;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories.WebServerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WebServerService {
    @Autowired
    private WebServerRepository webServerRepository;

    //Getting data Web Server with search and pagination
    public PaginationUtil<WebServerEntity, WebServerEntity> getAllWebServerByPagination(WebServerRequestDTO searchRequest) {
        Specification<WebServerEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("webServer")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<WebServerEntity> pagedResult = webServerRepository.findAll(specification, paging);
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WEB Server does not exists"));
        webServer.setWebServer(request.getWebServer());
        webServer.setWebServerStatus(request.getWebServerStatus());
        return webServerRepository.save(webServer);
    }

    @Transactional
    public void deleteWebServer(UUID uuid) {
        WebServerEntity findData = webServerRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Web Server does not exist"));
        webServerRepository.delete(findData);
    }
}
