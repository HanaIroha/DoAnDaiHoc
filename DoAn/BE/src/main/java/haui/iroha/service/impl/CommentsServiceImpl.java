package haui.iroha.service.impl;

import haui.iroha.domain.Comments;
import haui.iroha.repository.CommentsRepository;
import haui.iroha.service.CommentsService;
import haui.iroha.service.dto.CommentsDTO;
import haui.iroha.service.mapper.CommentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comments}.
 */
@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

    private final Logger log = LoggerFactory.getLogger(CommentsServiceImpl.class);

    private final CommentsRepository commentsRepository;

    private final CommentsMapper commentsMapper;

    public CommentsServiceImpl(CommentsRepository commentsRepository, CommentsMapper commentsMapper) {
        this.commentsRepository = commentsRepository;
        this.commentsMapper = commentsMapper;
    }

    @Override
    public CommentsDTO save(CommentsDTO commentsDTO) {
        log.debug("Request to save Comments : {}", commentsDTO);
        Comments comments = commentsMapper.toEntity(commentsDTO);
        comments = commentsRepository.save(comments);
        return commentsMapper.toDto(comments);
    }

    @Override
    public Optional<CommentsDTO> partialUpdate(CommentsDTO commentsDTO) {
        log.debug("Request to partially update Comments : {}", commentsDTO);

        return commentsRepository
            .findById(commentsDTO.getIdComment())
            .map(existingComments -> {
                commentsMapper.partialUpdate(existingComments, commentsDTO);

                return existingComments;
            })
            .map(commentsRepository::save)
            .map(commentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentsRepository.findAll(pageable).map(commentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentsDTO> findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        return commentsRepository.findById(id).map(commentsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comments : {}", id);
        commentsRepository.deleteById(id);
    }

    @Override
    public Page<CommentsDTO> findAllCommentByProductId(long id, Pageable pageable) {
        log.debug("Request to get all Comments by Product Id");
        return commentsRepository.findAllCommentByProductId(id,pageable).map(commentsMapper::toDto);
    }
}
