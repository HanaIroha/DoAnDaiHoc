package haui.iroha.service;

import haui.iroha.service.dto.ImagesDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link haui.iroha.domain.Images}.
 */
public interface ImagesService {
    /**
     * Save a images.
     *
     * @param imagesDTO the entity to save.
     * @return the persisted entity.
     */
    ImagesDTO save(ImagesDTO imagesDTO);

    /**
     * Partially updates a images.
     *
     * @param imagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImagesDTO> partialUpdate(ImagesDTO imagesDTO);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImagesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" images.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImagesDTO> findOne(Long id);

    /**
     * Delete the "id" images.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<ImagesDTO> findAllByProductId(long id, Pageable pageable);
}
