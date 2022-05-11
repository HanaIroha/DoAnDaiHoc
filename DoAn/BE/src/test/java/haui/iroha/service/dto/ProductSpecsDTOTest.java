package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecsDTO.class);
        ProductSpecsDTO productSpecsDTO1 = new ProductSpecsDTO();
        productSpecsDTO1.setIdProductSpec(1L);
        ProductSpecsDTO productSpecsDTO2 = new ProductSpecsDTO();
        assertThat(productSpecsDTO1).isNotEqualTo(productSpecsDTO2);
        productSpecsDTO2.setIdProductSpec(productSpecsDTO1.getIdProductSpec());
        assertThat(productSpecsDTO1).isEqualTo(productSpecsDTO2);
        productSpecsDTO2.setIdProductSpec(2L);
        assertThat(productSpecsDTO1).isNotEqualTo(productSpecsDTO2);
        productSpecsDTO1.setIdProductSpec(null);
        assertThat(productSpecsDTO1).isNotEqualTo(productSpecsDTO2);
    }
}
