package haui.iroha.service;

import haui.iroha.service.dto.ProductDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link haui.iroha.domain.ProductDetails}.
 */
public interface ProductDetailsService {
    /**
     * Save a productDetails.
     *
     * @param productDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDetailsDTO save(ProductDetailsDTO productDetailsDTO);

    /**
     * Partially updates a productDetails.
     *
     * @param productDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductDetailsDTO> partialUpdate(ProductDetailsDTO productDetailsDTO);

    /**
     * Get all the productDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" productDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
