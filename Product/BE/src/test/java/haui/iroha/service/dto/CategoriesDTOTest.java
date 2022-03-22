package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriesDTO.class);
        CategoriesDTO categoriesDTO1 = new CategoriesDTO();
        categoriesDTO1.setIdCategory(1L);
        CategoriesDTO categoriesDTO2 = new CategoriesDTO();
        assertThat(categoriesDTO1).isNotEqualTo(categoriesDTO2);
        categoriesDTO2.setIdCategory(categoriesDTO1.getIdCategory());
        assertThat(categoriesDTO1).isEqualTo(categoriesDTO2);
        categoriesDTO2.setIdCategory(2L);
        assertThat(categoriesDTO1).isNotEqualTo(categoriesDTO2);
        categoriesDTO1.setIdCategory(null);
        assertThat(categoriesDTO1).isNotEqualTo(categoriesDTO2);
    }
}
