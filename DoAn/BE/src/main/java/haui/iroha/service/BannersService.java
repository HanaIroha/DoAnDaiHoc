package haui.iroha.service;

import haui.iroha.service.dto.BannersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link haui.iroha.domain.Banners}.
 */
public interface BannersService {
    /**
     * Save a banners.
     *
     * @param bannersDTO the entity to save.
     * @return the persisted entity.
     */
    BannersDTO save(BannersDTO bannersDTO);

    /**
     * Partially updates a banners.
     *
     * @param bannersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BannersDTO> partialUpdate(BannersDTO bannersDTO);

    /**
     * Get all the banners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BannersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" banners.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BannersDTO> findOne(Long id);

    /**
     * Delete the "id" banners.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
