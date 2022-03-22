package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.Producers;
import haui.iroha.repository.ProducersRepository;
import haui.iroha.service.dto.ProducersDTO;
import haui.iroha.service.mapper.ProducersMapper;
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
 * Integration tests for the {@link ProducersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProducersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/producers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idProducer}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProducersRepository producersRepository;

    @Autowired
    private ProducersMapper producersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProducersMockMvc;

    private Producers producers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producers createEntity(EntityManager em) {
        Producers producers = new Producers().name(DEFAULT_NAME).createdAt(DEFAULT_CREATED_AT).updatedAt(DEFAULT_UPDATED_AT);
        return producers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producers createUpdatedEntity(EntityManager em) {
        Producers producers = new Producers().name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        return producers;
    }

    @BeforeEach
    public void initTest() {
        producers = createEntity(em);
    }

    @Test
    @Transactional
    void createProducers() throws Exception {
        int databaseSizeBeforeCreate = producersRepository.findAll().size();
        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);
        restProducersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producersDTO)))
            .andExpect(status().isCreated());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeCreate + 1);
        Producers testProducers = producersList.get(producersList.size() - 1);
        assertThat(testProducers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducers.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProducers.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createProducersWithExistingId() throws Exception {
        // Create the Producers with an existing ID
        producers.setIdProducer(1L);
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        int databaseSizeBeforeCreate = producersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProducersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducers() throws Exception {
        // Initialize the database
        producersRepository.saveAndFlush(producers);

        // Get all the producersList
        restProducersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idProducer,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idProducer").value(hasItem(producers.getIdProducer().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getProducers() throws Exception {
        // Initialize the database
        producersRepository.saveAndFlush(producers);

        // Get the producers
        restProducersMockMvc
            .perform(get(ENTITY_API_URL_ID, producers.getIdProducer()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idProducer").value(producers.getIdProducer().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingProducers() throws Exception {
        // Get the producers
        restProducersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProducers() throws Exception {
        // Initialize the database
        producersRepository.saveAndFlush(producers);

        int databaseSizeBeforeUpdate = producersRepository.findAll().size();

        // Update the producers
        Producers updatedProducers = producersRepository.findById(producers.getIdProducer()).get();
        // Disconnect from session so that the updates on updatedProducers are not directly saved in db
        em.detach(updatedProducers);
        updatedProducers.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        ProducersDTO producersDTO = producersMapper.toDto(updatedProducers);

        restProducersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, producersDTO.getIdProducer())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
        Producers testProducers = producersList.get(producersList.size() - 1);
        assertThat(testProducers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingProducers() throws Exception {
        int databaseSizeBeforeUpdate = producersRepository.findAll().size();
        producers.setIdProducer(count.incrementAndGet());

        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProducersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, producersDTO.getIdProducer())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducers() throws Exception {
        int databaseSizeBeforeUpdate = producersRepository.findAll().size();
        producers.setIdProducer(count.incrementAndGet());

        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducers() throws Exception {
        int databaseSizeBeforeUpdate = producersRepository.findAll().size();
        producers.setIdProducer(count.incrementAndGet());

        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProducersWithPatch() throws Exception {
        // Initialize the database
        producersRepository.saveAndFlush(producers);

        int databaseSizeBeforeUpdate = producersRepository.findAll().size();

        // Update the producers using partial update
        Producers partialUpdatedProducers = new Producers();
        partialUpdatedProducers.setIdProducer(producers.getIdProducer());

        partialUpdatedProducers.createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restProducersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducers.getIdProducer())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducers))
            )
            .andExpect(status().isOk());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
        Producers testProducers = producersList.get(producersList.size() - 1);
        assertThat(testProducers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateProducersWithPatch() throws Exception {
        // Initialize the database
        producersRepository.saveAndFlush(producers);

        int databaseSizeBeforeUpdate = producersRepository.findAll().size();

        // Update the producers using partial update
        Producers partialUpdatedProducers = new Producers();
        partialUpdatedProducers.setIdProducer(producers.getIdProducer());

        partialUpdatedProducers.name(UPDATED_NAME).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restProducersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducers.getIdProducer())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducers))
            )
            .andExpect(status().isOk());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
        Producers testProducers = producersList.get(producersList.size() - 1);
        assertThat(testProducers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducers.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducers.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingProducers() throws Exception {
        int databaseSizeBeforeUpdate = producersRepository.findAll().size();
        producers.setIdProducer(count.incrementAndGet());

        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProducersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, producersDTO.getIdProducer())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(producersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducers() throws Exception {
        int databaseSizeBeforeUpdate = producersRepository.findAll().size();
        producers.setIdProducer(count.incrementAndGet());

        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(producersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducers() throws Exception {
        int databaseSizeBeforeUpdate = producersRepository.findAll().size();
        producers.setIdProducer(count.incrementAndGet());

        // Create the Producers
        ProducersDTO producersDTO = producersMapper.toDto(producers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(producersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producers in the database
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducers() throws Exception {
        // Initialize the database
        producersRepository.saveAndFlush(producers);

        int databaseSizeBeforeDelete = producersRepository.findAll().size();

        // Delete the producers
        restProducersMockMvc
            .perform(delete(ENTITY_API_URL_ID, producers.getIdProducer()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producers> producersList = producersRepository.findAll();
        assertThat(producersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
