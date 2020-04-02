package com.dragonofmugloar.backend.model.common;


import com.dragonofmugloar.backend.dto.common.GameId;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameInfo {
    private GameId gameId;
    private int lives;
    private BigDecimal gold = BigDecimal.ZERO;
    private int level;
    private int score;
    private int highScore;
    private int turn;

    public boolean isStillAlive() {
        return lives > 0;
    }

    public boolean isFullLives() {
        return lives > 1;
    }
}
