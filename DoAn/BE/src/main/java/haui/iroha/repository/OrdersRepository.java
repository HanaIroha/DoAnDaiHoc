package haui.iroha.repository;

import haui.iroha.domain.Orders;
import haui.iroha.domain.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Orders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM ORDERS WHERE ORDER_STATUS LIKE %:status%",
        countQuery = "SELECT count(*) FROM ORDERS WHERE ORDER_STATUS LIKE %:status%",
        nativeQuery = true)
    Page<Orders> findAllOrdersByStatus(@Param("status")String status, Pageable pageable);

    @Query(value = "SELECT * FROM ORDERS WHERE LOGIN = :login",
        countQuery = "SELECT count(*) FROM ORDERS WHERE LOGIN = :login",
        nativeQuery = true)
    Page<Orders> findAllOrdersByLogin(@Param("login")String login, Pageable pageable);

    @Query(value = "SELECT * FROM ORDERS WHERE ID_ORDER LIKE %:status%",
        countQuery = "SELECT count(*) FROM ORDERS WHERE ID_ORDER LIKE %:status%",
        nativeQuery = true)
    Page<Orders> findAllOrdersById(@Param("status")String status, Pageable pageable);

    @Query(value="SELECT COUNT(*) FROM ORDERS WHERE login=:login", nativeQuery = true)
    long getUserOrderCount(@Param("login")String login);
}
