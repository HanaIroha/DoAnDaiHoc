package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDetailsDTO.class);
        OrderDetailsDTO orderDetailsDTO1 = new OrderDetailsDTO();
        orderDetailsDTO1.setIdOrderDetail(1L);
        OrderDetailsDTO orderDetailsDTO2 = new OrderDetailsDTO();
        assertThat(orderDetailsDTO1).isNotEqualTo(orderDetailsDTO2);
        orderDetailsDTO2.setIdOrderDetail(orderDetailsDTO1.getIdOrderDetail());
        assertThat(orderDetailsDTO1).isEqualTo(orderDetailsDTO2);
        orderDetailsDTO2.setIdOrderDetail(2L);
        assertThat(orderDetailsDTO1).isNotEqualTo(orderDetailsDTO2);
        orderDetailsDTO1.setIdOrderDetail(null);
        assertThat(orderDetailsDTO1).isNotEqualTo(orderDetailsDTO2);
    }
}
