package haui.iroha.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import haui.iroha.domain.Products;
import haui.iroha.repository.ProductsRepository;
import haui.iroha.service.ProductsService;
import haui.iroha.service.dto.ProductsDTO;
import haui.iroha.web.rest.errors.BadRequestAlertException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link haui.iroha.domain.Products}.
 */
@RestController
@RequestMapping("/api")
public class ProductsResource {

    @Value("${imageStoragePath}")
    private String imageStoragePath;

    private final Logger log = LoggerFactory.getLogger(ProductsResource.class);

    private static final String ENTITY_NAME = "products";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductsService productsService;

    private final ProductsRepository productsRepository;

    public ProductsResource(ProductsService productsService, ProductsRepository productsRepository) {
        this.productsService = productsService;
        this.productsRepository = productsRepository;
    }

    /**
     * {@code POST  /products} : Create a new products.
     *
     * @param productsDTO the productsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productsDTO, or with status {@code 400 (Bad Request)} if the products has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/products", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductsDTO> createProducts(@RequestParam("product") String product, @RequestParam(value = "image",required = false) MultipartFile image) throws URISyntaxException {
        ProductsDTO productsDTO;
        try {
            productsDTO = new ObjectMapper().readValue(product, ProductsDTO.class);
        } catch (Exception ex) {
            throw new BadRequestAlertException("undentity Exception", ENTITY_NAME, "error");
        }
        log.debug("REST request to save Products : {}", productsDTO);
        if (productsDTO.getIdProduct() != null) {
            throw new BadRequestAlertException("A new products cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(image != null){
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            fileName = System.currentTimeMillis() + fileName;
            productsDTO.setImage(fileName);
            //upload image
            Path uploadPath = Paths.get(imageStoragePath);
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                InputStream inputStream = image.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productsDTO.setLastPrice(productsDTO.getSalePercent()!=null?productsDTO.getPrice()/100*(100-productsDTO.getSalePercent()):productsDTO.getPrice());
        productsDTO.setCreatedAt(ZonedDateTime.now());
        productsDTO.setUpdatedAt(ZonedDateTime.now());


        productsDTO.setIsDisable(false);

        ProductsDTO result = productsService.save(productsDTO);



        return ResponseEntity
            .created(new URI("/api/products/" + result.getIdProduct()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdProduct().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /products/:idProduct} : Updates an existing products.
     *
     * @param idProduct the id of the productsDTO to save.
     * @param productsDTO the productsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsDTO,
     * or with status {@code 400 (Bad Request)} if the productsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/products/{idProduct}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductsDTO> updateProducts(
        @PathVariable(value = "idProduct", required = false) final Long idProduct,
        @RequestParam("product") String product, @RequestParam(value = "image",required = false) MultipartFile image
    ) throws URISyntaxException {
        ProductsDTO productsDTO;
        try {
            productsDTO = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(product, ProductsDTO.class);
        } catch (Exception ex) {
            throw new BadRequestAlertException("undentity Exception", ENTITY_NAME, "error");
        }

        log.debug("REST request to update Products : {}, {}", idProduct, productsDTO);
        if (productsDTO.getIdProduct() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProduct, productsDTO.getIdProduct())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productsRepository.existsById(idProduct)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if(image != null){
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            fileName = System.currentTimeMillis() + fileName;
            productsDTO.setImage(fileName);
            //upload image
            Path uploadPath = Paths.get(imageStoragePath);
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                InputStream inputStream = image.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productsDTO.setLastPrice(productsDTO.getSalePercent()!=null?productsDTO.getPrice()/100*(100-productsDTO.getSalePercent()):productsDTO.getPrice());
        productsDTO.setUpdatedAt(ZonedDateTime.now());

        ProductsDTO result = productsService.save(productsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productsDTO.getIdProduct().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /products/:idProduct} : Partial updates given fields of an existing products, field will ignore if it is null
     *
     * @param idProduct the id of the productsDTO to save.
     * @param productsDTO the productsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsDTO,
     * or with status {@code 400 (Bad Request)} if the productsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/products/{idProduct}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductsDTO> partialUpdateProducts(
        @PathVariable(value = "idProduct", required = false) final Long idProduct,
        @RequestBody ProductsDTO productsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Products partially : {}, {}", idProduct, productsDTO);
        if (productsDTO.getIdProduct() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProduct, productsDTO.getIdProduct())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productsRepository.existsById(idProduct)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductsDTO> result = productsService.partialUpdate(productsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productsDTO.getIdProduct().toString())
        );
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductsDTO>> getAllProducts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Products");
        Page<ProductsDTO> page = productsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/productsActive/{filterKey}/{filterProducer}/{filterCategory}")
    public ResponseEntity<List<ProductsDTO>> getAllProductsActive(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
                                                                  @PathVariable(name = "filterKey", required = false) String filterKey,
                                                                  @PathVariable(name = "filterProducer", required = false) String filterProducer,
                                                                  @PathVariable(name = "filterCategory", required = false) String filterCategory) {
        if(filterKey.equals("0"))
            filterKey="%";

        if(filterProducer.equals("0"))
            filterProducer = "%";

        if(filterCategory.equals("0"))
            filterCategory = "%";

        log.debug("REST request to get a page of Active Products");
        Page<ProductsDTO> page = productsService.findAllActiveWithFilter(filterKey, filterProducer, filterCategory, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/productsActivez")
    public ResponseEntity<List<ProductsDTO>> getAllProductsActivez(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Active Products");
        Page<ProductsDTO> page = productsService.findAllActive(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /products/:id} : get the "id" products.
     *
     * @param id the id of the productsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductsDTO> getProducts(@PathVariable Long id) {
        log.debug("REST request to get Products : {}", id);
        Optional<ProductsDTO> productsDTO = productsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsDTO);
    }

    /**
     * {@code DELETE  /products/:id} : delete the "id" products.
     *
     * @param id the id of the productsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id) {
        log.debug("REST request to delete Products : {}", id);
        productsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @DeleteMapping("/products/safedelete/{id}")
    public ResponseEntity<Void> safeDeleteProducts(@PathVariable Long id) {
        log.debug("REST request to safe delete Products : {}", id);
        productsService.safeDelete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/products/amountbyos/{os}")
    public long phoneAmountByOs(@PathVariable String os){
        return productsService.getAmountPhoneByOS(os);
    }

    @GetMapping("/products/pricebyid/{id}")
    public long getPriceById(@PathVariable long id){
        return productsService.getPriceById(id);
    }
}
