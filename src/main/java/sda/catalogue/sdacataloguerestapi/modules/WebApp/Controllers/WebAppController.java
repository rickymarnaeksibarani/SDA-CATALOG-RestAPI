package sda.catalogue.sdacataloguerestapi.modules.WebApp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.*;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Services.WebAppService;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;

import java.util.*;

@RestController
@Validated
@RequestMapping("/api/v1/web-app")
@CrossOrigin(origins = "${spring.frontend}")
public class WebAppController {

}
