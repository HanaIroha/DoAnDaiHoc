package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDetailsDTO.class);
        ProductDetailsDTO productDetailsDTO1 = new ProductDetailsDTO();
        productDetailsDTO1.setIdProductDetail(1L);
        ProductDetailsDTO productDetailsDTO2 = new ProductDetailsDTO();
        assertThat(productDetailsDTO1).isNotEqualTo(productDetailsDTO2);
        productDetailsDTO2.setIdProductDetail(productDetailsDTO1.getIdProductDetail());
        assertThat(productDetailsDTO1).isEqualTo(productDetailsDTO2);
        productDetailsDTO2.setIdProductDetail(2L);
        assertThat(productDetailsDTO1).isNotEqualTo(productDetailsDTO2);
        productDetailsDTO1.setIdProductDetail(null);
        assertThat(productDetailsDTO1).isNotEqualTo(productDetailsDTO2);
    }
}
