package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Products.class);
        Products products1 = new Products();
        products1.setIdProduct(1L);
        Products products2 = new Products();
        products2.setIdProduct(products1.getIdProduct());
        assertThat(products1).isEqualTo(products2);
        products2.setIdProduct(2L);
        assertThat(products1).isNotEqualTo(products2);
        products1.setIdProduct(null);
        assertThat(products1).isNotEqualTo(products2);
    }
}
