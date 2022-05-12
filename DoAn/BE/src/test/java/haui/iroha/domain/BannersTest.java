package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BannersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banners.class);
        Banners banners1 = new Banners();
        banners1.setIdBanner(1L);
        Banners banners2 = new Banners();
        banners2.setIdBanner(banners1.getIdBanner());
        assertThat(banners1).isEqualTo(banners2);
        banners2.setIdBanner(2L);
        assertThat(banners1).isNotEqualTo(banners2);
        banners1.setIdBanner(null);
        assertThat(banners1).isNotEqualTo(banners2);
    }
}
