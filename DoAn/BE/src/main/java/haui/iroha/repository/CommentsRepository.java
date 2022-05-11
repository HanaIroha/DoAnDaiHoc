package haui.iroha.repository;

import haui.iroha.domain.Comments;
import haui.iroha.domain.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Comments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query(value = "SELECT * FROM COMMENTS WHERE id_product = :id",
        countQuery = "SELECT count(*) FROM COMMENTS WHERE id_product = :id",
        nativeQuery = true)
    Page<Comments> findAllCommentByProductId(@Param("id")long id, Pageable pageable);
}
