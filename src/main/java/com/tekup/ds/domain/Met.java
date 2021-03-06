package com.tekup.ds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Met {
    @Id
    private String nom;
    private double prix;
    @ManyToMany()
    private Set<Ticket> tickets;

    public Met(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }


}
