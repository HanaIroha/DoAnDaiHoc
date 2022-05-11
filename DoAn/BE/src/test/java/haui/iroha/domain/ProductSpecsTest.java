package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSpecsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSpecs.class);
        ProductSpecs productSpecs1 = new ProductSpecs();
        productSpecs1.setIdProductSpec(1L);
        ProductSpecs productSpecs2 = new ProductSpecs();
        productSpecs2.setIdProductSpec(productSpecs1.getIdProductSpec());
        assertThat(productSpecs1).isEqualTo(productSpecs2);
        productSpecs2.setIdProductSpec(2L);
        assertThat(productSpecs1).isNotEqualTo(productSpecs2);
        productSpecs1.setIdProductSpec(null);
        assertThat(productSpecs1).isNotEqualTo(productSpecs2);
    }
}
