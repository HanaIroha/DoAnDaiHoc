package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.Payments;
import haui.iroha.repository.PaymentsRepository;
import haui.iroha.service.dto.PaymentsDTO;
import haui.iroha.service.mapper.PaymentsMapper;
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
 * Integration tests for the {@link PaymentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idPayment}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private PaymentsMapper paymentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createEntity(EntityManager em) {
        Payments payments = new Payments()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return payments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createUpdatedEntity(EntityManager em) {
        Payments payments = new Payments()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return payments;
    }

    @BeforeEach
    public void initTest() {
        payments = createEntity(em);
    }

    @Test
    @Transactional
    void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();
        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayments.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPayments.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createPaymentsWithExistingId() throws Exception {
        // Create the Payments with an existing ID
        payments.setIdPayment(1L);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idPayment,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idPayment").value(hasItem(payments.getIdPayment().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get the payments
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL_ID, payments.getIdPayment()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idPayment").value(payments.getIdPayment().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findById(payments.getIdPayment()).get();
        // Disconnect from session so that the updates on updatedPayments are not directly saved in db
        em.detach(updatedPayments);
        updatedPayments.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(updatedPayments);

        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentsDTO.getIdPayment())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayments.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPayments.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setIdPayment(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentsDTO.getIdPayment())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setIdPayment(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setIdPayment(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setIdPayment(payments.getIdPayment());

        partialUpdatedPayments.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getIdPayment())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayments.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPayments.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setIdPayment(payments.getIdPayment());

        partialUpdatedPayments
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getIdPayment())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayments.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPayments.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPayments.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setIdPayment(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentsDTO.getIdPayment())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setIdPayment(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setIdPayment(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Delete the payments
        restPaymentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, payments.getIdPayment()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
