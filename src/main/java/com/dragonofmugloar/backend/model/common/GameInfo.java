package com.dragonofmugloar.backend.model.common;


import com.dragonofmugloar.backend.dto.common.GameId;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameInfo {
    private GameId gameId;
    private int lives;
    private int gold;
    private int level;
    private int score;
    private int highScore;
    private int turn;
}
