package com.dragonofmugloar.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@TestExecutionListeners(inheritListeners = false,
    listeners =
        {
            DependencyInjectionTestExecutionListener.class,
            DirtiesContextTestExecutionListener.class
        }
)
@Slf4j
class GameServiceTest {

    @DisplayName("Test WebClient and Start Game")
    @Test
    void startGame() {
    }

    @DisplayName("Test Reputation response")
    @Test
    void getReputation() {
    }
}
