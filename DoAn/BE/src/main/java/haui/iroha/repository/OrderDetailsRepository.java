package haui.iroha.repository;

import haui.iroha.domain.OrderDetails;
import haui.iroha.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query(value = "SELECT * FROM ORDER_DETAILS WHERE ID_ORDER = :id",
        countQuery = "SELECT count(*) FROM ORDER_DETAILS WHERE ID_ORDER  = :id",
        nativeQuery = true)
    Page<OrderDetails> findAllByOrderId(@Param("id")long id, Pageable pageable);

    @Query(value = "SELECT SUM(PRICE) FROM ORDER_DETAILS WHERE ID_ORDER = :id",
        nativeQuery = true)
   long orderValue(@Param("id")long id);
}
