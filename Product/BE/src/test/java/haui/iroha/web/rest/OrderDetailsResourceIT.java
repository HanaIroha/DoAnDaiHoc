package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.OrderDetails;
import haui.iroha.repository.OrderDetailsRepository;
import haui.iroha.service.dto.OrderDetailsDTO;
import haui.iroha.service.mapper.OrderDetailsMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderDetailsResourceIT {

    private static final Long DEFAULT_ID_ORDER = 1L;
    private static final Long UPDATED_ID_ORDER = 2L;

    private static final Long DEFAULT_ID_PRODUCT = 1L;
    private static final Long UPDATED_ID_PRODUCT = 2L;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/order-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idOrderDetail}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderDetailsMockMvc;

    private OrderDetails orderDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetails createEntity(EntityManager em) {
        OrderDetails orderDetails = new OrderDetails()
            .idOrder(DEFAULT_ID_ORDER)
            .idProduct(DEFAULT_ID_PRODUCT)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return orderDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetails createUpdatedEntity(EntityManager em) {
        OrderDetails orderDetails = new OrderDetails()
            .idOrder(UPDATED_ID_ORDER)
            .idProduct(UPDATED_ID_PRODUCT)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return orderDetails;
    }

    @BeforeEach
    public void initTest() {
        orderDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();
        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);
        restOrderDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getIdOrder()).isEqualTo(DEFAULT_ID_ORDER);
        assertThat(testOrderDetails.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
        assertThat(testOrderDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrderDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrderDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createOrderDetailsWithExistingId() throws Exception {
        // Create the OrderDetails with an existing ID
        orderDetails.setIdOrderDetail(1L);
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get all the orderDetailsList
        restOrderDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idOrderDetail,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idOrderDetail").value(hasItem(orderDetails.getIdOrderDetail().intValue())))
            .andExpect(jsonPath("$.[*].idOrder").value(hasItem(DEFAULT_ID_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(DEFAULT_ID_PRODUCT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get the orderDetails
        restOrderDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, orderDetails.getIdOrderDetail()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idOrderDetail").value(orderDetails.getIdOrderDetail().intValue()))
            .andExpect(jsonPath("$.idOrder").value(DEFAULT_ID_ORDER.intValue()))
            .andExpect(jsonPath("$.idProduct").value(DEFAULT_ID_PRODUCT.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingOrderDetails() throws Exception {
        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Update the orderDetails
        OrderDetails updatedOrderDetails = orderDetailsRepository.findById(orderDetails.getIdOrderDetail()).get();
        // Disconnect from session so that the updates on updatedOrderDetails are not directly saved in db
        em.detach(updatedOrderDetails);
        updatedOrderDetails
            .idOrder(UPDATED_ID_ORDER)
            .idProduct(UPDATED_ID_PRODUCT)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(updatedOrderDetails);

        restOrderDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDetailsDTO.getIdOrderDetail())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getIdOrder()).isEqualTo(UPDATED_ID_ORDER);
        assertThat(testOrderDetails.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testOrderDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrderDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();
        orderDetails.setIdOrderDetail(count.incrementAndGet());

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDetailsDTO.getIdOrderDetail())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();
        orderDetails.setIdOrderDetail(count.incrementAndGet());

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();
        orderDetails.setIdOrderDetail(count.incrementAndGet());

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderDetailsWithPatch() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Update the orderDetails using partial update
        OrderDetails partialUpdatedOrderDetails = new OrderDetails();
        partialUpdatedOrderDetails.setIdOrderDetail(orderDetails.getIdOrderDetail());

        partialUpdatedOrderDetails.idOrder(UPDATED_ID_ORDER).quantity(UPDATED_QUANTITY).price(UPDATED_PRICE).updatedAt(UPDATED_UPDATED_AT);

        restOrderDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderDetails.getIdOrderDetail())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderDetails))
            )
            .andExpect(status().isOk());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getIdOrder()).isEqualTo(UPDATED_ID_ORDER);
        assertThat(testOrderDetails.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
        assertThat(testOrderDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrderDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateOrderDetailsWithPatch() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Update the orderDetails using partial update
        OrderDetails partialUpdatedOrderDetails = new OrderDetails();
        partialUpdatedOrderDetails.setIdOrderDetail(orderDetails.getIdOrderDetail());

        partialUpdatedOrderDetails
            .idOrder(UPDATED_ID_ORDER)
            .idProduct(UPDATED_ID_PRODUCT)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restOrderDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderDetails.getIdOrderDetail())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderDetails))
            )
            .andExpect(status().isOk());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getIdOrder()).isEqualTo(UPDATED_ID_ORDER);
        assertThat(testOrderDetails.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testOrderDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrderDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();
        orderDetails.setIdOrderDetail(count.incrementAndGet());

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderDetailsDTO.getIdOrderDetail())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();
        orderDetails.setIdOrderDetail(count.incrementAndGet());

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();
        orderDetails.setIdOrderDetail(count.incrementAndGet());

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(orderDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        int databaseSizeBeforeDelete = orderDetailsRepository.findAll().size();

        // Delete the orderDetails
        restOrderDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderDetails.getIdOrderDetail()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
