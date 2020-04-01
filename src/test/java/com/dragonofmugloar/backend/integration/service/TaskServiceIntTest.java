package com.dragonofmugloar.backend.integration.service;

import com.dragonofmugloar.backend.model.task.Ad;
import com.dragonofmugloar.backend.model.task.AdStatus;
import com.dragonofmugloar.backend.service.TaskService;
import com.dragonofmugloar.backend.test.ApplicationTesting;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.List;
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
public class TaskServiceIntTest extends ApplicationTesting {

    @Autowired
    private TaskService taskService;


    @BeforeEach
    void setUp() {
        assertNotNull(gameInfo.getGameId(), "GameInfo is required for testing");
    }

    @DisplayName("Look into all quests list")
    @Test
    void getAllMessages() {
        List<Ad> allMessages = taskService.getAllMessages(gameInfo.getGameId());

        log.info("Tasks : {}", allMessages);

        assertFalse(allMessages.isEmpty());
    }

    @DisplayName("Trying to solve first quest")
    @Test
    void solveTask() {
        List<Ad> allMessages = taskService.getAllMessages(gameInfo.getGameId());

        Ad ad = allMessages.stream().findFirst().orElseThrow();

        log.info("Task for solving: {}", ad);

        Optional<AdStatus> adStatus = taskService.solveTask(gameInfo.getGameId(), ad.getAdId());

        assertTrue(adStatus.isPresent(), "Solving result acquired");

        log.info("Solving {}", adStatus.get());
    }


    @DisplayName("Trying to solve first quest until success")
    @Test
    void successfulSolveTask() {
        List<Ad> allMessages = taskService.getAllMessages(gameInfo.getGameId());

        Ad ad = allMessages.stream().findFirst().orElseThrow();

        log.info("Task for solving: {}", ad);

        Optional<AdStatus> adStatus = taskService.solveTask(gameInfo.getGameId(), ad.getAdId());

        assertTrue(adStatus.isPresent(), "Solving result acquired");

        if (adStatus.get().isSuccess()) {
            return;
        }

        while (adStatus.isPresent() && adStatus.get().getLives() > 0 && !adStatus.get().isSuccess()) {
            adStatus = taskService.solveTask(gameInfo.getGameId(), ad.getAdId());
        }

        assertTrue(adStatus.isPresent() && adStatus.get().isSuccess(), "Solving result is successful");
    }
}
