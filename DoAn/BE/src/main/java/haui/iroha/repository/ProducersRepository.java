package haui.iroha.repository;

import haui.iroha.domain.Producers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Producers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProducersRepository extends JpaRepository<Producers, Long> {}
