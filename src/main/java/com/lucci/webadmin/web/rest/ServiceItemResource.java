package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.domain.ServiceItem;
import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.service.ServiceItemService;
import com.lucci.webadmin.service.VideoService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;

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
import java.util.Set;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.ServiceItem}.
 */
@RestController
@RequestMapping("/api")
public class ServiceItemResource {

    private final Logger log = LoggerFactory.getLogger(ServiceItemResource.class);

    private static final String ENTITY_NAME = "serviceItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceItemService serviceItemService;
    private final ImgUrlService imgUrlService;
    private final VideoService videoService;

    public ServiceItemResource(ServiceItemService serviceItemService, ImgUrlService imgUrlService, VideoService videoService) {
        this.serviceItemService = serviceItemService;
        this.imgUrlService = imgUrlService;
        this.videoService = videoService;
    }

    /**
     * {@code POST  /service-items} : Create a new serviceItem.
     *
     * @param serviceItem the serviceItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceItem, or with status {@code 400 (Bad Request)} if the serviceItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-items")
    public ResponseEntity<ServiceItem> createServiceItem(@Valid @RequestBody ServiceItem serviceItem) throws URISyntaxException {
        log.debug("REST request to save ServiceItem : {}", serviceItem);
        if (serviceItem.getId() != null) {
            throw new BadRequestAlertException("A new serviceItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceItem result = serviceItemService.save(serviceItem);
        return ResponseEntity.created(new URI("/api/service-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-items} : Updates an existing serviceItem.
     *
     * @param serviceItem the serviceItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceItem,
     * or with status {@code 400 (Bad Request)} if the serviceItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-items")
    public ResponseEntity<ServiceItem> updateServiceItem(@Valid @RequestBody ServiceItem serviceItem) throws URISyntaxException {
        log.debug("REST request to update ServiceItem : {}", serviceItem);
        if (serviceItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<ServiceItem> serviceItemOpt = serviceItemService.findOne(serviceItem.getId());
        if (!serviceItemOpt.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        serviceItemOpt.get().getCustomerImgUrls().forEach(imgUrl -> {
            imgUrl.setServiceItem(null);
            imgUrlService.save(imgUrl);
        });
        if (serviceItem.getCustomerImgUrls() != null) {
            serviceItem.getCustomerImgUrls().forEach(img ->
                imgUrlService.findOne(img.getId())
                .ifPresent(imgUrl -> {
                    img.setImgUrl(imgUrl.getImgUrl());
                    img.setServiceItem(serviceItem);
                })
            );
        }
        if (serviceItem.getRelatedVideos() != null) {
            serviceItem.getRelatedVideos().forEach(vid ->
                videoService.findOne(vid.getId())
                    .ifPresent(imgUrl -> {
                        vid.setUrl(imgUrl.getUrl());
                        vid.setServiceItem(serviceItem);
                    })
            );
        }
        ServiceItem result = serviceItemService.save(serviceItem);
        ImgUrl oldImgUrl = serviceItemOpt.get().getImgUrl();
        if (oldImgUrl != null && !oldImgUrl.equals(serviceItem.getImgUrl())) {
            oldImgUrl.setServiceItem(null);
            imgUrlService.delete(oldImgUrl.getId());
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-items} : get all the serviceItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceItems in body.
     */
    @CrossOrigin
    @GetMapping("/service-items")
    public List<ServiceItem> getAllServiceItems() {
        log.debug("REST request to get all ServiceItems");
        return serviceItemService.findAll();
    }

    /**
     * {@code GET  /service-items/:id} : get the "id" serviceItem.
     *
     * @param id the id of the serviceItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceItem, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/service-items/{id}")
    public ResponseEntity<ServiceItem> getServiceItem(@PathVariable Long id) {
        log.debug("REST request to get ServiceItem : {}", id);
        Optional<ServiceItem> serviceItem = serviceItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceItem);
    }

    /**
     * {@code DELETE  /service-items/:id} : delete the "id" serviceItem.
     *
     * @param id the id of the serviceItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-items/{id}")
    public ResponseEntity<Void> deleteServiceItem(@PathVariable Long id) {
        log.debug("REST request to delete ServiceItem : {}", id);
        Optional<ServiceItem> serviceItemOpt = serviceItemService.findOne(id);
        if (!serviceItemOpt.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        serviceItemService.delete(id);
        ImgUrl imgUrl = serviceItemOpt.get().getImgUrl();
        if (imgUrl != null) {
            imgUrlService.delete(imgUrl.getId());
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
