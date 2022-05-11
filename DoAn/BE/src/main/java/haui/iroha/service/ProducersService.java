package haui.iroha.service;

import haui.iroha.service.dto.ProducersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link haui.iroha.domain.Producers}.
 */
public interface ProducersService {
    /**
     * Save a producers.
     *
     * @param producersDTO the entity to save.
     * @return the persisted entity.
     */
    ProducersDTO save(ProducersDTO producersDTO);

    /**
     * Partially updates a producers.
     *
     * @param producersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProducersDTO> partialUpdate(ProducersDTO producersDTO);

    /**
     * Get all the producers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProducersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" producers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProducersDTO> findOne(Long id);

    /**
     * Delete the "id" producers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
