package haui.iroha.web.rest;

import haui.iroha.repository.ImagesRepository;
import haui.iroha.service.ImagesService;
import haui.iroha.service.dto.ImagesDTO;
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
 * REST controller for managing {@link haui.iroha.domain.Images}.
 */
@RestController
@RequestMapping("/api")
public class ImagesResource {

    @Value("${imageStoragePath}")
    private String imageStoragePath;

    private final Logger log = LoggerFactory.getLogger(ImagesResource.class);

    private static final String ENTITY_NAME = "images";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImagesService imagesService;

    private final ImagesRepository imagesRepository;

    public ImagesResource(ImagesService imagesService, ImagesRepository imagesRepository) {
        this.imagesService = imagesService;
        this.imagesRepository = imagesRepository;
    }

    /**
     * {@code POST  /images} : Create a new images.
     *
     * @param imagesDTO the imagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imagesDTO, or with status {@code 400 (Bad Request)} if the images has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/images", consumes = {"multipart/form-data"})
    public ResponseEntity<ImagesDTO> createImages(@RequestParam("id") long id, @RequestParam(value = "image",required = false) MultipartFile image) throws URISyntaxException {
        log.debug("REST request to save Images : {}");
        ImagesDTO imgDTO = new ImagesDTO();
        imgDTO.setIdProduct(id);
        if(image != null){
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            fileName = System.currentTimeMillis() + fileName;
            imgDTO.setImageUrl(fileName);
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
        ImagesDTO result = imagesService.save(imgDTO);
        return ResponseEntity
            .created(new URI("/api/images/" + result.getIdImage()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdImage().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /images/:idImage} : Updates an existing images.
     *
     * @param idImage the id of the imagesDTO to save.
     * @param imagesDTO the imagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagesDTO,
     * or with status {@code 400 (Bad Request)} if the imagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/images/{idImage}")
    public ResponseEntity<ImagesDTO> updateImages(
        @PathVariable(value = "idImage", required = false) final Long idImage,
        @RequestBody ImagesDTO imagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Images : {}, {}", idImage, imagesDTO);
        if (imagesDTO.getIdImage() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idImage, imagesDTO.getIdImage())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagesRepository.existsById(idImage)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImagesDTO result = imagesService.save(imagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagesDTO.getIdImage().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /images/:idImage} : Partial updates given fields of an existing images, field will ignore if it is null
     *
     * @param idImage the id of the imagesDTO to save.
     * @param imagesDTO the imagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imagesDTO,
     * or with status {@code 400 (Bad Request)} if the imagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the imagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the imagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/images/{idImage}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImagesDTO> partialUpdateImages(
        @PathVariable(value = "idImage", required = false) final Long idImage,
        @RequestBody ImagesDTO imagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Images partially : {}, {}", idImage, imagesDTO);
        if (imagesDTO.getIdImage() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idImage, imagesDTO.getIdImage())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imagesRepository.existsById(idImage)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImagesDTO> result = imagesService.partialUpdate(imagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imagesDTO.getIdImage().toString())
        );
    }

    /**
     * {@code GET  /images} : get all the images.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of images in body.
     */
    @GetMapping("/images")
    public ResponseEntity<List<ImagesDTO>> getAllImages(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Images");
        Page<ImagesDTO> page = imagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/imagesofproduct/{id}")
    public ResponseEntity<List<ImagesDTO>> getAllImagesOfProduct(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
                                                                 @PathVariable("id")long id) {
        log.debug("REST request to get a page of Images");
        Page<ImagesDTO> page = imagesService.findAllByProductId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /images/:id} : get the "id" images.
     *
     * @param id the id of the imagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<ImagesDTO> getImages(@PathVariable Long id) {
        log.debug("REST request to get Images : {}", id);
        Optional<ImagesDTO> imagesDTO = imagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagesDTO);
    }

    /**
     * {@code DELETE  /images/:id} : delete the "id" images.
     *
     * @param id the id of the imagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImages(@PathVariable Long id) {
        log.debug("REST request to delete Images : {}", id);
        imagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
