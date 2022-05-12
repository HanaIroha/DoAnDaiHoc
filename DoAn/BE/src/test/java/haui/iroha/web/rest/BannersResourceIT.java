package haui.iroha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.Banners;
import haui.iroha.repository.BannersRepository;
import haui.iroha.service.dto.BannersDTO;
import haui.iroha.service.mapper.BannersMapper;
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
 * Integration tests for the {@link BannersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BannersResourceIT {

    private static final String DEFAULT_UPPER_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_UPPER_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_NAVIGATE = "AAAAAAAAAA";
    private static final String UPDATED_LINK_NAVIGATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idBanner}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BannersRepository bannersRepository;

    @Autowired
    private BannersMapper bannersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannersMockMvc;

    private Banners banners;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banners createEntity(EntityManager em) {
        Banners banners = new Banners()
            .upperTitle(DEFAULT_UPPER_TITLE)
            .mainTitle(DEFAULT_MAIN_TITLE)
            .image(DEFAULT_IMAGE)
            .linkNavigate(DEFAULT_LINK_NAVIGATE);
        return banners;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banners createUpdatedEntity(EntityManager em) {
        Banners banners = new Banners()
            .upperTitle(UPDATED_UPPER_TITLE)
            .mainTitle(UPDATED_MAIN_TITLE)
            .image(UPDATED_IMAGE)
            .linkNavigate(UPDATED_LINK_NAVIGATE);
        return banners;
    }

    @BeforeEach
    public void initTest() {
        banners = createEntity(em);
    }

    @Test
    @Transactional
    void createBanners() throws Exception {
        int databaseSizeBeforeCreate = bannersRepository.findAll().size();
        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);
        restBannersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bannersDTO)))
            .andExpect(status().isCreated());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeCreate + 1);
        Banners testBanners = bannersList.get(bannersList.size() - 1);
        assertThat(testBanners.getUpperTitle()).isEqualTo(DEFAULT_UPPER_TITLE);
        assertThat(testBanners.getMainTitle()).isEqualTo(DEFAULT_MAIN_TITLE);
        assertThat(testBanners.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBanners.getLinkNavigate()).isEqualTo(DEFAULT_LINK_NAVIGATE);
    }

    @Test
    @Transactional
    void createBannersWithExistingId() throws Exception {
        // Create the Banners with an existing ID
        banners.setIdBanner(1L);
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        int databaseSizeBeforeCreate = bannersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bannersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanners() throws Exception {
        // Initialize the database
        bannersRepository.saveAndFlush(banners);

        // Get all the bannersList
        restBannersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idBanner,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idBanner").value(hasItem(banners.getIdBanner().intValue())))
            .andExpect(jsonPath("$.[*].upperTitle").value(hasItem(DEFAULT_UPPER_TITLE)))
            .andExpect(jsonPath("$.[*].mainTitle").value(hasItem(DEFAULT_MAIN_TITLE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].linkNavigate").value(hasItem(DEFAULT_LINK_NAVIGATE)));
    }

    @Test
    @Transactional
    void getBanners() throws Exception {
        // Initialize the database
        bannersRepository.saveAndFlush(banners);

        // Get the banners
        restBannersMockMvc
            .perform(get(ENTITY_API_URL_ID, banners.getIdBanner()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idBanner").value(banners.getIdBanner().intValue()))
            .andExpect(jsonPath("$.upperTitle").value(DEFAULT_UPPER_TITLE))
            .andExpect(jsonPath("$.mainTitle").value(DEFAULT_MAIN_TITLE))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.linkNavigate").value(DEFAULT_LINK_NAVIGATE));
    }

    @Test
    @Transactional
    void getNonExistingBanners() throws Exception {
        // Get the banners
        restBannersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBanners() throws Exception {
        // Initialize the database
        bannersRepository.saveAndFlush(banners);

        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();

        // Update the banners
        Banners updatedBanners = bannersRepository.findById(banners.getIdBanner()).get();
        // Disconnect from session so that the updates on updatedBanners are not directly saved in db
        em.detach(updatedBanners);
        updatedBanners
            .upperTitle(UPDATED_UPPER_TITLE)
            .mainTitle(UPDATED_MAIN_TITLE)
            .image(UPDATED_IMAGE)
            .linkNavigate(UPDATED_LINK_NAVIGATE);
        BannersDTO bannersDTO = bannersMapper.toDto(updatedBanners);

        restBannersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bannersDTO.getIdBanner())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bannersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
        Banners testBanners = bannersList.get(bannersList.size() - 1);
        assertThat(testBanners.getUpperTitle()).isEqualTo(UPDATED_UPPER_TITLE);
        assertThat(testBanners.getMainTitle()).isEqualTo(UPDATED_MAIN_TITLE);
        assertThat(testBanners.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBanners.getLinkNavigate()).isEqualTo(UPDATED_LINK_NAVIGATE);
    }

    @Test
    @Transactional
    void putNonExistingBanners() throws Exception {
        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();
        banners.setIdBanner(count.incrementAndGet());

        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bannersDTO.getIdBanner())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bannersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanners() throws Exception {
        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();
        banners.setIdBanner(count.incrementAndGet());

        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bannersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanners() throws Exception {
        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();
        banners.setIdBanner(count.incrementAndGet());

        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bannersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBannersWithPatch() throws Exception {
        // Initialize the database
        bannersRepository.saveAndFlush(banners);

        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();

        // Update the banners using partial update
        Banners partialUpdatedBanners = new Banners();
        partialUpdatedBanners.setIdBanner(banners.getIdBanner());

        partialUpdatedBanners.upperTitle(UPDATED_UPPER_TITLE);

        restBannersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanners.getIdBanner())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanners))
            )
            .andExpect(status().isOk());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
        Banners testBanners = bannersList.get(bannersList.size() - 1);
        assertThat(testBanners.getUpperTitle()).isEqualTo(UPDATED_UPPER_TITLE);
        assertThat(testBanners.getMainTitle()).isEqualTo(DEFAULT_MAIN_TITLE);
        assertThat(testBanners.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBanners.getLinkNavigate()).isEqualTo(DEFAULT_LINK_NAVIGATE);
    }

    @Test
    @Transactional
    void fullUpdateBannersWithPatch() throws Exception {
        // Initialize the database
        bannersRepository.saveAndFlush(banners);

        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();

        // Update the banners using partial update
        Banners partialUpdatedBanners = new Banners();
        partialUpdatedBanners.setIdBanner(banners.getIdBanner());

        partialUpdatedBanners
            .upperTitle(UPDATED_UPPER_TITLE)
            .mainTitle(UPDATED_MAIN_TITLE)
            .image(UPDATED_IMAGE)
            .linkNavigate(UPDATED_LINK_NAVIGATE);

        restBannersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanners.getIdBanner())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanners))
            )
            .andExpect(status().isOk());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
        Banners testBanners = bannersList.get(bannersList.size() - 1);
        assertThat(testBanners.getUpperTitle()).isEqualTo(UPDATED_UPPER_TITLE);
        assertThat(testBanners.getMainTitle()).isEqualTo(UPDATED_MAIN_TITLE);
        assertThat(testBanners.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBanners.getLinkNavigate()).isEqualTo(UPDATED_LINK_NAVIGATE);
    }

    @Test
    @Transactional
    void patchNonExistingBanners() throws Exception {
        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();
        banners.setIdBanner(count.incrementAndGet());

        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bannersDTO.getIdBanner())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bannersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanners() throws Exception {
        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();
        banners.setIdBanner(count.incrementAndGet());

        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bannersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanners() throws Exception {
        int databaseSizeBeforeUpdate = bannersRepository.findAll().size();
        banners.setIdBanner(count.incrementAndGet());

        // Create the Banners
        BannersDTO bannersDTO = bannersMapper.toDto(banners);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bannersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banners in the database
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanners() throws Exception {
        // Initialize the database
        bannersRepository.saveAndFlush(banners);

        int databaseSizeBeforeDelete = bannersRepository.findAll().size();

        // Delete the banners
        restBannersMockMvc
            .perform(delete(ENTITY_API_URL_ID, banners.getIdBanner()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banners> bannersList = bannersRepository.findAll();
        assertThat(bannersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
