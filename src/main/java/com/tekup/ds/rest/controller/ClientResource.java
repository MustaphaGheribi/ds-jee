package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Client;
import com.tekup.ds.domain.Ticket;
import com.tekup.ds.domain.dto.ClientDTO;
import com.tekup.ds.repository.ClientRepository;
import com.tekup.ds.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.tekup.ds.domain.Client}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final ClientRepository clientRepository;
    private final TicketRepository ticketRepository;

    public ClientResource(ClientRepository clientRepository, TicketRepository ticketRepository) {
        this.clientRepository = clientRepository;
        this.ticketRepository = ticketRepository;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param client the client to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new client, or with status {@code 400 (Bad Request)} if the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients")
    public Client createClient(@Valid @RequestBody ClientDTO clientDTO) {
        log.debug("REST request to save Client : {}", clientDTO.toString());
        Client client = new Client(clientDTO.getNom(), clientDTO.getDateDeNaissance(), clientDTO.getCourriel(), clientDTO.getTelephone(), clientDTO.getPrenom());
        if(!clientDTO.getTickets().isEmpty()) {
                Set<Ticket> savedTickets =
                    clientDTO.getTickets().stream()
                                .map(ticket -> ticketRepository.findById(ticket.getNumero()).get())
                    .collect(Collectors.toSet());
            client.setTickets(savedTickets);
        }
        return clientRepository.save(client);
    }
//
//    /**
//     * {@code PUT  /clients} : Updates an existing client.
//     *
//     * @param client the client to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated client,
//     * or with status {@code 400 (Bad Request)} if the client is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the client couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/clients")
//    public ResponseEntity<Client> updateClient(@RequestBody Client client) throws URISyntaxException {
//        log.debug("REST request to update Client : {}", client);
//
//        Client result = clientRepository.save(client);
//        return null;
//    }

    /**
     * {@code GET  /clients} : get all the clients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clients in body.
     */
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        log.debug("REST request to get all Clients");
        return clientRepository.findAll();
    }

    /**
     * {@code GET  /clients/:id} : get the "id" client.
     *
     * @param id the id of the client to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the client, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clients/{id}")
    public Optional<Client> getClient(@PathVariable Integer id) {
        log.debug("REST request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    /**
     * {@code DELETE  /clients/:id} : delete the "id" client.
     *
     * @param id the id of the client to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Integer id) {
        log.debug("REST request to delete Client : {}", id);
        clientRepository.deleteById(id);

    }
}

