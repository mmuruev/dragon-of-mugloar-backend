package com.dragonofmugloar.backend.model.character;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Reputation {
    private BigDecimal people;
    private BigDecimal state;
    private BigDecimal underworld;

    public Reputation() {
        people = BigDecimal.ZERO;
        state = BigDecimal.ZERO;
        underworld = BigDecimal.ZERO;
    }
}
