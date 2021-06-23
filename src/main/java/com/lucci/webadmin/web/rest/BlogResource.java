package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Blog;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.domain.ServiceItem;
import com.lucci.webadmin.service.BlogService;
import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.Blog}.
 */
@RestController
@RequestMapping("/api")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private static final String ENTITY_NAME = "blog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogService blogService;
    private final ImgUrlService imgUrlService;

    public BlogResource(BlogService blogService, ImgUrlService imgUrlService) {
        this.blogService = blogService;
        this.imgUrlService = imgUrlService;
    }

    /**
     * {@code POST  /blogs} : Create a new blog.
     *
     * @param blog the blog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blog, or with status {@code 400 (Bad Request)} if the blog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blogs")
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", blog);
        if (blog.getId() != null) {
            throw new BadRequestAlertException("A new blog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Blog result = blogService.save(blog);
        return ResponseEntity.created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blogs} : Updates an existing blog.
     *
     * @param blog the blog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blog,
     * or with status {@code 400 (Bad Request)} if the blog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blogs")
    public ResponseEntity<Blog> updateBlog(@Valid @RequestBody Blog blog) throws URISyntaxException {
        log.debug("REST request to update Blog : {}", blog);
        if (blog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<Blog> blogOpt = blogService.findOne(blog.getId());
        if (!blogOpt.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        Blog result = blogService.save(blog);
        ImgUrl oldImgUrl = blogOpt.get().getTitleImgUrl();
        if (oldImgUrl != null && !oldImgUrl.equals(blog.getTitleImgUrl())) {
            oldImgUrl.setServiceItem(null);
            imgUrlService.delete(oldImgUrl.getId());
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blogs} : get all the blogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogs in body.
     */
    @CrossOrigin
    @GetMapping("/blogs")
    public List<Blog> getAllBlogs() {
        log.debug("REST request to get all Blogs");
        return blogService.findAll();
    }

    @CrossOrigin
    @GetMapping("/blogs/service/{id}")
    public List<Blog> getAllBlogsByServiceId(@PathVariable Long id) {
        log.debug("REST request to get all Blogs by Service: {}", id);
        return blogService.findAllByServiceId(id);
    }

    /**
     * {@code GET  /blogs/:id} : get the "id" blog.
     *
     * @param id the id of the blog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blog, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable Long id) {
        log.debug("REST request to get Blog : {}", id);
        Optional<Blog> blog = blogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blog);
    }

    /**
     * {@code DELETE  /blogs/:id} : delete the "id" blog.
     *
     * @param id the id of the blog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.debug("REST request to delete Blog : {}", id);
        Optional<Blog> blogOpt = blogService.findOne(id);
        if (!blogOpt.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        blogService.delete(id);
        ImgUrl imgUrl = blogOpt.get().getTitleImgUrl();
        if (imgUrl != null) {
            imgUrlService.delete(imgUrl.getId());
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
