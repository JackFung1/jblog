package com.jblog.fung.service;

import com.jblog.fung.service.dto.EntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Entry.
 */
public interface EntryService {

    /**
     * Save a entry.
     *
     * @param entryDTO the entity to save
     * @return the persisted entity
     */
    EntryDTO save(EntryDTO entryDTO);

    /**
     *  Get all the entries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EntryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" entry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EntryDTO findOne(Long id);

    /**
     *  Delete the "id" entry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
