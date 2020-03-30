package com.dragonofmugloar.backend.service.impl;


import com.dragonofmugloar.backend.dto.task.AdId;
import com.dragonofmugloar.backend.dto.common.GameId;
import com.dragonofmugloar.backend.model.task.AdStatus;
import com.dragonofmugloar.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.dragonofmugloar.backend.model.task.Ad;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceMugloar implements TaskService {

    private final WebClient webClient;

    /**
     * %s - GameId
     */
    private final static String GET_ALL_MESSAGES_ENDPOINT = "/%s/messages";

    /**
     * %s - GameId , %s - Unique message (ad) ID
     */
    private final static String SOLVE_MESSAGE_ENDPOINT = "/%s/solve/%s";

    public List<Ad> getAllMessages(GameId gameId) {
        return webClient.get()
            .uri(String.format(GET_ALL_MESSAGES_ENDPOINT, gameId))
            .retrieve()
            .bodyToFlux(Ad.class)
            .collectList()
            .block();
    }

    public Optional<AdStatus> solveTask(GameId gameId, AdId adventureId) {
        return webClient.post()
            .uri(String.format(SOLVE_MESSAGE_ENDPOINT, gameId, adventureId))
            .retrieve()
            .bodyToMono(AdStatus.class)
            .blockOptional();
    }
}
