package com.tekup.ds.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@javax.persistence.Table(name = "ds_table")
@NoArgsConstructor
@Getter @Setter
public class Table implements Serializable {
    @Id
    private int numero;
    private int nbCovert;
    private String type;
    private double supplement;
    @JsonManagedReference(value = "table")
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();

    public Table(int numero, int nbCovert, String type, double supplement) {
        this.numero = numero;
        this.nbCovert = nbCovert;
        this.type = type;
        this.supplement = supplement;
    }

}
