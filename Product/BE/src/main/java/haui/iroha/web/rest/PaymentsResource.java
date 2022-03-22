package haui.iroha.web.rest;

import haui.iroha.repository.PaymentsRepository;
import haui.iroha.service.PaymentsService;
import haui.iroha.service.dto.PaymentsDTO;
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
 * REST controller for managing {@link haui.iroha.domain.Payments}.
 */
@RestController
@RequestMapping("/api")
public class PaymentsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsResource.class);

    private static final String ENTITY_NAME = "payments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsService paymentsService;

    private final PaymentsRepository paymentsRepository;

    public PaymentsResource(PaymentsService paymentsService, PaymentsRepository paymentsRepository) {
        this.paymentsService = paymentsService;
        this.paymentsRepository = paymentsRepository;
    }

    /**
     * {@code POST  /payments} : Create a new payments.
     *
     * @param paymentsDTO the paymentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentsDTO, or with status {@code 400 (Bad Request)} if the payments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments")
    public ResponseEntity<PaymentsDTO> createPayments(@RequestBody PaymentsDTO paymentsDTO) throws URISyntaxException {
        log.debug("REST request to save Payments : {}", paymentsDTO);
        if (paymentsDTO.getIdPayment() != null) {
            throw new BadRequestAlertException("A new payments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentsDTO.setCreatedAt(ZonedDateTime.now());
        paymentsDTO.setUpdatedAt(ZonedDateTime.now());
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity
            .created(new URI("/api/payments/" + result.getIdPayment()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdPayment().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments/:idPayment} : Updates an existing payments.
     *
     * @param idPayment the id of the paymentsDTO to save.
     * @param paymentsDTO the paymentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments/{idPayment}")
    public ResponseEntity<PaymentsDTO> updatePayments(
        @PathVariable(value = "idPayment", required = false) final Long idPayment,
        @RequestBody PaymentsDTO paymentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Payments : {}, {}", idPayment, paymentsDTO);
        if (paymentsDTO.getIdPayment() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idPayment, paymentsDTO.getIdPayment())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentsRepository.existsById(idPayment)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentsDTO.setUpdatedAt(ZonedDateTime.now());
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsDTO.getIdPayment().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payments/:idPayment} : Partial updates given fields of an existing payments, field will ignore if it is null
     *
     * @param idPayment the id of the paymentsDTO to save.
     * @param paymentsDTO the paymentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payments/{idPayment}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentsDTO> partialUpdatePayments(
        @PathVariable(value = "idPayment", required = false) final Long idPayment,
        @RequestBody PaymentsDTO paymentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payments partially : {}, {}", idPayment, paymentsDTO);
        if (paymentsDTO.getIdPayment() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idPayment, paymentsDTO.getIdPayment())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentsRepository.existsById(idPayment)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentsDTO> result = paymentsService.partialUpdate(paymentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentsDTO.getIdPayment().toString())
        );
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentsDTO>> getAllPayments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Payments");
        Page<PaymentsDTO> page = paymentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payments.
     *
     * @param id the id of the paymentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentsDTO> getPayments(@PathVariable Long id) {
        log.debug("REST request to get Payments : {}", id);
        Optional<PaymentsDTO> paymentsDTO = paymentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentsDTO);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payments.
     *
     * @param id the id of the paymentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayments(@PathVariable Long id) {
        log.debug("REST request to delete Payments : {}", id);
        paymentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
