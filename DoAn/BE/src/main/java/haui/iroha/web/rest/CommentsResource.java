package haui.iroha.web.rest;

import haui.iroha.repository.CommentsRepository;
import haui.iroha.security.SecurityUtils;
import haui.iroha.service.CommentsService;
import haui.iroha.service.dto.CommentsDTO;
import haui.iroha.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
 * REST controller for managing {@link haui.iroha.domain.Comments}.
 */
@RestController
@RequestMapping("/api")
public class CommentsResource {

    private final Logger log = LoggerFactory.getLogger(CommentsResource.class);

    private static final String ENTITY_NAME = "comments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentsService commentsService;

    private final CommentsRepository commentsRepository;

    public CommentsResource(CommentsService commentsService, CommentsRepository commentsRepository) {
        this.commentsService = commentsService;
        this.commentsRepository = commentsRepository;
    }

    /**
     * {@code POST  /comments} : Create a new comments.
     *
     * @param commentsDTO the commentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentsDTO, or with status {@code 400 (Bad Request)} if the comments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentsDTO> createComments(@RequestBody CommentsDTO commentsDTO) throws URISyntaxException {

        log.debug("REST request to save Comments : {}", commentsDTO);
        if (commentsDTO.getIdComment() != null) {
            throw new BadRequestAlertException("A new comments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String userLogin = SecurityUtils
            .getCurrentUserLogin().get();
        if(userLogin.equals(commentsDTO.getLogin())){
            commentsDTO.setCreatedAt(ZonedDateTime.now());
            commentsDTO.setUpdatedAt(ZonedDateTime.now());
            CommentsDTO result = commentsService.save(commentsDTO);
            return ResponseEntity
                .created(new URI("/api/comments/" + result.getIdComment()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdComment().toString()))
                .body(result);
        }
        else{
            return null;
        }
    }

    /**
     * {@code PUT  /comments/:idComment} : Updates an existing comments.
     *
     * @param idComment the id of the commentsDTO to save.
     * @param commentsDTO the commentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsDTO,
     * or with status {@code 400 (Bad Request)} if the commentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments/{idComment}")
    public ResponseEntity<CommentsDTO> updateComments(
        @PathVariable(value = "idComment", required = false) final Long idComment,
        @RequestBody CommentsDTO commentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Comments : {}, {}", idComment, commentsDTO);
        if (commentsDTO.getIdComment() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idComment, commentsDTO.getIdComment())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsRepository.existsById(idComment)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentsDTO result = commentsService.save(commentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentsDTO.getIdComment().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comments/:idComment} : Partial updates given fields of an existing comments, field will ignore if it is null
     *
     * @param idComment the id of the commentsDTO to save.
     * @param commentsDTO the commentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsDTO,
     * or with status {@code 400 (Bad Request)} if the commentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comments/{idComment}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentsDTO> partialUpdateComments(
        @PathVariable(value = "idComment", required = false) final Long idComment,
        @RequestBody CommentsDTO commentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Comments partially : {}, {}", idComment, commentsDTO);
        if (commentsDTO.getIdComment() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idComment, commentsDTO.getIdComment())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsRepository.existsById(idComment)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentsDTO> result = commentsService.partialUpdate(commentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentsDTO.getIdComment().toString())
        );
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comments in body.
     */
    @GetMapping("/comments")
    public ResponseEntity<List<CommentsDTO>> getAllComments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Comments");
        Page<CommentsDTO> page = commentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/commentsByProductId/{id}")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsByProductId(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
                                                                       @PathVariable("id")long id) {
        log.debug("REST request to get a page of Comments");
        Page<CommentsDTO> page = commentsService.findAllCommentByProductId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comments.
     *
     * @param id the id of the commentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Long id) {
        log.debug("REST request to get Comments : {}", id);
        Optional<CommentsDTO> commentsDTO = commentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentsDTO);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comments.
     *
     * @param id the id of the commentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long id) {
        log.debug("REST request to delete Comments : {}", id);
        commentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
