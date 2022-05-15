package haui.iroha.web.rest;

import haui.iroha.domain.User;
import haui.iroha.repository.OrderDetailsRepository;
import haui.iroha.repository.OrdersRepository;
import haui.iroha.security.AuthoritiesConstants;
import haui.iroha.security.SecurityUtils;
import haui.iroha.service.MailService;
import haui.iroha.service.OrderDetailsService;
import haui.iroha.service.OrdersService;
import haui.iroha.service.UserService;
import haui.iroha.service.dto.AdminUserDTO;
import haui.iroha.service.dto.CommentsDTO;
import haui.iroha.service.dto.OrderDetailsDTO;
import haui.iroha.service.dto.OrdersDTO;
import haui.iroha.service.impl.OrderDetailsServiceImpl;
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

import javax.lang.model.type.UnknownTypeException;

/**
 * REST controller for managing {@link haui.iroha.domain.Orders}.
 */
@RestController
@RequestMapping("/api")
public class OrdersResource {

    private final Logger log = LoggerFactory.getLogger(OrdersResource.class);

    private static final String ENTITY_NAME = "orders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdersService ordersService;

    private final OrdersRepository ordersRepository;

    private final OrderDetailsService orderDetailsService;

    private final OrderDetailsRepository orderDetailsRepository;

    private final UserService userService;

    private final MailService mailService;

    public OrdersResource(OrdersService ordersService, OrdersRepository ordersRepository, UserService userService, MailService mailService, OrderDetailsService orderDetailsService, OrderDetailsRepository orderDetailsRepository) {
        this.ordersService = ordersService;
        this.ordersRepository = ordersRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.orderDetailsService = orderDetailsService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    /**
     * {@code POST  /orders} : Create a new orders.
     *
     * @param ordersDTO the ordersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordersDTO, or with status {@code 400 (Bad Request)} if the orders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orders")
    public ResponseEntity<OrdersDTO> createOrders(@RequestBody OrdersDTO ordersDTO) throws URISyntaxException {
        String userLogin = SecurityUtils
            .getCurrentUserLogin().get();
        if(userLogin.equals(ordersDTO.getLogin())){
            log.debug("REST request to save Orders : {}", ordersDTO);
            if (ordersDTO.getIdOrder() != null) {
                throw new BadRequestAlertException("A new orders cannot already have an ID", ENTITY_NAME, "idexists");
            }
            ordersDTO.setCreatedAt(ZonedDateTime.now());
            ordersDTO.setUpdatedAt(ZonedDateTime.now());
            ordersDTO.setOrderCode(ordersDTO.getLogin()+System.currentTimeMillis());
            long status=0;
            ordersDTO.setOrderStatus(status);
            OrdersDTO result = ordersService.save(ordersDTO);
            User as = userService
                .getUserWithAuthorities().get();
            mailService.sendEmail(as.getEmail(),
                "Cam on ban "+as.getFullName()+" da dat hang thanh cong",
                "Ban da dat thanh cong don hang " + result.getOrderCode(), false, false);
            return ResponseEntity
                .created(new URI("/api/orders/" + result.getIdOrder()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdOrder().toString()))
                .body(result);
        }
        else{
            return null;
        }

    }

    /**
     * {@code PUT  /orders/:idOrder} : Updates an existing orders.
     *
     * @param idOrder the id of the ordersDTO to save.
     * @param ordersDTO the ordersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordersDTO,
     * or with status {@code 400 (Bad Request)} if the ordersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orders/{idOrder}")
    public ResponseEntity<OrdersDTO> updateOrders(
        @PathVariable(value = "idOrder", required = false) final Long idOrder,
        @RequestBody OrdersDTO ordersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Orders : {}, {}", idOrder, ordersDTO);
        if (ordersDTO.getIdOrder() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idOrder, ordersDTO.getIdOrder())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordersRepository.existsById(idOrder)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrdersDTO result = ordersService.save(ordersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordersDTO.getIdOrder().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /orders/:idOrder} : Partial updates given fields of an existing orders, field will ignore if it is null
     *
     * @param idOrder the id of the ordersDTO to save.
     * @param ordersDTO the ordersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordersDTO,
     * or with status {@code 400 (Bad Request)} if the ordersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orders/{idOrder}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrdersDTO> partialUpdateOrders(
        @PathVariable(value = "idOrder", required = false) final Long idOrder,
        @RequestBody OrdersDTO ordersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Orders partially : {}, {}", idOrder, ordersDTO);
        if (ordersDTO.getIdOrder() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idOrder, ordersDTO.getIdOrder())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordersRepository.existsById(idOrder)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdersDTO> result = ordersService.partialUpdate(ordersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordersDTO.getIdOrder().toString())
        );
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrdersDTO>> getAllOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        Page<OrdersDTO> page = ordersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ordersbystatus/{status}")
    public ResponseEntity<List<OrdersDTO>> getAllOrdersByStatus(@PathVariable("status")String status,
                                                                @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        if(status.equals("4"))
            status="%";
        Page<OrdersDTO> page = ordersService.findAllByOrderStatus(status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ordersbylogin")
    public ResponseEntity<List<OrdersDTO>> getAllOrdersByLogin(@RequestParam(name = "login", required = false)String login,
                                                                   @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Orders By Login and Status");
        String userLogin = "";
        if(login == null){
            userLogin = SecurityUtils
                .getCurrentUserLogin().get();
        }
        else{
            userLogin=login;
        }
        Page<OrdersDTO> page = ordersService.findAllByLogin(userLogin, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /orders/:id} : get the "id" orders.
     *
     * @param id the id of the ordersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrdersDTO> getOrders(@PathVariable Long id) {
        log.debug("REST request to get Orders : {}", id);
        Optional<OrdersDTO> ordersDTO = ordersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordersDTO);
    }

    @GetMapping("/orders/userordercount")
    public long getUserOrderCountByLogin(@RequestParam(name = "login") String login) {
        log.debug("REST request to get Orders Count by LOGIN : {}", login);
        return ordersRepository.getUserOrderCount(login);
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" orders.
     *
     * @param id the id of the ordersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
        log.debug("REST request to delete Orders : {}", id);
        ordersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/setordersstatus/{idOrder}/{status}")
    public ResponseEntity<String> updateOrdersStatus(@PathVariable(value = "idOrder")Long idOrder,@PathVariable(value="status")long status){
        OrdersDTO dt = ordersService.findOne(idOrder).get();
        String userLogin = SecurityUtils
            .getCurrentUserLogin().get();
        if(status == 1){
            if(dt.getOrderStatus()==0 && SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
                dt.setUpdatedAt(ZonedDateTime.now());
                dt.setOrderStatus(status);
                OrdersDTO result = ordersService.save(dt);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
        }
        else if (status == 2){
            if(dt.getOrderStatus()==0 && (userLogin.equals(dt.getLogin()) || SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN))) {
                dt.setUpdatedAt(ZonedDateTime.now());
                dt.setOrderStatus(status);
                OrdersDTO result = ordersService.save(dt);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity<>("FAIL", HttpStatus.OK);
        }
        return new ResponseEntity<>("FAIL", HttpStatus.OK);
    }
}
