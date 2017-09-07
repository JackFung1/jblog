package com.jblog.fung.service.impl;

import com.jblog.fung.service.EntryService;
import com.jblog.fung.domain.Entry;
import com.jblog.fung.repository.EntryRepository;
import com.jblog.fung.service.dto.EntryDTO;
import com.jblog.fung.service.mapper.EntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Entry.
 */
@Service
@Transactional
public class EntryServiceImpl implements EntryService{

    private final Logger log = LoggerFactory.getLogger(EntryServiceImpl.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;
    public EntryServiceImpl(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }

    /**
     * Save a entry.
     *
     * @param entryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    /**
     *  Get all the entries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entries");
        return entryRepository.findAll(pageable)
            .map(entryMapper::toDto);
    }

    /**
     *  Get one entry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntryDTO findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        Entry entry = entryRepository.findOneWithEagerRelationships(id);
        return entryMapper.toDto(entry);
    }

    /**
     *  Delete the  entry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.delete(id);
    }
}
