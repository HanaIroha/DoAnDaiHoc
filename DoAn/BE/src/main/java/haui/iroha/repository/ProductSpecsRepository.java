package haui.iroha.repository;

import haui.iroha.domain.ProductSpecs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductSpecs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecsRepository extends JpaRepository<ProductSpecs, Long> {}
