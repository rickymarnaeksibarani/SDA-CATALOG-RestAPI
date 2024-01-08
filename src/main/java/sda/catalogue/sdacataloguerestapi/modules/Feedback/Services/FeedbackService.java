package sda.catalogue.sdacataloguerestapi.modules.Feedback.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.ObjectMapper.ObjectMapperUtil;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.FeedbackDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Repositories.FeedbackRepository;

import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public PaginateResponse<List<FeedbackEntity>> searchFilterFeedback(int year, Long webAppEntityId, Long rate, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<FeedbackEntity> result = feedbackRepository.filterByParams(year, webAppEntityId, rate, pageable);
        long total = feedbackRepository.countByFilter(year, webAppEntityId, rate);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
    }

    public FeedbackEntity getFeedbackByUuid(UUID uuid) {
        return feedbackRepository.findByUuid(uuid);
    }

    public FeedbackEntity createFeedback(FeedbackDTO request) {
        FeedbackEntity data = ObjectMapperUtil.map(request, FeedbackEntity.class);
        return feedbackRepository.save(data);
    }

    @Transactional
    public FeedbackEntity updateFeedback(UUID uuid, FeedbackDTO request) {
        int result = feedbackRepository.findByUuidAndUpdate(uuid, request.getPersonalNumber(), request.getRate(), request.getComment());
        if (result > 0) {
            return feedbackRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public FeedbackEntity deleteFeedback(UUID uuid) {
        FeedbackEntity findData = feedbackRepository.findByUuid(uuid);
        int result = feedbackRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
