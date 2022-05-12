package haui.iroha.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import haui.iroha.repository.BannersRepository;
import haui.iroha.service.BannersService;
import haui.iroha.service.dto.BannersDTO;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link haui.iroha.domain.Banners}.
 */
@RestController
@RequestMapping("/api")
public class BannersResource {

    @Value("${imageStoragePath}")
    private String imageStoragePath;

    private final Logger log = LoggerFactory.getLogger(BannersResource.class);

    private static final String ENTITY_NAME = "banners";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannersService bannersService;

    private final BannersRepository bannersRepository;

    public BannersResource(BannersService bannersService, BannersRepository bannersRepository) {
        this.bannersService = bannersService;
        this.bannersRepository = bannersRepository;
    }

    /**
     * {@code POST  /banners} : Create a new banners.
     *
     * @param bannersDTO the bannersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bannersDTO, or with status {@code 400 (Bad Request)} if the banners has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/banners", consumes = {"multipart/form-data"})
    public ResponseEntity<BannersDTO> createBanners(@RequestParam("banner") String banner, @RequestParam(value = "image",required = false) MultipartFile image) throws URISyntaxException {
        BannersDTO bannersDTO;
        try {
            bannersDTO = new ObjectMapper().readValue(banner, BannersDTO.class);
        } catch (Exception ex) {
            throw new BadRequestAlertException("undentity Exception", ENTITY_NAME, "error");
        }
        log.debug("REST request to save Banners : {}", bannersDTO);
        if (bannersDTO.getIdBanner() != null) {
            throw new BadRequestAlertException("A new banners cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(image != null){
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            fileName = System.currentTimeMillis() + fileName;
            bannersDTO.setImage(fileName);
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
        BannersDTO result = bannersService.save(bannersDTO);
        return ResponseEntity
            .created(new URI("/api/banners/" + result.getIdBanner()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdBanner().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banners/:idBanner} : Updates an existing banners.
     *
     * @param idBanner the id of the bannersDTO to save.
     * @param bannersDTO the bannersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bannersDTO,
     * or with status {@code 400 (Bad Request)} if the bannersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bannersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banners/{idBanner}")
    public ResponseEntity<BannersDTO> updateBanners(
        @PathVariable(value = "idBanner", required = false) final Long idBanner,
        @RequestBody BannersDTO bannersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Banners : {}, {}", idBanner, bannersDTO);
        if (bannersDTO.getIdBanner() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idBanner, bannersDTO.getIdBanner())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannersRepository.existsById(idBanner)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BannersDTO result = bannersService.save(bannersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bannersDTO.getIdBanner().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /banners/:idBanner} : Partial updates given fields of an existing banners, field will ignore if it is null
     *
     * @param idBanner the id of the bannersDTO to save.
     * @param bannersDTO the bannersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bannersDTO,
     * or with status {@code 400 (Bad Request)} if the bannersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bannersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bannersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/banners/{idBanner}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BannersDTO> partialUpdateBanners(
        @PathVariable(value = "idBanner", required = false) final Long idBanner,
        @RequestBody BannersDTO bannersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Banners partially : {}, {}", idBanner, bannersDTO);
        if (bannersDTO.getIdBanner() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idBanner, bannersDTO.getIdBanner())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannersRepository.existsById(idBanner)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BannersDTO> result = bannersService.partialUpdate(bannersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bannersDTO.getIdBanner().toString())
        );
    }

    /**
     * {@code GET  /banners} : get all the banners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banners in body.
     */
    @GetMapping("/banners")
    public ResponseEntity<List<BannersDTO>> getAllBanners(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Banners");
        Page<BannersDTO> page = bannersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banners/:id} : get the "id" banners.
     *
     * @param id the id of the bannersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bannersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banners/{id}")
    public ResponseEntity<BannersDTO> getBanners(@PathVariable Long id) {
        log.debug("REST request to get Banners : {}", id);
        Optional<BannersDTO> bannersDTO = bannersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bannersDTO);
    }

    /**
     * {@code DELETE  /banners/:id} : delete the "id" banners.
     *
     * @param id the id of the bannersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banners/{id}")
    public ResponseEntity<Void> deleteBanners(@PathVariable Long id) {
        log.debug("REST request to delete Banners : {}", id);
        bannersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
