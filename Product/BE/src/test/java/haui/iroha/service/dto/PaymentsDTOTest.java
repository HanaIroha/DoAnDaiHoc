package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentsDTO.class);
        PaymentsDTO paymentsDTO1 = new PaymentsDTO();
        paymentsDTO1.setIdPayment(1L);
        PaymentsDTO paymentsDTO2 = new PaymentsDTO();
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
        paymentsDTO2.setIdPayment(paymentsDTO1.getIdPayment());
        assertThat(paymentsDTO1).isEqualTo(paymentsDTO2);
        paymentsDTO2.setIdPayment(2L);
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
        paymentsDTO1.setIdPayment(null);
        assertThat(paymentsDTO1).isNotEqualTo(paymentsDTO2);
    }
}
