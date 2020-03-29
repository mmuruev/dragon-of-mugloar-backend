package com.dragonofmugloar.backend.service;

import com.dragonofmugloar.backend.conf.IntegrationTest;
import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import com.dragonofmugloar.backend.test.ApplicationTesting;
import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

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
@IntegrationTest
class GameServiceTest extends ApplicationTesting {

    @Autowired
    private GameService gameService;

    private GameInfo gameInfo;

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

        assertEquals(0, gameInfo.getGold());
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

        assertEquals(0, reputation.get().getPeople());
        assertEquals(0, reputation.get().getState());
        assertEquals(0, reputation.get().getUnderworld());
    }
}
