package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsDTO.class);
        ProductsDTO productsDTO1 = new ProductsDTO();
        productsDTO1.setIdProduct(1L);
        ProductsDTO productsDTO2 = new ProductsDTO();
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
        productsDTO2.setIdProduct(productsDTO1.getIdProduct());
        assertThat(productsDTO1).isEqualTo(productsDTO2);
        productsDTO2.setIdProduct(2L);
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
        productsDTO1.setIdProduct(null);
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
    }
}
