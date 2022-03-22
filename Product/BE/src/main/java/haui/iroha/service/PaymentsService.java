package haui.iroha.service;

import haui.iroha.service.dto.PaymentsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link haui.iroha.domain.Payments}.
 */
public interface PaymentsService {
    /**
     * Save a payments.
     *
     * @param paymentsDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentsDTO save(PaymentsDTO paymentsDTO);

    /**
     * Partially updates a payments.
     *
     * @param paymentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentsDTO> partialUpdate(PaymentsDTO paymentsDTO);

    /**
     * Get all the payments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" payments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentsDTO> findOne(Long id);

    /**
     * Delete the "id" payments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
