package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.service.dto.ImgUrlDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.ImgUrl}.
 */
@RestController
@RequestMapping("/api")
public class ImgUrlResource {

    private final Logger log = LoggerFactory.getLogger(ImgUrlResource.class);

    private static final String ENTITY_NAME = "imgUrl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImgUrlService imgUrlService;

    public ImgUrlResource(ImgUrlService imgUrlService) {
        this.imgUrlService = imgUrlService;
    }

    /**
     * {@code POST  /img-urls} : Create a new imgUrl.
     *
     * @param imgUrlDTO the imgUrlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imgUrlDTO, or with status {@code 400 (Bad Request)} if the imgUrl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/img-urls")
    public ResponseEntity<ImgUrlDTO> createImgUrl(@Valid @RequestBody ImgUrlDTO imgUrlDTO) throws URISyntaxException {
        log.debug("REST request to save ImgUrl : {}", imgUrlDTO);
        if (imgUrlDTO.getId() != null) {
            throw new BadRequestAlertException("A new imgUrl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImgUrlDTO result = imgUrlService.save(imgUrlDTO);
        return ResponseEntity.created(new URI("/api/img-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/img-urls/upload")
    public ResponseEntity<ImgUrlDTO> uploadImage(@RequestParam("image") MultipartFile multipartFile) throws URISyntaxException {
        ImgUrlDTO result = imgUrlService.upload(multipartFile);
        return createImgUrl(result);
    }
    /**
     * {@code PUT  /img-urls} : Updates an existing imgUrl.
     *
     * @param imgUrlDTO the imgUrlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imgUrlDTO,
     * or with status {@code 400 (Bad Request)} if the imgUrlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imgUrlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/img-urls")
    public ResponseEntity<ImgUrlDTO> updateImgUrl(@Valid @RequestBody ImgUrlDTO imgUrlDTO) throws URISyntaxException {
        log.debug("REST request to update ImgUrl : {}", imgUrlDTO);
        if (imgUrlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImgUrlDTO result = imgUrlService.save(imgUrlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imgUrlDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /img-urls} : get all the imgUrls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imgUrls in body.
     */
    @CrossOrigin
    @GetMapping("/img-urls")
    public List<ImgUrlDTO> getAllImgUrls(Long treatmentId) {
        log.debug("REST request to get all ImgUrls");
        List<ImgUrlDTO> imgUrlDTOList;
        if (treatmentId != null) {
            imgUrlDTOList = imgUrlService.findByTreatmentId(treatmentId);
        } else {
            imgUrlDTOList = imgUrlService.findAll();
        }
        return imgUrlDTOList;
    }

    /**
     * {@code GET  /img-urls/:id} : get the "id" imgUrl.
     *
     * @param id the id of the imgUrlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imgUrlDTO, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/img-urls/{id}")
    public ResponseEntity<ImgUrlDTO> getImgUrl(@PathVariable Long id) {
        log.debug("REST request to get ImgUrl : {}", id);
        Optional<ImgUrlDTO> imgUrlDTO = imgUrlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imgUrlDTO);
    }

    /**
     * {@code DELETE  /img-urls/:id} : delete the "id" imgUrl.
     *
     * @param id the id of the imgUrlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/img-urls/{id}")
    public ResponseEntity<Void> deleteImgUrl(@PathVariable Long id) {
        log.debug("REST request to delete ImgUrl : {}", id);
        imgUrlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
