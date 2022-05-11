package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payments.class);
        Payments payments1 = new Payments();
        payments1.setIdPayment(1L);
        Payments payments2 = new Payments();
        payments2.setIdPayment(payments1.getIdPayment());
        assertThat(payments1).isEqualTo(payments2);
        payments2.setIdPayment(2L);
        assertThat(payments1).isNotEqualTo(payments2);
        payments1.setIdPayment(null);
        assertThat(payments1).isNotEqualTo(payments2);
    }
}
