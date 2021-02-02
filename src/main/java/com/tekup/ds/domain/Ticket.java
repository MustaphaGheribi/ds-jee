package com.tekup.ds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Ticket implements Serializable {
    @Id
    @GeneratedValue
    private int numero;
    private LocalDateTime date;
    private int nbCouvert;
    private double addition;

    @ManyToOne(fetch = FetchType.EAGER)
    private Table table;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
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
