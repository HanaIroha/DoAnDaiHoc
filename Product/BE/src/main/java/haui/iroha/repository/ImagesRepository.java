package haui.iroha.repository;

import haui.iroha.domain.Images;
import haui.iroha.domain.Products;
import haui.iroha.service.dto.ImagesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Images entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

    @Query(value = "SELECT * FROM IMAGES WHERE id_product = :id",
        countQuery = "SELECT count(*) FROM IMAGES WHERE id_product = :id",
        nativeQuery = true)
    Page<Images> findAllByProductId(@Param("id") long id, Pageable pageable);

    @Query(value = "SELECT * FROM IMAGES WHERE id_product = :id",
        nativeQuery = true)
    List<Images> findAllByProductId(@Param("id") long id);
}
