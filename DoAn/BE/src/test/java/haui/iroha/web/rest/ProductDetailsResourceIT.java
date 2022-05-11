package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.ProductDetails;
import haui.iroha.repository.ProductDetailsRepository;
import haui.iroha.service.dto.ProductDetailsDTO;
import haui.iroha.service.mapper.ProductDetailsMapper;
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
 * Integration tests for the {@link ProductDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductDetailsResourceIT {

    private static final Long DEFAULT_ID_PRODUCT = 1L;
    private static final Long UPDATED_ID_PRODUCT = 2L;

    private static final String DEFAULT_ROM = "AAAAAAAAAA";
    private static final String UPDATED_ROM = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Long DEFAULT_IMPORT_QUANTITY = 1L;
    private static final Long UPDATED_IMPORT_QUANTITY = 2L;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Long DEFAULT_IMPORT_PRICE = 1L;
    private static final Long UPDATED_IMPORT_PRICE = 2L;

    private static final Long DEFAULT_SALE_PRICE = 1L;
    private static final Long UPDATED_SALE_PRICE = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/product-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idProductDetail}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDetailsMockMvc;

    private ProductDetails productDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails()
            .idProduct(DEFAULT_ID_PRODUCT)
            .rom(DEFAULT_ROM)
            .color(DEFAULT_COLOR)
            .importQuantity(DEFAULT_IMPORT_QUANTITY)
            .quantity(DEFAULT_QUANTITY)
            .importPrice(DEFAULT_IMPORT_PRICE)
            .salePrice(DEFAULT_SALE_PRICE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return productDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createUpdatedEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails()
            .idProduct(UPDATED_ID_PRODUCT)
            .rom(UPDATED_ROM)
            .color(UPDATED_COLOR)
            .importQuantity(UPDATED_IMPORT_QUANTITY)
            .quantity(UPDATED_QUANTITY)
            .importPrice(UPDATED_IMPORT_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return productDetails;
    }

    @BeforeEach
    public void initTest() {
        productDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createProductDetails() throws Exception {
        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();
        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);
        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
        assertThat(testProductDetails.getRom()).isEqualTo(DEFAULT_ROM);
        assertThat(testProductDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductDetails.getImportQuantity()).isEqualTo(DEFAULT_IMPORT_QUANTITY);
        assertThat(testProductDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductDetails.getImportPrice()).isEqualTo(DEFAULT_IMPORT_PRICE);
        assertThat(testProductDetails.getSalePrice()).isEqualTo(DEFAULT_SALE_PRICE);
        assertThat(testProductDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProductDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createProductDetailsWithExistingId() throws Exception {
        // Create the ProductDetails with an existing ID
        productDetails.setIdProductDetail(1L);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idProductDetail,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idProductDetail").value(hasItem(productDetails.getIdProductDetail().intValue())))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(DEFAULT_ID_PRODUCT.intValue())))
            .andExpect(jsonPath("$.[*].rom").value(hasItem(DEFAULT_ROM)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].importQuantity").value(hasItem(DEFAULT_IMPORT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].importPrice").value(hasItem(DEFAULT_IMPORT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get the productDetails
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, productDetails.getIdProductDetail()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idProductDetail").value(productDetails.getIdProductDetail().intValue()))
            .andExpect(jsonPath("$.idProduct").value(DEFAULT_ID_PRODUCT.intValue()))
            .andExpect(jsonPath("$.rom").value(DEFAULT_ROM))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.importQuantity").value(DEFAULT_IMPORT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.importPrice").value(DEFAULT_IMPORT_PRICE.intValue()))
            .andExpect(jsonPath("$.salePrice").value(DEFAULT_SALE_PRICE.intValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingProductDetails() throws Exception {
        // Get the productDetails
        restProductDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails
        ProductDetails updatedProductDetails = productDetailsRepository.findById(productDetails.getIdProductDetail()).get();
        // Disconnect from session so that the updates on updatedProductDetails are not directly saved in db
        em.detach(updatedProductDetails);
        updatedProductDetails
            .idProduct(UPDATED_ID_PRODUCT)
            .rom(UPDATED_ROM)
            .color(UPDATED_COLOR)
            .importQuantity(UPDATED_IMPORT_QUANTITY)
            .quantity(UPDATED_QUANTITY)
            .importPrice(UPDATED_IMPORT_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(updatedProductDetails);

        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDetailsDTO.getIdProductDetail())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testProductDetails.getRom()).isEqualTo(UPDATED_ROM);
        assertThat(testProductDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductDetails.getImportQuantity()).isEqualTo(UPDATED_IMPORT_QUANTITY);
        assertThat(testProductDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductDetails.getImportPrice()).isEqualTo(UPDATED_IMPORT_PRICE);
        assertThat(testProductDetails.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testProductDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProductDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setIdProductDetail(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDetailsDTO.getIdProductDetail())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setIdProductDetail(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setIdProductDetail(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductDetailsWithPatch() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails using partial update
        ProductDetails partialUpdatedProductDetails = new ProductDetails();
        partialUpdatedProductDetails.setIdProductDetail(productDetails.getIdProductDetail());

        partialUpdatedProductDetails.idProduct(UPDATED_ID_PRODUCT).salePrice(UPDATED_SALE_PRICE);

        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDetails.getIdProductDetail())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDetails))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testProductDetails.getRom()).isEqualTo(DEFAULT_ROM);
        assertThat(testProductDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductDetails.getImportQuantity()).isEqualTo(DEFAULT_IMPORT_QUANTITY);
        assertThat(testProductDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductDetails.getImportPrice()).isEqualTo(DEFAULT_IMPORT_PRICE);
        assertThat(testProductDetails.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testProductDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProductDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateProductDetailsWithPatch() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails using partial update
        ProductDetails partialUpdatedProductDetails = new ProductDetails();
        partialUpdatedProductDetails.setIdProductDetail(productDetails.getIdProductDetail());

        partialUpdatedProductDetails
            .idProduct(UPDATED_ID_PRODUCT)
            .rom(UPDATED_ROM)
            .color(UPDATED_COLOR)
            .importQuantity(UPDATED_IMPORT_QUANTITY)
            .quantity(UPDATED_QUANTITY)
            .importPrice(UPDATED_IMPORT_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDetails.getIdProductDetail())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDetails))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testProductDetails.getRom()).isEqualTo(UPDATED_ROM);
        assertThat(testProductDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductDetails.getImportQuantity()).isEqualTo(UPDATED_IMPORT_QUANTITY);
        assertThat(testProductDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductDetails.getImportPrice()).isEqualTo(UPDATED_IMPORT_PRICE);
        assertThat(testProductDetails.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testProductDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProductDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setIdProductDetail(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDetailsDTO.getIdProductDetail())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setIdProductDetail(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setIdProductDetail(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeDelete = productDetailsRepository.findAll().size();

        // Delete the productDetails
        restProductDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, productDetails.getIdProductDetail()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
