package haui.iroha.service.impl;

import haui.iroha.domain.Images;
import haui.iroha.repository.ImagesRepository;
import haui.iroha.service.ImagesService;
import haui.iroha.service.dto.ImagesDTO;
import haui.iroha.service.mapper.ImagesMapper;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Images}.
 */
@Service
@Transactional
public class ImagesServiceImpl implements ImagesService {

    private final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);

    private final ImagesRepository imagesRepository;

    private final ImagesMapper imagesMapper;

    public ImagesServiceImpl(ImagesRepository imagesRepository, ImagesMapper imagesMapper) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
    }

    @Override
    public ImagesDTO save(ImagesDTO imagesDTO) {
        log.debug("Request to save Images : {}", imagesDTO);
        Images images = imagesMapper.toEntity(imagesDTO);
        images = imagesRepository.save(images);
        return imagesMapper.toDto(images);
    }

    @Override
    public Optional<ImagesDTO> partialUpdate(ImagesDTO imagesDTO) {
        log.debug("Request to partially update Images : {}", imagesDTO);

        return imagesRepository
            .findById(imagesDTO.getIdImage())
            .map(existingImages -> {
                imagesMapper.partialUpdate(existingImages, imagesDTO);

                return existingImages;
            })
            .map(imagesRepository::save)
            .map(imagesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imagesRepository.findAll(pageable).map(imagesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImagesDTO> findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        return imagesRepository.findById(id).map(imagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.deleteById(id);
    }

    @Override
    public Page<ImagesDTO> findAllByProductId(long id, Pageable pageable) {
        return imagesRepository.findAllByProductId(id,pageable).map(imagesMapper::toDto);
    }
}
