package haui.iroha.service;

import haui.iroha.service.dto.ProductSpecsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link haui.iroha.domain.ProductSpecs}.
 */
public interface ProductSpecsService {
    /**
     * Save a productSpecs.
     *
     * @param productSpecsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSpecsDTO save(ProductSpecsDTO productSpecsDTO);

    /**
     * Partially updates a productSpecs.
     *
     * @param productSpecsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductSpecsDTO> partialUpdate(ProductSpecsDTO productSpecsDTO);

    /**
     * Get all the productSpecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductSpecsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productSpecs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSpecsDTO> findOne(Long id);

    /**
     * Delete the "id" productSpecs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
