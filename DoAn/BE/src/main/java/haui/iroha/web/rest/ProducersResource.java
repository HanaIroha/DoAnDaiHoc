package haui.iroha.web.rest;

import haui.iroha.repository.ProducersRepository;
import haui.iroha.service.ProducersService;
import haui.iroha.service.dto.ProducersDTO;
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
 * REST controller for managing {@link haui.iroha.domain.Producers}.
 */
@RestController
@RequestMapping("/api")
public class ProducersResource {

    private final Logger log = LoggerFactory.getLogger(ProducersResource.class);

    private static final String ENTITY_NAME = "producers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProducersService producersService;

    private final ProducersRepository producersRepository;

    public ProducersResource(ProducersService producersService, ProducersRepository producersRepository) {
        this.producersService = producersService;
        this.producersRepository = producersRepository;
    }

    /**
     * {@code POST  /producers} : Create a new producers.
     *
     * @param producersDTO the producersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new producersDTO, or with status {@code 400 (Bad Request)} if the producers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producers")
    public ResponseEntity<ProducersDTO> createProducers(@RequestBody ProducersDTO producersDTO) throws URISyntaxException {
        log.debug("REST request to save Producers : {}", producersDTO);
        if (producersDTO.getIdProducer() != null) {
            throw new BadRequestAlertException("A new producers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        producersDTO.setCreatedAt(ZonedDateTime.now());
        producersDTO.setUpdatedAt(ZonedDateTime.now());
        ProducersDTO result = producersService.save(producersDTO);
        return ResponseEntity
            .created(new URI("/api/producers/" + result.getIdProducer()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdProducer().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producers/:idProducer} : Updates an existing producers.
     *
     * @param idProducer the id of the producersDTO to save.
     * @param producersDTO the producersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producersDTO,
     * or with status {@code 400 (Bad Request)} if the producersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the producersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producers/{idProducer}")
    public ResponseEntity<ProducersDTO> updateProducers(
        @PathVariable(value = "idProducer", required = false) final Long idProducer,
        @RequestBody ProducersDTO producersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Producers : {}, {}", idProducer, producersDTO);
        if (producersDTO.getIdProducer() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProducer, producersDTO.getIdProducer())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!producersRepository.existsById(idProducer)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        producersDTO.setUpdatedAt(ZonedDateTime.now());
        ProducersDTO result = producersService.save(producersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, producersDTO.getIdProducer().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producers/:idProducer} : Partial updates given fields of an existing producers, field will ignore if it is null
     *
     * @param idProducer the id of the producersDTO to save.
     * @param producersDTO the producersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producersDTO,
     * or with status {@code 400 (Bad Request)} if the producersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the producersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the producersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producers/{idProducer}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProducersDTO> partialUpdateProducers(
        @PathVariable(value = "idProducer", required = false) final Long idProducer,
        @RequestBody ProducersDTO producersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Producers partially : {}, {}", idProducer, producersDTO);
        if (producersDTO.getIdProducer() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProducer, producersDTO.getIdProducer())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!producersRepository.existsById(idProducer)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProducersDTO> result = producersService.partialUpdate(producersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, producersDTO.getIdProducer().toString())
        );
    }

    /**
     * {@code GET  /producers} : get all the producers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of producers in body.
     */
    @GetMapping("/producers")
    public ResponseEntity<List<ProducersDTO>> getAllProducers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Producers");
        Page<ProducersDTO> page = producersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producers/:id} : get the "id" producers.
     *
     * @param id the id of the producersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the producersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producers/{id}")
    public ResponseEntity<ProducersDTO> getProducers(@PathVariable Long id) {
        log.debug("REST request to get Producers : {}", id);
        Optional<ProducersDTO> producersDTO = producersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(producersDTO);
    }

    /**
     * {@code DELETE  /producers/:id} : delete the "id" producers.
     *
     * @param id the id of the producersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producers/{id}")
    public ResponseEntity<Void> deleteProducers(@PathVariable Long id) {
        log.debug("REST request to delete Producers : {}", id);
        producersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
