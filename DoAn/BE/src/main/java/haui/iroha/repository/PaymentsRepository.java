package haui.iroha.repository;

import haui.iroha.domain.Payments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Payments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {}
