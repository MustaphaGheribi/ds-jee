package com.tekup.ds.repository;

import com.tekup.ds.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
//     @Query("SELECT METS_NOM , COUNT(METS_NOM ) " +
//             "FROM TICKET_METS GROUP BY METS_NOM " +
//             "HAVING COUNT (METS_NOM )  = ( " +
//             "  SELECT MAX(mycount) " +
//             "  FROM ( " +
//             "        SELECT METS_NOM , COUNT(METS_NOM ) mycount " +
//             "        FROM TICKET_METS " +
//             "        GROUP BY METS_NOM" +
//             "       )" +
//             " ) ")
     List<Ticket> findByDateBetween(LocalDateTime from, LocalDateTime to);
     List<Ticket> findByDate(LocalDateTime date);
}
