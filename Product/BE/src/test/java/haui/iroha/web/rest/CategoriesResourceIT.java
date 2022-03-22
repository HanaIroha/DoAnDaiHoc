package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.Categories;
import haui.iroha.repository.CategoriesRepository;
import haui.iroha.service.dto.CategoriesDTO;
import haui.iroha.service.mapper.CategoriesMapper;
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
 * Integration tests for the {@link CategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idCategory}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesMockMvc;

    private Categories categories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createEntity(EntityManager em) {
        Categories categories = new Categories()
            .name(DEFAULT_NAME)
            .parentId(DEFAULT_PARENT_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return categories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createUpdatedEntity(EntityManager em) {
        Categories categories = new Categories()
            .name(UPDATED_NAME)
            .parentId(UPDATED_PARENT_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return categories;
    }

    @BeforeEach
    public void initTest() {
        categories = createEntity(em);
    }

    @Test
    @Transactional
    void createCategories() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();
        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);
        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategories.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testCategories.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCategories.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCategoriesWithExistingId() throws Exception {
        // Create the Categories with an existing ID
        categories.setIdCategory(1L);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idCategory,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idCategory").value(hasItem(categories.getIdCategory().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get the categories
        restCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, categories.getIdCategory()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idCategory").value(categories.getIdCategory().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingCategories() throws Exception {
        // Get the categories
        restCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories
        Categories updatedCategories = categoriesRepository.findById(categories.getIdCategory()).get();
        // Disconnect from session so that the updates on updatedCategories are not directly saved in db
        em.detach(updatedCategories);
        updatedCategories.name(UPDATED_NAME).parentId(UPDATED_PARENT_ID).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(updatedCategories);

        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriesDTO.getIdCategory())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategories.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setIdCategory(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriesDTO.getIdCategory())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setIdCategory(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setIdCategory(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoriesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriesWithPatch() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories using partial update
        Categories partialUpdatedCategories = new Categories();
        partialUpdatedCategories.setIdCategory(categories.getIdCategory());

        partialUpdatedCategories.parentId(UPDATED_PARENT_ID).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategories.getIdCategory())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategories.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCategoriesWithPatch() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories using partial update
        Categories partialUpdatedCategories = new Categories();
        partialUpdatedCategories.setIdCategory(categories.getIdCategory());

        partialUpdatedCategories.name(UPDATED_NAME).parentId(UPDATED_PARENT_ID).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategories.getIdCategory())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategories))
            )
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategories.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCategories.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCategories.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setIdCategory(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriesDTO.getIdCategory())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setIdCategory(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();
        categories.setIdCategory(count.incrementAndGet());

        // Create the Categories
        CategoriesDTO categoriesDTO = categoriesMapper.toDto(categories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeDelete = categoriesRepository.findAll().size();

        // Delete the categories
        restCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, categories.getIdCategory()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
