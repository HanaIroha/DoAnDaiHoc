package haui.iroha.repository;

import haui.iroha.domain.Banners;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Banners entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BannersRepository extends JpaRepository<Banners, Long> {}
