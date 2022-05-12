package haui.iroha.repository;

import haui.iroha.domain.OrderDetails;
import haui.iroha.domain.ProductSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ProductSpecs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSpecsRepository extends JpaRepository<ProductSpecs, Long> {
    @Query(value = "SELECT * FROM PRODUCT_SPECS WHERE ID_PRODUCT = :id",
        countQuery = "SELECT count(*) FROM PRODUCT_SPECS WHERE ID_PRODUCT  = :id",
        nativeQuery = true)
    Page<ProductSpecs> findAllByProductId(@Param("id")long id, Pageable pageable);
}
