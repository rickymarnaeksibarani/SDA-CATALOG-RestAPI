package sda.catalogue.sdacataloguerestapi.modules.Feedback.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.FeedbackDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Services.FeedbackService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback")
@Validated
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping()
    public ResponseEntity<?> searchFilterFeedback(
            @RequestParam(name = "year", defaultValue = "") int year,
            @RequestParam(name = "webAppEntityId", defaultValue = "") Long webAppEntityId,
            @RequestParam(name = "rate", defaultValue = "") Long rate,
            @RequestParam(name = "page", defaultValue = "1") long page,
            @RequestParam(name = "size", defaultValue = "10") long size
    ) {
        try {
            PaginateResponse<List<FeedbackEntity>> result = feedbackService.searchFilterFeedback(year, webAppEntityId, rate, page, size);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data feedback!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getFeedbackByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            FeedbackEntity result = feedbackService.getFeedbackByUuid(uuid);
            ApiResponse<FeedbackEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data feedback!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createFeedback(
            @RequestBody @Valid FeedbackDTO request
    ) {
        try {
            FeedbackEntity result = feedbackService.createFeedback(request);
            ApiResponse<FeedbackEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data feedback!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateFeedback(
            @PathVariable("uuid") UUID uuid,
            @RequestBody @Valid FeedbackDTO request
    ) {
        try {
            FeedbackEntity result = feedbackService.updateFeedback(uuid, request);
            ApiResponse<FeedbackEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data feedback!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFeedback(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            FeedbackEntity result = feedbackService.deleteFeedback(uuid);
            ApiResponse<FeedbackEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data feedback!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
