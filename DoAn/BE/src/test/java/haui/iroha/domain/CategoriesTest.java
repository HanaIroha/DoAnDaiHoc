package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categories.class);
        Categories categories1 = new Categories();
        categories1.setIdCategory(1L);
        Categories categories2 = new Categories();
        categories2.setIdCategory(categories1.getIdCategory());
        assertThat(categories1).isEqualTo(categories2);
        categories2.setIdCategory(2L);
        assertThat(categories1).isNotEqualTo(categories2);
        categories1.setIdCategory(null);
        assertThat(categories1).isNotEqualTo(categories2);
    }
}
