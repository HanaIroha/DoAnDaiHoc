package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagesDTO.class);
        ImagesDTO imagesDTO1 = new ImagesDTO();
        imagesDTO1.setIdImage(1L);
        ImagesDTO imagesDTO2 = new ImagesDTO();
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
        imagesDTO2.setIdImage(imagesDTO1.getIdImage());
        assertThat(imagesDTO1).isEqualTo(imagesDTO2);
        imagesDTO2.setIdImage(2L);
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
        imagesDTO1.setIdImage(null);
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
    }
}
