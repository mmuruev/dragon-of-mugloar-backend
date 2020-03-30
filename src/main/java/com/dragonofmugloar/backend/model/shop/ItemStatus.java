package com.dragonofmugloar.backend.model.shop;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemStatus {

    private String shoppingSuccess;

    private BigDecimal gold;

    private int lives;

    private int level;

    private int turn;
}
