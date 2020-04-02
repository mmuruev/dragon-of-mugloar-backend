package com.dragonofmugloar.backend.app;


import com.dragonofmugloar.backend.controller.GameController;
import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import com.dragonofmugloar.backend.model.shop.Item;
import com.dragonofmugloar.backend.model.shop.ItemStatus;
import com.dragonofmugloar.backend.model.task.Ad;
import com.dragonofmugloar.backend.service.GameService;
import com.dragonofmugloar.backend.service.ShopService;
import com.dragonofmugloar.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameRunner implements CommandLineRunner {

    final private GameService gameService;
    final private TaskService taskService;
    final private ShopService shopService;

    final private GameController gameController;


    @Override
    public void run(String... args) {
        GameInfo gameInfo;
        do {
            gameInfo = gameController.playRound();
            log.info("Game status {}", gameInfo);
        } while (gameInfo.isStillAlive());

        log.info("Game Over! With statics {}", gameInfo);
    }
}
