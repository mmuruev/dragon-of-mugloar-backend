package com.dragonofmugloar.backend.integration.service;

import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.service.GameService;
import com.dragonofmugloar.backend.test.ApplicationTesting;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestExecutionListeners(inheritListeners = false,
    listeners =
        {
            DependencyInjectionTestExecutionListener.class,
            DirtiesContextTestExecutionListener.class
        }
)
@Slf4j
class GameServiceIntTest extends ApplicationTesting {

    @Autowired
    private GameService gameService;


    @BeforeEach
    void setUp() {
        gameInfo = Optional.ofNullable(gameInfo).or(() -> gameService.startGame()).orElse(null);
        assertNotNull(gameInfo, "No Game data from server");
    }

    @DisplayName("Test WebClient and Start Game")
    @Test
    void startGame() {

        log.info("Init game with {}", gameInfo);

        assertNotNull(gameInfo.getGameId(), "Game id check");

        assertEquals(BigDecimal.ZERO, gameInfo.getGold());
        assertEquals(0, gameInfo.getLevel());
        assertEquals(0, gameInfo.getTurn());
        assertEquals(3, gameInfo.getLives());
    }

    @DisplayName("Test Reputation response")
    @Test
    void getReputation() {
        Optional<Reputation> reputation = gameService.getReputation(gameInfo.getGameId());

        assertTrue(reputation.isPresent(), "No Reputation data from server");

        log.info("Reputation: {}", reputation.get());

        assertEquals(BigDecimal.ZERO, reputation.get().getPeople());
        assertEquals(BigDecimal.ZERO, reputation.get().getState());
        assertEquals(BigDecimal.ZERO, reputation.get().getUnderworld());
    }
}

