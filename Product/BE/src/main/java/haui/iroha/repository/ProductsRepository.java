package haui.iroha.repository;

import haui.iroha.domain.Products;
import haui.iroha.service.dto.ProductsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Products entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    @Query(value = "SELECT * FROM PRODUCTS WHERE IS_DISABLE = FALSE",
        countQuery = "SELECT count(*) FROM PRODUCTS WHERE IS_DISABLE = FALSE",
        nativeQuery = true)
    Page<Products> findAllActive(Pageable pageable);

    @Query(value = "SELECT * FROM PRODUCTS WHERE IS_DISABLE = FALSE AND name Like %:filterKey% AND id_producer Like %:filterProducer% AND price between :minprice and :maxprice and r_am like %:filterRam% and r_om like %:filterRom%",
        countQuery = "SELECT count(*) FROM PRODUCTS WHERE IS_DISABLE = FALSE AND name Like %:filterKey% AND id_producer Like %:filterProducer% AND price between :minprice and :maxprice and r_am like %:filterRam% and r_om like %:filterRom%",
        nativeQuery = true)
    Page<Products> findAllActiveWithFilter(@Param("filterKey") String filterKey,
                                           @Param("filterProducer") String filterProducer,
                                           @Param("minprice")String minprice,
                                           @Param("maxprice")String maxprice,
                                           @Param("filterRam")String filterRam,
                                           @Param("filterRom")String filterRom, Pageable pageable);
    @Query(value="SELECT COUNT(*) FROM PRODUCTS WHERE o_s=:os AND IS_DISABLE = FALSE", nativeQuery = true)
    long getAmountPhoneByOS(@Param("os")String os);

    @Query(value="SELECT price FROM PRODUCTS WHERE id_product=:id AND IS_DISABLE = FALSE", nativeQuery = true)
    long getPriceById(@Param("id")long id);
}
