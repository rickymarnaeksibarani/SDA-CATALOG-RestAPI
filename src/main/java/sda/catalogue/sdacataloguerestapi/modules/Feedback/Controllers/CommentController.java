package sda.catalogue.sdacataloguerestapi.modules.Feedback.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.CommentDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.FeedbackDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.CommentEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Services.CommentService;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comment")
//@CrossOrigin(origins = "${spring.frontend}")
@Validated
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping()
    public ResponseEntity<?> createComment(
            @RequestBody @Valid CommentDTO request
    ) {
        try {
            CommentEntity result = commentService.createComment(request);
            ApiResponse<CommentEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success created data comment!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
    @GetMapping()
    public ResponseEntity<?> searchFilterComment(@ModelAttribute CommentDTO searchDTO) {
        try {
            PaginationUtil<CommentEntity, CommentDTO> result = commentService.getAllCommentByPagination();
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data feedback!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateComment(
            @PathVariable("uuid") UUID uuid,
            @RequestBody @Valid CommentDTO request
    ) {
        try {
            CommentEntity result = commentService.updateComment(uuid, request);
            ApiResponse<CommentEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success updated data comment!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteComment(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            CommentEntity result = commentService.deleteComment(uuid);
            ApiResponse<CommentEntity> response = new ApiResponse<>(HttpStatus.OK, "Success deleted data comment!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

}
