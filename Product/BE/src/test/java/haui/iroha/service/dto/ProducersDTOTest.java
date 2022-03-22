package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProducersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProducersDTO.class);
        ProducersDTO producersDTO1 = new ProducersDTO();
        producersDTO1.setIdProducer(1L);
        ProducersDTO producersDTO2 = new ProducersDTO();
        assertThat(producersDTO1).isNotEqualTo(producersDTO2);
        producersDTO2.setIdProducer(producersDTO1.getIdProducer());
        assertThat(producersDTO1).isEqualTo(producersDTO2);
        producersDTO2.setIdProducer(2L);
        assertThat(producersDTO1).isNotEqualTo(producersDTO2);
        producersDTO1.setIdProducer(null);
        assertThat(producersDTO1).isNotEqualTo(producersDTO2);
    }
}
