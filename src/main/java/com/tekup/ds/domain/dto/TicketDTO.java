package com.tekup.ds.domain.dto;

import com.tekup.ds.domain.Met;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class TicketDTO {
    @NotNull(message = "Le ticket doit avoir un numero")
    private int numero;

    @NotNull(message = "Le ticket doit avoir un nombre couvert")
    private int nbCouvert;
    @NotNull(message = "Le ticket doit avoir au moins un met")
    private Set<Met> mets;

}
