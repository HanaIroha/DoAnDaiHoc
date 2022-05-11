package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProducersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producers.class);
        Producers producers1 = new Producers();
        producers1.setIdProducer(1L);
        Producers producers2 = new Producers();
        producers2.setIdProducer(producers1.getIdProducer());
        assertThat(producers1).isEqualTo(producers2);
        producers2.setIdProducer(2L);
        assertThat(producers1).isNotEqualTo(producers2);
        producers1.setIdProducer(null);
        assertThat(producers1).isNotEqualTo(producers2);
    }
}
