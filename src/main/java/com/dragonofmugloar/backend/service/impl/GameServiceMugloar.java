package com.dragonofmugloar.backend.service.impl;


import com.dragonofmugloar.backend.dto.common.GameId;
import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import com.dragonofmugloar.backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceMugloar implements GameService {

    private final WebClient webClient;

    private final static String START_GAME_ENDPOINT = "/game/start";

    /**
     * %s - GameId
     */
    private final static String CHARACTER_REPUTATION_ENDPOINT = "/%s/investigate/reputation";

    public Optional<GameInfo> startGame() {
        return webClient.post()
            .uri(START_GAME_ENDPOINT)
            .retrieve()
            .bodyToMono(GameInfo.class)
            .blockOptional();
    }

    public Optional<Reputation> getReputation(GameId gameId) {
        return webClient.post()
            .uri(String.format(CHARACTER_REPUTATION_ENDPOINT, gameId.getIdentifier()))
            .retrieve()
            .bodyToMono(Reputation.class)
            .blockOptional();
    }
}
