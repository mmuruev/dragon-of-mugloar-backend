package com.dragonofmugloar.backend.model.task;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdStatus {
    private boolean success;

    private int lives;

    private int gold;

    private int score;

    private int highScore;

    private int turn;

    private String message;

}
