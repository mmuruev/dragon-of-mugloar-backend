package com.dragonofmugloar.backend.model.task;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdStatus {
    private boolean success;

    private int lives;

    private BigDecimal gold = BigDecimal.ZERO;

    private int score;

    private int highScore;

    private int turn;

    private String message;

}
