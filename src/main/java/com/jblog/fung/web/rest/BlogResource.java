package com.jblog.fung.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jblog.fung.domain.Blog;

import com.jblog.fung.repository.BlogRepository;
import com.jblog.fung.web.rest.util.HeaderUtil;
import com.jblog.fung.service.dto.BlogDTO;
import com.jblog.fung.service.mapper.BlogMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Blog.
 */
@RestController
@RequestMapping("/api")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private static final String ENTITY_NAME = "blog";

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;
    public BlogResource(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    /**
     * POST  /blogs : Create a new blog.
     *
     * @param blogDTO the blogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blogDTO, or with status 400 (Bad Request) if the blog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blogs")
    @Timed
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogDTO blogDTO) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", blogDTO);
        if (blogDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new blog cannot already have an ID")).body(null);
        }
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        BlogDTO result = blogMapper.toDto(blog);
        return ResponseEntity.created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blogs : Updates an existing blog.
     *
     * @param blogDTO the blogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blogDTO,
     * or with status 400 (Bad Request) if the blogDTO is not valid,
     * or with status 500 (Internal Server Error) if the blogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blogs")
    @Timed
    public ResponseEntity<BlogDTO> updateBlog(@Valid @RequestBody BlogDTO blogDTO) throws URISyntaxException {
        log.debug("REST request to update Blog : {}", blogDTO);
        if (blogDTO.getId() == null) {
            return createBlog(blogDTO);
        }
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        BlogDTO result = blogMapper.toDto(blog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blogs : get all the blogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blogs in body
     */
    @GetMapping("/blogs")
    @Timed
    public List<BlogDTO> getAllBlogs() {
        log.debug("REST request to get all Blogs");
        List<Blog> blogs = blogRepository.findAll();
        return blogMapper.toDto(blogs);
        }

    /**
     * GET  /blogs/:id : get the "id" blog.
     *
     * @param id the id of the blogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/blogs/{id}")
    @Timed
    public ResponseEntity<BlogDTO> getBlog(@PathVariable Long id) {
        log.debug("REST request to get Blog : {}", id);
        Blog blog = blogRepository.findOne(id);
        BlogDTO blogDTO = blogMapper.toDto(blog);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blogDTO));
    }

    /**
     * DELETE  /blogs/:id : delete the "id" blog.
     *
     * @param id the id of the blogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        log.debug("REST request to delete Blog : {}", id);
        blogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
