package sda.catalogue.sdacataloguerestapi.modules.Feedback.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.ObjectMapper.ObjectMapperUtil;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Dto.CommentDTO;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.CommentEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Repositories.CommentRepository;

import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentEntity createComment(CommentDTO request) {
        CommentEntity data = ObjectMapperUtil.map(request, CommentEntity.class);
        return commentRepository.save(data);
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
