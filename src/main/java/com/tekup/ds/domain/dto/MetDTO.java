package com.tekup.ds.domain.dto;

import com.tekup.ds.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MetDTO {
    @NotNull(message = "Le met doit avoir un nom")
    private String nom;
    @NotNull(message = "Le met doit avoir un prix")
    private double prix;
    private Set<Ticket> tickets;
}
