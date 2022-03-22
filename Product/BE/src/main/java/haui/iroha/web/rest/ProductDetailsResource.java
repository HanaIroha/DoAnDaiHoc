package haui.iroha.web.rest;

import haui.iroha.repository.ProductDetailsRepository;
import haui.iroha.service.ProductDetailsService;
import haui.iroha.service.dto.ProductDetailsDTO;
import haui.iroha.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link haui.iroha.domain.ProductDetails}.
 */
@RestController
@RequestMapping("/api")
public class ProductDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsResource.class);

    private static final String ENTITY_NAME = "productDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDetailsService productDetailsService;

    private final ProductDetailsRepository productDetailsRepository;

    public ProductDetailsResource(ProductDetailsService productDetailsService, ProductDetailsRepository productDetailsRepository) {
        this.productDetailsService = productDetailsService;
        this.productDetailsRepository = productDetailsRepository;
    }

    /**
     * {@code POST  /product-details} : Create a new productDetails.
     *
     * @param productDetailsDTO the productDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDetailsDTO, or with status {@code 400 (Bad Request)} if the productDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-details")
    public ResponseEntity<ProductDetailsDTO> createProductDetails(@RequestBody ProductDetailsDTO productDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductDetails : {}", productDetailsDTO);
        if (productDetailsDTO.getIdProductDetail() != null) {
            throw new BadRequestAlertException("A new productDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDetailsDTO result = productDetailsService.save(productDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/product-details/" + result.getIdProductDetail()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdProductDetail().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-details/:idProductDetail} : Updates an existing productDetails.
     *
     * @param idProductDetail the id of the productDetailsDTO to save.
     * @param productDetailsDTO the productDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-details/{idProductDetail}")
    public ResponseEntity<ProductDetailsDTO> updateProductDetails(
        @PathVariable(value = "idProductDetail", required = false) final Long idProductDetail,
        @RequestBody ProductDetailsDTO productDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductDetails : {}, {}", idProductDetail, productDetailsDTO);
        if (productDetailsDTO.getIdProductDetail() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProductDetail, productDetailsDTO.getIdProductDetail())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDetailsRepository.existsById(idProductDetail)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductDetailsDTO result = productDetailsService.save(productDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDetailsDTO.getIdProductDetail().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /product-details/:idProductDetail} : Partial updates given fields of an existing productDetails, field will ignore if it is null
     *
     * @param idProductDetail the id of the productDetailsDTO to save.
     * @param productDetailsDTO the productDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-details/{idProductDetail}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductDetailsDTO> partialUpdateProductDetails(
        @PathVariable(value = "idProductDetail", required = false) final Long idProductDetail,
        @RequestBody ProductDetailsDTO productDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductDetails partially : {}, {}", idProductDetail, productDetailsDTO);
        if (productDetailsDTO.getIdProductDetail() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProductDetail, productDetailsDTO.getIdProductDetail())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDetailsRepository.existsById(idProductDetail)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDetailsDTO> result = productDetailsService.partialUpdate(productDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDetailsDTO.getIdProductDetail().toString())
        );
    }

    /**
     * {@code GET  /product-details} : get all the productDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDetails in body.
     */
    @GetMapping("/product-details")
    public ResponseEntity<List<ProductDetailsDTO>> getAllProductDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductDetails");
        Page<ProductDetailsDTO> page = productDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-details/:id} : get the "id" productDetails.
     *
     * @param id the id of the productDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-details/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable Long id) {
        log.debug("REST request to get ProductDetails : {}", id);
        Optional<ProductDetailsDTO> productDetailsDTO = productDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDetailsDTO);
    }

    /**
     * {@code DELETE  /product-details/:id} : delete the "id" productDetails.
     *
     * @param id the id of the productDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-details/{id}")
    public ResponseEntity<Void> deleteProductDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProductDetails : {}", id);
        productDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
