package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Images.class);
        Images images1 = new Images();
        images1.setIdImage(1L);
        Images images2 = new Images();
        images2.setIdImage(images1.getIdImage());
        assertThat(images1).isEqualTo(images2);
        images2.setIdImage(2L);
        assertThat(images1).isNotEqualTo(images2);
        images1.setIdImage(null);
        assertThat(images1).isNotEqualTo(images2);
    }
}
