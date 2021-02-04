package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Table;
import com.tekup.ds.domain.Ticket;
import com.tekup.ds.domain.dto.TableDTO;
import com.tekup.ds.repository.TableRepository;
import com.tekup.ds.repository.TicketRepository;
import com.tekup.ds.rest.controller.exception.NotFoundException;
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
    private final TicketRepository ticketRepository;
    public TableResource(TableRepository tableRepository, TicketRepository ticketRepository) {
        this.tableRepository = tableRepository;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/tables/{ticketId}")
    public Table createTable(@RequestBody TableDTO tableDTO, @PathVariable int ticketId)  {
        Table table = new Table(tableDTO.getNumero(), tableDTO.getNbCovert(), tableDTO.getType(), tableDTO.getSupplement());
        log.info("REST request to save Table : {}", table);
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if(ticket.isPresent()){
            table.getTickets().add(ticket.get());
            Table tableSaved = tableRepository.save(table);
            ticket.get().setTable(tableSaved);
            ticketRepository.save(ticket.get());
            return table;
        } else throw new NotFoundException("ticket est introuvable");
    }

    @PutMapping("/tables")
    public Table updateTable(@RequestBody Table table) throws URISyntaxException {
        log.debug("REST request to update Table : {}", table);
        return tableRepository.save(table);

    }


    @GetMapping("/tables")
    public List<Table> getAllTables() {
        log.debug("REST request to get all Tables");
        return tableRepository.findAll();
    }

    @GetMapping("/tables/{id}")
    public Optional<Table> getTable(@PathVariable Integer id) {
        log.debug("REST request to get Table : {}", id);
        return tableRepository.findById(id);
    }

    @DeleteMapping("/tables/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Integer id) {
        log.debug("REST request to delete Table : {}", id);
        tableRepository.deleteById(id);
        return null;
    }

    @GetMapping("/tables/reserved")
    public int mostReservedTable() {
        List<Table> tables = tableRepository.findAll();
        int max=0;
        int nbTable = 0;
        for (Table t: tables) {
            if(t.getTickets().size()> max) {
                max = t.getTickets().size();
                nbTable = t.getNumero();
            }
        }
        return nbTable;
    }
}
