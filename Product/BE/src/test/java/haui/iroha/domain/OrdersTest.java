package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orders.class);
        Orders orders1 = new Orders();
        orders1.setIdOrder(1L);
        Orders orders2 = new Orders();
        orders2.setIdOrder(orders1.getIdOrder());
        assertThat(orders1).isEqualTo(orders2);
        orders2.setIdOrder(2L);
        assertThat(orders1).isNotEqualTo(orders2);
        orders1.setIdOrder(null);
        assertThat(orders1).isNotEqualTo(orders2);
    }
}
