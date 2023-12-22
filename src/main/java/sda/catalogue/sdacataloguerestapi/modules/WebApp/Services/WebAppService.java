package sda.catalogue.sdacataloguerestapi.modules.WebApp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppPostDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;

import java.util.List;
import java.util.UUID;

@Service
public class WebAppService {

    @Autowired
    private WebAppRepository webAppRepository;


    //Getting data Web App with search and pagination
    public PaginateResponse<List<WebAppEntity>> searchAndPaginate(String searchTerm, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<WebAppEntity> result = webAppRepository.findBySearchTerm(searchTerm, pageable);
        long total = webAppRepository.countBySearchTerm(searchTerm);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
    }

    //Getting data by UUID
    public WebAppEntity getWebAppByUuid(UUID uuid) {
        WebAppEntity result = webAppRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data WebApp
    public WebAppEntity createWebApp(WebAppPostDTO request) {
        WebAppEntity data = new WebAppEntity();
        System.out.println(request);
        return webAppRepository.save(data);
    }


    //Updating data WebApp by UUID
    public WebAppEntity updateWebAppByUuid(UUID uuid, WebAppEntity request) {
        return webAppRepository.updateByUuid(
                uuid,
                request.getApplicationName(),
                request.getDescription(),
                request.getFunctionApplication(),
                request.getAddress(),
                request.getBusinessImpactPriority(),
                request.getStatus());
    }

    //Deleting data WebApp by UUID
    public WebAppEntity deleteWebAppByUuid(UUID uuid) {
        return webAppRepository.findByUuidAndDelete(uuid);
    }
}
