package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Met;
import com.tekup.ds.domain.Ticket;
import com.tekup.ds.domain.dto.PeriodeDTO;
import com.tekup.ds.domain.dto.TicketDTO;
import com.tekup.ds.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Transactional
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    private final TicketRepository ticketRepository;

    public TicketResource(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/tickets")
    public Ticket createTicket(@Valid @RequestBody TicketDTO ticketDTO)  {
        Ticket ticket = new Ticket(LocalDateTime.now(), ticketDTO.getNbCouvert(), 0);
        log.debug("REST request to save Ticket : {}", ticketDTO);
        Double addition = ticketDTO.getMets()
                .stream()
                .map(Met::getPrix)
                .reduce(0.0, Double::sum);
        ticket.setAddition(addition);
        ticket.setMets(ticketDTO.getMets());
        return ticketRepository.save(ticket);
    }


//    @PutMapping("/tickets")
//    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody Ticket ticket) throws URISyntaxException {
//        log.debug("REST request to update Ticket : {}", ticket);
//        if (ticket.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        Ticket result = ticketRepository.save(ticket);
//        return ResponseEntity.ok()
//                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticket.getId().toString()))
//                .body(result);
//    }


    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        log.debug("REST request to get all Tickets");
        return ticketRepository.findAll();
    }


    @GetMapping("/tickets/{id}")
    public Optional<Ticket> getTicket(@PathVariable Integer id) {
        log.debug("REST request to get Ticket : {}", id);
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket;
    }


    @DeleteMapping("/tickets/{id}")
    public void deleteTicket(@PathVariable Integer id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketRepository.deleteById(id);
    }
    @PostMapping("/tickets/income")
    public Double getIncome(@RequestBody PeriodeDTO periodeDTO) {
        List<Ticket> tickets = ticketRepository.findAll();
//        List<Ticket> tickets = ticketRepository.findByDate(periodeDTO.from.atStartOfDay());
        Double addition = tickets
                .stream()
                .map(Ticket::getAddition)
                .reduce(0.0, Double::sum);
        return addition;
    }
}

