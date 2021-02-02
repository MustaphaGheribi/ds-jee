package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Table;
import com.tekup.ds.domain.dto.TableDTO;
import com.tekup.ds.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
@Transactional
public class TableResource {
    private final Logger log = LoggerFactory.getLogger(TableResource.class);
    private final TableRepository tableRepository;

    public TableResource(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @PostMapping("/tables")
    public Table createTable(@RequestBody TableDTO tableDTO)  {
        Table table = new Table(tableDTO.getNumero(), tableDTO.getNbCovert(), tableDTO.getType(), tableDTO.getSupplement());
        log.info("REST request to save Table : {}", table);
        table.setTickets(tableDTO.getTickets());
        return tableRepository.save(table);
    }

    @PutMapping("/tables")
    public Table updateTable(@RequestBody Table table) throws URISyntaxException {
        log.debug("REST request to update Table : {}", table);
        return tableRepository.save(table);

    }

    /**
     * {@code GET  /tables} : get all the tables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tables in body.
     */
    @GetMapping("/tables")
    public List<Table> getAllTables() {
        log.debug("REST request to get all Tables");
        return tableRepository.findAll();
    }

    /**
     * {@code GET  /tables/:id} : get the "id" table.
     *
     * @param id the id of the table to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the table, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tables/{id}")
    public Optional<Table> getTable(@PathVariable Integer id) {
        log.debug("REST request to get Table : {}", id);
        return tableRepository.findById(id);
    }

    /**
     * {@code DELETE  /tables/:id} : delete the "id" table.
     *
     * @param id the id of the table to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tables/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Integer id) {
        log.debug("REST request to delete Table : {}", id);
        tableRepository.deleteById(id);
        return null;
    }
}
