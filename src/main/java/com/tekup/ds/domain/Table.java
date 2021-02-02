package com.tekup.ds.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@javax.persistence.Table(name = "ds_table")
@NoArgsConstructor @Getter @Setter
public class Table implements Serializable {
    @Id
    private int numero;
    private int nbCovert;
    private String type;
    private double supplement;
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    public Table(int numero, int nbCovert, String type, double supplement) {
        this.numero = numero;
        this.nbCovert = nbCovert;
        this.type = type;
        this.supplement = supplement;
    }

}
