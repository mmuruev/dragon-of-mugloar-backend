package com.dragonofmugloar.backend.service;

import com.dragonofmugloar.backend.dto.common.GameId;
import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GameService {
    Optional<GameInfo> startGame();

    Optional<Reputation> getReputation(GameId gameId);
}
