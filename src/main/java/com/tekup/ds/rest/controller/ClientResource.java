package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Client;
import com.tekup.ds.domain.Table;
import com.tekup.ds.domain.Ticket;
import com.tekup.ds.domain.dto.ClientDTO;
import com.tekup.ds.repository.ClientRepository;
import com.tekup.ds.repository.TableRepository;
import com.tekup.ds.repository.TicketRepository;
import com.tekup.ds.rest.controller.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.format.TextStyle;
import java.util.*;
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
    private final TableRepository tableRepository;

    public ClientResource(ClientRepository clientRepository, TicketRepository ticketRepository, TableRepository tableRepository) {
        this.clientRepository = clientRepository;
        this.ticketRepository = ticketRepository;
        this.tableRepository = tableRepository;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param client the client to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new client, or with status {@code 400 (Bad Request)} if the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients/{tableId}")
    public Client createClient(@Valid @RequestBody ClientDTO clientDTO, @PathVariable int tableId) {
        log.debug("REST request to save Client : {}", clientDTO.toString());
        Client client = new Client(clientDTO.getNom(), clientDTO.getDateDeNaissance(), clientDTO.getCourriel(), clientDTO.getTelephone(), clientDTO.getPrenom());
        Optional<Table> table = tableRepository.findById(tableId);
        if(table.isPresent()){
            Client clientSaved = clientRepository.save(client);
            table.get().getTickets().stream().forEach(ticket -> ticket.setClient(clientSaved));
            List<Ticket> allById = ticketRepository.findAllById
                    (table.get().getTickets().stream().map(Ticket::getNumero).collect(Collectors.toList()));
            allById.stream().forEach(ticket -> ticket.setClient(clientSaved));
            tableRepository.save(table.get());
            ticketRepository.saveAll(allById);
            log.info("TICKET POPULATED WITCH CLIENT{}" ,ticketRepository.findAll().get(0).getClient().getNom());
            log.info("TICKET POPULATED WITCH TABLE{}" ,ticketRepository.findAll().get(0).getTable().getType());
            return clientSaved;
        }
        else throw new NotFoundException("Table est introuvable");
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        log.debug("REST request to get all Clients");
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public Optional<Client> getClient(@PathVariable Integer id) {
        log.debug("REST request to get Client : {}", id);
        return clientRepository.findById(id);
    }


    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Integer id) {
        log.debug("REST request to delete Client : {}", id);
        clientRepository.deleteById(id);

    }

    @GetMapping("/loyal")
    public String loyalClient() {
        List<String> list = new ArrayList<>();
//        List<Ticket> tickets = ticketRepository.findByDateBetween(periodeDTO.from.atStartOfDay(), periodeDTO.to.atStartOfDay());
        List<Ticket> tickets = ticketRepository.findAll();
        tickets.forEach(ticket -> list.add(ticket.getClient().getNom()));
        Object[] objArr  = list.toArray();
        String[] str = Arrays
                .copyOf(objArr, objArr
                                .length,
                        String[].class);
        return findWord(str);
    }
    @GetMapping("/clients/reservedday/{clientId}")
    public String mostReservedDay(@PathVariable int clientId) {
        Locale locale = new Locale("en", "US");
        List<String> list = new ArrayList<>();
        Optional<Client> client = clientRepository.findById(clientId);
        if(client.isPresent()) {
            client.get().getTickets().forEach(ticket -> list.add(ticket.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL,locale)));
            Object[] objArr  = list.toArray();
            String[] str = Arrays
                    .copyOf(objArr, objArr
                                    .length,
                            String[].class);
            return findWord(str);
        }


        throw new NotFoundException("Client n'existe pas");
    }

    static String findWord(String[] arr)
    {

        // Create HashMap to store word and it's frequency
        HashMap<String, Integer> hs = new HashMap<String, Integer>();

        // Iterate through array of words
        for (int i = 0; i < arr.length; i++) {
            // If word already exist in HashMap then increase it's count by 1
            if (hs.containsKey(arr[i])) {
                hs.put(arr[i], hs.get(arr[i]) + 1);
            }
            // Otherwise add word to HashMap
            else {
                hs.put(arr[i], 1);
            }
        }

        // Create set to iterate over HashMap
        Set<Map.Entry<String, Integer> > set = hs.entrySet();
        String key = "";
        int value = 0;

        for (Map.Entry<String, Integer> me : set) {
            // Check for word having highest frequency
            if (me.getValue() > value) {
                value = me.getValue();
                key = me.getKey();
            }
        }

        // Return word having highest frequency
        return key;
    }
}

