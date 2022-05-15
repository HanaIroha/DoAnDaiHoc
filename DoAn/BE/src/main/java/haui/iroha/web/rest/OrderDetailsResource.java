package haui.iroha.web.rest;

import haui.iroha.repository.OrderDetailsRepository;
import haui.iroha.repository.ProductsRepository;
import haui.iroha.service.OrderDetailsService;
import haui.iroha.service.ProductsService;
import haui.iroha.service.dto.OrderDetailsDTO;
import haui.iroha.service.dto.ProductsDTO;
import haui.iroha.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
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
 * REST controller for managing {@link haui.iroha.domain.OrderDetails}.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsResource.class);

    private static final String ENTITY_NAME = "orderDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDetailsService orderDetailsService;

    private final OrderDetailsRepository orderDetailsRepository;

    private final ProductsService productsService;

    private final ProductsRepository productsRepository;

    public OrderDetailsResource(OrderDetailsService orderDetailsService, OrderDetailsRepository orderDetailsRepository, ProductsService productsService, ProductsRepository productsRepository) {
        this.orderDetailsService = orderDetailsService;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productsService = productsService;
        this.productsRepository = productsRepository;
    }

    /**
     * {@code POST  /order-details} : Create a new orderDetails.
     *
     * @param orderDetailsDTO the orderDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetailsDTO, or with status {@code 400 (Bad Request)} if the orderDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-details")
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetails : {}", orderDetailsDTO);
        if (orderDetailsDTO.getIdOrderDetail() != null) {
            throw new BadRequestAlertException("A new orderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderDetailsDTO.setCreatedAt(ZonedDateTime.now());
        orderDetailsDTO.setUpdatedAt(ZonedDateTime.now());
        OrderDetailsDTO result = orderDetailsService.save(orderDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/order-details/" + result.getIdOrderDetail()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdOrderDetail().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-details/:idOrderDetail} : Updates an existing orderDetails.
     *
     * @param idOrderDetail the id of the orderDetailsDTO to save.
     * @param orderDetailsDTO the orderDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-details/{idOrderDetail}")
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(
        @PathVariable(value = "idOrderDetail", required = false) final Long idOrderDetail,
        @RequestBody OrderDetailsDTO orderDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderDetails : {}, {}", idOrderDetail, orderDetailsDTO);
        if (orderDetailsDTO.getIdOrderDetail() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idOrderDetail, orderDetailsDTO.getIdOrderDetail())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailsRepository.existsById(idOrderDetail)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderDetailsDTO result = orderDetailsService.save(orderDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDetailsDTO.getIdOrderDetail().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-details/:idOrderDetail} : Partial updates given fields of an existing orderDetails, field will ignore if it is null
     *
     * @param idOrderDetail the id of the orderDetailsDTO to save.
     * @param orderDetailsDTO the orderDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-details/{idOrderDetail}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDetailsDTO> partialUpdateOrderDetails(
        @PathVariable(value = "idOrderDetail", required = false) final Long idOrderDetail,
        @RequestBody OrderDetailsDTO orderDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderDetails partially : {}, {}", idOrderDetail, orderDetailsDTO);
        if (orderDetailsDTO.getIdOrderDetail() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idOrderDetail, orderDetailsDTO.getIdOrderDetail())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailsRepository.existsById(idOrderDetail)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderDetailsDTO> result = orderDetailsService.partialUpdate(orderDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDetailsDTO.getIdOrderDetail().toString())
        );
    }

    /**
     * {@code GET  /order-details} : get all the orderDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetails in body.
     */
    @GetMapping("/order-details")
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrderDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderDetails");
        Page<OrderDetailsDTO> page = orderDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/order-detailsbyorderid/{id}")
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrderDetailsByOrderId(@PathVariable("id")long id,
                                                                             @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderDetails BY ID");
        Page<OrderDetailsDTO> page = orderDetailsService.findAllByOrderId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ordervalue/{id}")
    public long getOrderValue(@PathVariable("id")long id) {
        log.debug("REST request to get ORDER VALUE BY ID");
        return orderDetailsService.orderValue(id);
    }

    @GetMapping("/orderdetailscheck/{id}")
    public ResponseEntity<String> checkOrderDetails(@PathVariable("id")long id) {
        log.debug("REST request to check ORDER BY ID");
        List<OrderDetailsDTO> od = orderDetailsService.findAllByOrderId(id, Pageable.unpaged()).toList();
        long totalnumber = od.size();
        long oknumber = 0;
        for(OrderDetailsDTO check : od){
            ProductsDTO a = productsService.findOne(check.getIdProduct()).get();
            if(check.getQuantity()<=a.getQuantity())
                oknumber++;
        }
        if(totalnumber == oknumber)
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else
            return new ResponseEntity<>("FAIL", HttpStatus.OK);
    }

    @GetMapping("/orderdetailsok/{id}")
    public ResponseEntity<String> orderComplete(@PathVariable("id")long id) {
        log.debug("REST request to complete ORDER BY ID");
        List<OrderDetailsDTO> od = orderDetailsService.findAllByOrderId(id, Pageable.unpaged()).toList();
        long totalnumber = od.size();
        long oknumber = 0;
        for(OrderDetailsDTO check : od){
            ProductsDTO a = productsService.findOne(check.getIdProduct()).get();
            a.setQuantity(a.getQuantity()-check.getQuantity());
            a.setUpdatedAt(ZonedDateTime.now());
            ProductsDTO result = productsService.save(a);
            oknumber++;
        }
        if(totalnumber == oknumber)
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else
            return new ResponseEntity<>("FAIL", HttpStatus.OK);
    }

    /**
     * {@code GET  /order-details/:id} : get the "id" orderDetails.
     *
     * @param id the id of the orderDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderDetails : {}", id);
        Optional<OrderDetailsDTO> orderDetailsDTO = orderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDetailsDTO);
    }

    /**
     * {@code DELETE  /order-details/:id} : delete the "id" orderDetails.
     *
     * @param id the id of the orderDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-details/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetails : {}", id);
        orderDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
