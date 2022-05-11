package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDetails.class);
        ProductDetails productDetails1 = new ProductDetails();
        productDetails1.setIdProductDetail(1L);
        ProductDetails productDetails2 = new ProductDetails();
        productDetails2.setIdProductDetail(productDetails1.getIdProductDetail());
        assertThat(productDetails1).isEqualTo(productDetails2);
        productDetails2.setIdProductDetail(2L);
        assertThat(productDetails1).isNotEqualTo(productDetails2);
        productDetails1.setIdProductDetail(null);
        assertThat(productDetails1).isNotEqualTo(productDetails2);
    }
}
