package sda.catalogue.sdacataloguerestapi.modules.Feedback.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.ObjectMapper.ObjectMapperUtil;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.CommentDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.FeedbackDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.CommentEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Repositories.CommentRepository;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Repositories.FeedbackRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public CommentEntity createComment(CommentDTO request) {
        Optional<FeedbackEntity> findData = feedbackRepository.findById(request.getFeedBackEntity());
        if (findData.isPresent()) {
            CommentEntity data = ObjectMapperUtil.map(request, CommentEntity.class);
            data.setFeedBackEntity(findData.get());
            return commentRepository.save(data);
        } else {
            throw new CustomRequestException("ID Feedback" + request.getFeedBackEntity() + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public PaginationUtil<CommentEntity, CommentDTO> getAllCommentByPagination() {
        Pageable paging = PageRequest.of(0, 20);
        Specification<CommentEntity> specs = Specification.where(null);
        Page<CommentEntity> pagedResult = commentRepository.findAll(specs, paging);

        return new PaginationUtil<>(pagedResult, CommentDTO.class);
    }

    public CommentEntity updateComment(UUID uuid, CommentDTO request) {
        int result = commentRepository.findByUuidAndUpdate(uuid, request.getPersonalNumber(), request.getComment());
        if (result > 0) {
            return commentRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public CommentEntity deleteComment(UUID uuid) {
        CommentEntity findData = commentRepository.findByUuid(uuid);
        int result = commentRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
