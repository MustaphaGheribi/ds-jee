package com.tekup.ds.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Ticket implements Serializable {
    @Id
    @GeneratedValue
    private int numero;
    private LocalDateTime date;
    private int nbCouvert;
    private double addition;
    @JsonBackReference(value = "table")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Table.class)
    private Table table;

    @JsonBackReference(value = "client")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToMany()
    private Set<Met> mets;

    public Ticket(LocalDateTime date, int nbCouvert, double addition) {
        this.date = date;
        this.nbCouvert = nbCouvert;
        this.addition = addition;
    }
}
