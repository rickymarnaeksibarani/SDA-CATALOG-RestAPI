package sda.catalogue.sdacataloguerestapi.modules.WebApp.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Services.WebAppService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/web-app")
public class WebAppController {

    @Autowired
    WebAppService webAppService;

    /**
     * Endpoint for searching and paginating WebAppEntities based on the provided search term.
     *
     * @param searchTerm The term to search for in various fields.
     * @param page       The page number (zero-based).
     * @param size       The number of items per page.
     * @return A page of WebAppEntity matching the search term.
     */
    @GetMapping()
    public ResponseEntity<ApiResponse<PaginateResponse<List<WebAppEntity>>>> searchWebApps(
            @RequestParam(name = "searchTerm", defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
       try {
           PaginateResponse<List<WebAppEntity>> result = webAppService.searchAndPaginate(searchTerm, page, size);
           ApiResponse<PaginateResponse<List<WebAppEntity>>> response = new ApiResponse<>(HttpStatus.OK.value(), "Successfully retrieved data webapp!", result);
           return new ResponseEntity<>(response, HttpStatus.OK);
       } catch (Exception e) {
           throw new IllegalStateException(e);
       }
    }

    @PostMapping()
    public ResponseEntity<WebAppEntity> createWebApp(
            @RequestBody @Valid WebAppEntity webappData
    ) {
       try {
           WebAppEntity result = webAppService.createWebApp(webappData);
           return new ResponseEntity<>(result, HttpStatus.OK);
       }
       catch (Exception e) {
           throw new IllegalStateException(e);
       }
    }
}
