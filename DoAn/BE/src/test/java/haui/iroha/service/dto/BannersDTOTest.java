package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BannersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannersDTO.class);
        BannersDTO bannersDTO1 = new BannersDTO();
        bannersDTO1.setIdBanner(1L);
        BannersDTO bannersDTO2 = new BannersDTO();
        assertThat(bannersDTO1).isNotEqualTo(bannersDTO2);
        bannersDTO2.setIdBanner(bannersDTO1.getIdBanner());
        assertThat(bannersDTO1).isEqualTo(bannersDTO2);
        bannersDTO2.setIdBanner(2L);
        assertThat(bannersDTO1).isNotEqualTo(bannersDTO2);
        bannersDTO1.setIdBanner(null);
        assertThat(bannersDTO1).isNotEqualTo(bannersDTO2);
    }
}
