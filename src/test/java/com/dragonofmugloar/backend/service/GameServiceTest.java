package com.dragonofmugloar.backend.service;

import com.dragonofmugloar.backend.conf.IntegrationTest;
import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import com.dragonofmugloar.backend.test.ApplicationTesting;
import lombok.extern.slf4j.Slf4j;
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
