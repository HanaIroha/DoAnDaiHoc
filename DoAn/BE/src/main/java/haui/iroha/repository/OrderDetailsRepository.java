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

    @Query(value = "SELECT COALESCE(SUM(PRICE),0) FROM ORDER_DETAILS JOIN ORDERS ON ORDER_DETAILS.ID_ORDER = ORDERS.ID_ORDER WHERE MONTH(ORDERS.UPDATED_AT) = :month AND YEAR(ORDERS.UPDATED_AT) = :year AND ORDER_STATUS = 1",
        nativeQuery = true)
   long orderValueByTime(@Param("month")long month, @Param("year")long year);

    @Query(value = "SELECT COALESCE(SUM(QUANTITY),0) FROM ORDER_DETAILS JOIN ORDERS ON ORDER_DETAILS.ID_ORDER = ORDERS.ID_ORDER WHERE MONTH(ORDERS.UPDATED_AT) = :month AND YEAR(ORDERS.UPDATED_AT) = :year AND ORDER_STATUS = 1",
        nativeQuery = true)
    long orderItemAmountByTime(@Param("month")long month, @Param("year")long year);

    @Query(value = "SELECT COALESCE(COUNT(ID_ORDER_DETAIL),0) FROM ORDER_DETAILS JOIN ORDERS ON ORDER_DETAILS.ID_ORDER = ORDERS.ID_ORDER WHERE MONTH(ORDERS.UPDATED_AT) = :month AND YEAR(ORDERS.UPDATED_AT) = :year AND ORDER_STATUS = 1",
        nativeQuery = true)
    long orderAmountByTime(@Param("month")long month, @Param("year")long year);

    @Query(value = "SELECT SUM(PRICE) FROM ORDER_DETAILS WHERE ID_ORDER = :id",
        nativeQuery = true)
    long orderValue(@Param("id")long id);
}
