package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Met;
import com.tekup.ds.domain.Ticket;
import com.tekup.ds.domain.dto.MetDTO;
import com.tekup.ds.repository.MetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Transactional
public class MetResource {

    private final Logger log = LoggerFactory.getLogger(MetResource.class);


    private final MetRepository metRepository;

    public MetResource(MetRepository metRepository) {
        this.metRepository = metRepository;
    }

    /**
     * {@code POST  /mets} : Create a new met.
     *
     * @param met the met to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new met, or with status {@code 400 (Bad Request)} if the met has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mets")
    public Met createMet(@Valid @RequestBody MetDTO metDTO)  {
        log.info("REST request to save Met : {}", metDTO.toString());
        Met met = metRepository.save(new Met(metDTO.getNom(), metDTO.getPrix()));
        return metRepository.save(met);
    }

    /**
     * {@code PUT  /mets} : Updates an existing met.
     *
     * @param met the met to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated met,
     * or with status {@code 400 (Bad Request)} if the met is not valid,
     * or with status {@code 500 (Internal Server Error)} if the met couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PutMapping("/mets")
//    public ResponseEntity<Met> updateMet(@Valid @RequestBody Met met) throws URISyntaxException {
//        log.debug("REST request to update Met : {}", met);
//        if (met.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        Met result = metRepository.save(met);
//        return ResponseEntity.ok()
//                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, met.getId().toString()))
//                .body(result);
//    }

    /**
     * {@code GET  /mets} : get all the mets.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mets in body.
     */
    @GetMapping("/mets")
    public List<Met> getAllMets() {
        log.debug("REST request to get all Mets");
        return metRepository.findAll();
    }

    /**
     * {@code GET  /mets/:id} : get the "id" met.
     *
     * @param id the id of the met to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the met, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mets/{id}")
    public Optional<Met> getMet(@PathVariable String id) {
        log.debug("REST request to get Met : {}", id);
        return metRepository.findById(id);

    }

    /**
     * {@code DELETE  /mets/:id} : delete the "id" met.
     *
     * @param id the id of the met to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mets/{id}")
    public void deleteMet(@PathVariable String id) {
        log.debug("REST request to delete Met : {}", id);
        metRepository.deleteById(id);
        }

}