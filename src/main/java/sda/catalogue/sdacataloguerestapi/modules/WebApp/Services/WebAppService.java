package sda.catalogue.sdacataloguerestapi.modules.WebApp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;

import java.util.List;

@Service
public class WebAppService {

    @Autowired
    WebAppRepository webAppRepository;


    /**
     * Searches for WebAppEntities based on the provided search term and returns a paginated result.
     *
     * @param searchTerm The term to search for in various fields.
     * @param page       The page number (zero-based).
     * @param size       The number of items per page.
     * @return A page of WebAppEntity matching the search term.
     */
    public PaginateResponse<List<WebAppEntity>> searchAndPaginate(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<WebAppEntity> result = webAppRepository.findBySearchTerm(searchTerm, pageable);
        long total = webAppRepository.countBySearchTerm(searchTerm);
        return new PaginateResponse<>(result, page, total);
    }

    public WebAppEntity createWebApp(WebAppEntity webappData) {
        return webAppRepository.save(webappData);
    }
}
