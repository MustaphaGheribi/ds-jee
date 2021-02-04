package com.tekup.ds.rest.controller;

import com.tekup.ds.domain.Met;
import com.tekup.ds.domain.Ticket;
import com.tekup.ds.domain.dto.MetDTO;
import com.tekup.ds.domain.dto.PeriodeDTO;
import com.tekup.ds.repository.MetRepository;
import com.tekup.ds.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Transactional
public class MetResource {

    private final Logger log = LoggerFactory.getLogger(MetResource.class);

    private final MetRepository metRepository;
    private final TicketRepository ticketRepository;
    public MetResource(MetRepository metRepository, TicketRepository ticketRepository) {
        this.metRepository = metRepository;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/mets")
    public Met createMet(@Valid @RequestBody MetDTO metDTO)  {
        log.info("REST request to save Met : {}", metDTO.toString());
        Met met = metRepository.save(new Met(metDTO.getNom(), metDTO.getPrix()));
        return metRepository.save(met);
    }

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

    @DeleteMapping("/mets/{id}")
    public void deleteMet(@PathVariable String id) {
        log.debug("REST request to delete Met : {}", id);
        metRepository.deleteById(id);
    }

    @PostMapping("/mets/bought")
    public String mostBought(@RequestBody PeriodeDTO periodeDTO) {
        List<String> list = new ArrayList<>();
//        List<Ticket> tickets = ticketRepository.findByDateBetween(periodeDTO.from.atStartOfDay(), periodeDTO.to.atStartOfDay());
        List<Ticket> tickets = ticketRepository.findAll();
        tickets.forEach(ticket -> {
            ticket.getMets().forEach(met -> list.add(met.getNom()));
        });

        Object[] objArr  = list.toArray();
        String[] str = Arrays
                .copyOf(objArr, objArr
                                .length,
                        String[].class);
        return findWord(str);
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