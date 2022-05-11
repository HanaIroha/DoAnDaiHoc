package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.Products;
import haui.iroha.repository.ProductsRepository;
import haui.iroha.service.dto.ProductsDTO;
import haui.iroha.service.mapper.ProductsMapper;
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
 * Integration tests for the {@link ProductsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductsResourceIT {

    private static final Long DEFAULT_ID_CATEGORY = 1L;
    private static final Long UPDATED_ID_CATEGORY = 2L;

    private static final Long DEFAULT_ID_PRODUCER = 1L;
    private static final Long UPDATED_ID_PRODUCER = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final Long DEFAULT_SALE_PERCENT = 1L;
    private static final Long UPDATED_SALE_PERCENT = 2L;

    private static final String DEFAULT_SUPPORT_SIM = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORT_SIM = "BBBBBBBBBB";

    private static final String DEFAULT_MONITOR = "AAAAAAAAAA";
    private static final String UPDATED_MONITOR = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT_CAMERA = "AAAAAAAAAA";
    private static final String UPDATED_FRONT_CAMERA = "BBBBBBBBBB";

    private static final String DEFAULT_REAR_CAMERA = "AAAAAAAAAA";
    private static final String UPDATED_REAR_CAMERA = "BBBBBBBBBB";

    private static final String DEFAULT_C_PU = "AAAAAAAAAA";
    private static final String UPDATED_C_PU = "BBBBBBBBBB";

    private static final String DEFAULT_G_PU = "AAAAAAAAAA";
    private static final String UPDATED_G_PU = "BBBBBBBBBB";

    private static final String DEFAULT_R_AM = "AAAAAAAAAA";
    private static final String UPDATED_R_AM = "BBBBBBBBBB";

    private static final String DEFAULT_R_OM = "AAAAAAAAAA";
    private static final String UPDATED_R_OM = "BBBBBBBBBB";

    private static final String DEFAULT_O_S = "AAAAAAAAAA";
    private static final String UPDATED_O_S = "BBBBBBBBBB";

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION_DETAILS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_DISABLE = false;
    private static final Boolean UPDATED_IS_DISABLE = true;

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idProduct}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsMockMvc;

    private Products products;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createEntity(EntityManager em) {
        Products products = new Products()
            .idCategory(DEFAULT_ID_CATEGORY)
            .idProducer(DEFAULT_ID_PRODUCER)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .code(DEFAULT_CODE)
            .price(DEFAULT_PRICE)
            .salePercent(DEFAULT_SALE_PERCENT)
            .supportSim(DEFAULT_SUPPORT_SIM)
            .monitor(DEFAULT_MONITOR)
            .color(DEFAULT_COLOR)
            .frontCamera(DEFAULT_FRONT_CAMERA)
            .rearCamera(DEFAULT_REAR_CAMERA)
            .cPU(DEFAULT_C_PU)
            .gPU(DEFAULT_G_PU)
            .rAM(DEFAULT_R_AM)
            .rOM(DEFAULT_R_OM)
            .oS(DEFAULT_O_S)
            .pin(DEFAULT_PIN)
            .informationDetails(DEFAULT_INFORMATION_DETAILS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .isDisable(DEFAULT_IS_DISABLE);
        return products;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createUpdatedEntity(EntityManager em) {
        Products products = new Products()
            .idCategory(UPDATED_ID_CATEGORY)
            .idProducer(UPDATED_ID_PRODUCER)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .code(UPDATED_CODE)
            .price(UPDATED_PRICE)
            .salePercent(UPDATED_SALE_PERCENT)
            .supportSim(UPDATED_SUPPORT_SIM)
            .monitor(UPDATED_MONITOR)
            .color(UPDATED_COLOR)
            .frontCamera(UPDATED_FRONT_CAMERA)
            .rearCamera(UPDATED_REAR_CAMERA)
            .cPU(UPDATED_C_PU)
            .gPU(UPDATED_G_PU)
            .rAM(UPDATED_R_AM)
            .rOM(UPDATED_R_OM)
            .oS(UPDATED_O_S)
            .pin(UPDATED_PIN)
            .informationDetails(UPDATED_INFORMATION_DETAILS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDisable(UPDATED_IS_DISABLE);
        return products;
    }

    @BeforeEach
    public void initTest() {
        products = createEntity(em);
    }

    @Test
    @Transactional
    void createProducts() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();
        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);
        restProductsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isCreated());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate + 1);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getIdCategory()).isEqualTo(DEFAULT_ID_CATEGORY);
        assertThat(testProducts.getIdProducer()).isEqualTo(DEFAULT_ID_PRODUCER);
        assertThat(testProducts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducts.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProducts.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProducts.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProducts.getSalePercent()).isEqualTo(DEFAULT_SALE_PERCENT);
        assertThat(testProducts.getSupportSim()).isEqualTo(DEFAULT_SUPPORT_SIM);
        assertThat(testProducts.getMonitor()).isEqualTo(DEFAULT_MONITOR);
        assertThat(testProducts.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProducts.getFrontCamera()).isEqualTo(DEFAULT_FRONT_CAMERA);
        assertThat(testProducts.getRearCamera()).isEqualTo(DEFAULT_REAR_CAMERA);
        assertThat(testProducts.getcPU()).isEqualTo(DEFAULT_C_PU);
        assertThat(testProducts.getgPU()).isEqualTo(DEFAULT_G_PU);
        assertThat(testProducts.getrAM()).isEqualTo(DEFAULT_R_AM);
        assertThat(testProducts.getrOM()).isEqualTo(DEFAULT_R_OM);
        assertThat(testProducts.getoS()).isEqualTo(DEFAULT_O_S);
        assertThat(testProducts.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testProducts.getInformationDetails()).isEqualTo(DEFAULT_INFORMATION_DETAILS);
        assertThat(testProducts.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProducts.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testProducts.getIsDisable()).isEqualTo(DEFAULT_IS_DISABLE);
    }

    @Test
    @Transactional
    void createProductsWithExistingId() throws Exception {
        // Create the Products with an existing ID
        products.setIdProduct(1L);
        ProductsDTO productsDTO = productsMapper.toDto(products);

        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList
        restProductsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idProduct,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(products.getIdProduct().intValue())))
            .andExpect(jsonPath("$.[*].idCategory").value(hasItem(DEFAULT_ID_CATEGORY.intValue())))
            .andExpect(jsonPath("$.[*].idProducer").value(hasItem(DEFAULT_ID_PRODUCER.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].salePercent").value(hasItem(DEFAULT_SALE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].supportSim").value(hasItem(DEFAULT_SUPPORT_SIM)))
            .andExpect(jsonPath("$.[*].monitor").value(hasItem(DEFAULT_MONITOR)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].frontCamera").value(hasItem(DEFAULT_FRONT_CAMERA)))
            .andExpect(jsonPath("$.[*].rearCamera").value(hasItem(DEFAULT_REAR_CAMERA)))
            .andExpect(jsonPath("$.[*].cPU").value(hasItem(DEFAULT_C_PU)))
            .andExpect(jsonPath("$.[*].gPU").value(hasItem(DEFAULT_G_PU)))
            .andExpect(jsonPath("$.[*].rAM").value(hasItem(DEFAULT_R_AM)))
            .andExpect(jsonPath("$.[*].rOM").value(hasItem(DEFAULT_R_OM)))
            .andExpect(jsonPath("$.[*].oS").value(hasItem(DEFAULT_O_S)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].informationDetails").value(hasItem(DEFAULT_INFORMATION_DETAILS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].isDisable").value(hasItem(DEFAULT_IS_DISABLE.booleanValue())));
    }

    @Test
    @Transactional
    void getProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get the products
        restProductsMockMvc
            .perform(get(ENTITY_API_URL_ID, products.getIdProduct()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idProduct").value(products.getIdProduct().intValue()))
            .andExpect(jsonPath("$.idCategory").value(DEFAULT_ID_CATEGORY.intValue()))
            .andExpect(jsonPath("$.idProducer").value(DEFAULT_ID_PRODUCER.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.salePercent").value(DEFAULT_SALE_PERCENT.intValue()))
            .andExpect(jsonPath("$.supportSim").value(DEFAULT_SUPPORT_SIM))
            .andExpect(jsonPath("$.monitor").value(DEFAULT_MONITOR))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.frontCamera").value(DEFAULT_FRONT_CAMERA))
            .andExpect(jsonPath("$.rearCamera").value(DEFAULT_REAR_CAMERA))
            .andExpect(jsonPath("$.cPU").value(DEFAULT_C_PU))
            .andExpect(jsonPath("$.gPU").value(DEFAULT_G_PU))
            .andExpect(jsonPath("$.rAM").value(DEFAULT_R_AM))
            .andExpect(jsonPath("$.rOM").value(DEFAULT_R_OM))
            .andExpect(jsonPath("$.oS").value(DEFAULT_O_S))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.informationDetails").value(DEFAULT_INFORMATION_DETAILS))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.isDisable").value(DEFAULT_IS_DISABLE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProducts() throws Exception {
        // Get the products
        restProductsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products
        Products updatedProducts = productsRepository.findById(products.getIdProduct()).get();
        // Disconnect from session so that the updates on updatedProducts are not directly saved in db
        em.detach(updatedProducts);
        updatedProducts
            .idCategory(UPDATED_ID_CATEGORY)
            .idProducer(UPDATED_ID_PRODUCER)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .code(UPDATED_CODE)
            .price(UPDATED_PRICE)
            .salePercent(UPDATED_SALE_PERCENT)
            .supportSim(UPDATED_SUPPORT_SIM)
            .monitor(UPDATED_MONITOR)
            .color(UPDATED_COLOR)
            .frontCamera(UPDATED_FRONT_CAMERA)
            .rearCamera(UPDATED_REAR_CAMERA)
            .cPU(UPDATED_C_PU)
            .gPU(UPDATED_G_PU)
            .rAM(UPDATED_R_AM)
            .rOM(UPDATED_R_OM)
            .oS(UPDATED_O_S)
            .pin(UPDATED_PIN)
            .informationDetails(UPDATED_INFORMATION_DETAILS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDisable(UPDATED_IS_DISABLE);
        ProductsDTO productsDTO = productsMapper.toDto(updatedProducts);

        restProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productsDTO.getIdProduct())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getIdCategory()).isEqualTo(UPDATED_ID_CATEGORY);
        assertThat(testProducts.getIdProducer()).isEqualTo(UPDATED_ID_PRODUCER);
        assertThat(testProducts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducts.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProducts.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProducts.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProducts.getSalePercent()).isEqualTo(UPDATED_SALE_PERCENT);
        assertThat(testProducts.getSupportSim()).isEqualTo(UPDATED_SUPPORT_SIM);
        assertThat(testProducts.getMonitor()).isEqualTo(UPDATED_MONITOR);
        assertThat(testProducts.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducts.getFrontCamera()).isEqualTo(UPDATED_FRONT_CAMERA);
        assertThat(testProducts.getRearCamera()).isEqualTo(UPDATED_REAR_CAMERA);
        assertThat(testProducts.getcPU()).isEqualTo(UPDATED_C_PU);
        assertThat(testProducts.getgPU()).isEqualTo(UPDATED_G_PU);
        assertThat(testProducts.getrAM()).isEqualTo(UPDATED_R_AM);
        assertThat(testProducts.getrOM()).isEqualTo(UPDATED_R_OM);
        assertThat(testProducts.getoS()).isEqualTo(UPDATED_O_S);
        assertThat(testProducts.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testProducts.getInformationDetails()).isEqualTo(UPDATED_INFORMATION_DETAILS);
        assertThat(testProducts.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducts.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testProducts.getIsDisable()).isEqualTo(UPDATED_IS_DISABLE);
    }

    @Test
    @Transactional
    void putNonExistingProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();
        products.setIdProduct(count.incrementAndGet());

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productsDTO.getIdProduct())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();
        products.setIdProduct(count.incrementAndGet());

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();
        products.setIdProduct(count.incrementAndGet());

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductsWithPatch() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products using partial update
        Products partialUpdatedProducts = new Products();
        partialUpdatedProducts.setIdProduct(products.getIdProduct());

        partialUpdatedProducts
            .idCategory(UPDATED_ID_CATEGORY)
            .name(UPDATED_NAME)
            .supportSim(UPDATED_SUPPORT_SIM)
            .monitor(UPDATED_MONITOR)
            .color(UPDATED_COLOR)
            .rearCamera(UPDATED_REAR_CAMERA)
            .cPU(UPDATED_C_PU)
            .gPU(UPDATED_G_PU)
            .oS(UPDATED_O_S)
            .informationDetails(UPDATED_INFORMATION_DETAILS)
            .createdAt(UPDATED_CREATED_AT)
            .isDisable(UPDATED_IS_DISABLE);

        restProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducts.getIdProduct())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducts))
            )
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getIdCategory()).isEqualTo(UPDATED_ID_CATEGORY);
        assertThat(testProducts.getIdProducer()).isEqualTo(DEFAULT_ID_PRODUCER);
        assertThat(testProducts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducts.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProducts.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProducts.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProducts.getSalePercent()).isEqualTo(DEFAULT_SALE_PERCENT);
        assertThat(testProducts.getSupportSim()).isEqualTo(UPDATED_SUPPORT_SIM);
        assertThat(testProducts.getMonitor()).isEqualTo(UPDATED_MONITOR);
        assertThat(testProducts.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducts.getFrontCamera()).isEqualTo(DEFAULT_FRONT_CAMERA);
        assertThat(testProducts.getRearCamera()).isEqualTo(UPDATED_REAR_CAMERA);
        assertThat(testProducts.getcPU()).isEqualTo(UPDATED_C_PU);
        assertThat(testProducts.getgPU()).isEqualTo(UPDATED_G_PU);
        assertThat(testProducts.getrAM()).isEqualTo(DEFAULT_R_AM);
        assertThat(testProducts.getrOM()).isEqualTo(DEFAULT_R_OM);
        assertThat(testProducts.getoS()).isEqualTo(UPDATED_O_S);
        assertThat(testProducts.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testProducts.getInformationDetails()).isEqualTo(UPDATED_INFORMATION_DETAILS);
        assertThat(testProducts.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducts.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testProducts.getIsDisable()).isEqualTo(UPDATED_IS_DISABLE);
    }

    @Test
    @Transactional
    void fullUpdateProductsWithPatch() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products using partial update
        Products partialUpdatedProducts = new Products();
        partialUpdatedProducts.setIdProduct(products.getIdProduct());

        partialUpdatedProducts
            .idCategory(UPDATED_ID_CATEGORY)
            .idProducer(UPDATED_ID_PRODUCER)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .code(UPDATED_CODE)
            .price(UPDATED_PRICE)
            .salePercent(UPDATED_SALE_PERCENT)
            .supportSim(UPDATED_SUPPORT_SIM)
            .monitor(UPDATED_MONITOR)
            .color(UPDATED_COLOR)
            .frontCamera(UPDATED_FRONT_CAMERA)
            .rearCamera(UPDATED_REAR_CAMERA)
            .cPU(UPDATED_C_PU)
            .gPU(UPDATED_G_PU)
            .rAM(UPDATED_R_AM)
            .rOM(UPDATED_R_OM)
            .oS(UPDATED_O_S)
            .pin(UPDATED_PIN)
            .informationDetails(UPDATED_INFORMATION_DETAILS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isDisable(UPDATED_IS_DISABLE);

        restProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducts.getIdProduct())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducts))
            )
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getIdCategory()).isEqualTo(UPDATED_ID_CATEGORY);
        assertThat(testProducts.getIdProducer()).isEqualTo(UPDATED_ID_PRODUCER);
        assertThat(testProducts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducts.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProducts.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProducts.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProducts.getSalePercent()).isEqualTo(UPDATED_SALE_PERCENT);
        assertThat(testProducts.getSupportSim()).isEqualTo(UPDATED_SUPPORT_SIM);
        assertThat(testProducts.getMonitor()).isEqualTo(UPDATED_MONITOR);
        assertThat(testProducts.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducts.getFrontCamera()).isEqualTo(UPDATED_FRONT_CAMERA);
        assertThat(testProducts.getRearCamera()).isEqualTo(UPDATED_REAR_CAMERA);
        assertThat(testProducts.getcPU()).isEqualTo(UPDATED_C_PU);
        assertThat(testProducts.getgPU()).isEqualTo(UPDATED_G_PU);
        assertThat(testProducts.getrAM()).isEqualTo(UPDATED_R_AM);
        assertThat(testProducts.getrOM()).isEqualTo(UPDATED_R_OM);
        assertThat(testProducts.getoS()).isEqualTo(UPDATED_O_S);
        assertThat(testProducts.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testProducts.getInformationDetails()).isEqualTo(UPDATED_INFORMATION_DETAILS);
        assertThat(testProducts.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducts.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testProducts.getIsDisable()).isEqualTo(UPDATED_IS_DISABLE);
    }

    @Test
    @Transactional
    void patchNonExistingProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();
        products.setIdProduct(count.incrementAndGet());

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productsDTO.getIdProduct())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();
        products.setIdProduct(count.incrementAndGet());

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();
        products.setIdProduct(count.incrementAndGet());

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeDelete = productsRepository.findAll().size();

        // Delete the products
        restProductsMockMvc
            .perform(delete(ENTITY_API_URL_ID, products.getIdProduct()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
