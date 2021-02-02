package com.tekup.ds.services.impl;

import com.tekup.ds.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl {
    @Autowired
    private TicketRepository ticketRepository;


}
