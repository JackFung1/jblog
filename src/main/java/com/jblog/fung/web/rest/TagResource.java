package com.jblog.fung.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jblog.fung.domain.Tag;

import com.jblog.fung.repository.TagRepository;
import com.jblog.fung.web.rest.util.HeaderUtil;
import com.jblog.fung.web.rest.util.PaginationUtil;
import com.jblog.fung.service.dto.TagDTO;
import com.jblog.fung.service.mapper.TagMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tag.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private static final String ENTITY_NAME = "tag";

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;
    public TagResource(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    /**
     * POST  /tags : Create a new tag.
     *
     * @param tagDTO the tagDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tagDTO, or with status 400 (Bad Request) if the tag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tags")
    @Timed
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) throws URISyntaxException {
        log.debug("REST request to save Tag : {}", tagDTO);
        if (tagDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tag cannot already have an ID")).body(null);
        }
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        TagDTO result = tagMapper.toDto(tag);
        return ResponseEntity.created(new URI("/api/tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tags : Updates an existing tag.
     *
     * @param tagDTO the tagDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tagDTO,
     * or with status 400 (Bad Request) if the tagDTO is not valid,
     * or with status 500 (Internal Server Error) if the tagDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tags")
    @Timed
    public ResponseEntity<TagDTO> updateTag(@Valid @RequestBody TagDTO tagDTO) throws URISyntaxException {
        log.debug("REST request to update Tag : {}", tagDTO);
        if (tagDTO.getId() == null) {
            return createTag(tagDTO);
        }
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        TagDTO result = tagMapper.toDto(tag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tagDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tags : get all the tags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tags in body
     */
    @GetMapping("/tags")
    @Timed
    public ResponseEntity<List<TagDTO>> getAllTags(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Tags");
        Page<Tag> page = tagRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tags");
        return new ResponseEntity<>(tagMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /tags/:id : get the "id" tag.
     *
     * @param id the id of the tagDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tagDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tags/{id}")
    @Timed
    public ResponseEntity<TagDTO> getTag(@PathVariable Long id) {
        log.debug("REST request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        TagDTO tagDTO = tagMapper.toDto(tag);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tagDTO));
    }

    /**
     * DELETE  /tags/:id : delete the "id" tag.
     *
     * @param id the id of the tagDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        log.debug("REST request to delete Tag : {}", id);
        tagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
