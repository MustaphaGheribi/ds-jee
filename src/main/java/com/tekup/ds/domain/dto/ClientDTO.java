package com.tekup.ds.domain.dto;

import com.tekup.ds.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ClientDTO {
    private int id;
    @NotNull(message = "Le client doit avoir un nom")
    private String nom;
    @NotNull(message = "Le client doit avoir un prenom")
    private String prenom;
    @NotNull(message = "Le client doit avoir une date de naissance")
    private LocalDate dateDeNaissance;
    private String courriel;
    private String telephone;
    private Set<Ticket> tickets;
}
