package com.tekup.ds.domain.dto;

import com.tekup.ds.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class TableDTO {
    @NotNull(message = "Le table doit avoir un numero")
    private int numero;
    @NotNull(message = "Le table doit avoir un nombre couvert")
    private int nbCovert;
    @NotNull(message = "Le table doit avoir un type")
    private String type;
    private double supplement;
    private Set<Ticket> tickets;

}
