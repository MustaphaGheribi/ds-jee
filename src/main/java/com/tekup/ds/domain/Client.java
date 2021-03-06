package com.tekup.ds.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private String courriel;
    private String telephone;
    @JsonManagedReference(value = "client")
    @OneToMany(mappedBy = "client")
    private Set<Ticket> tickets;

    public Client(String nom, LocalDate dateDeNaissance, String courriel, String telephone, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.courriel = courriel;
        this.telephone = telephone;
    }
}
