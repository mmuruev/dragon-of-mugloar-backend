package com.dragonofmugloar.backend.app;


import com.dragonofmugloar.backend.controller.GameController;
import com.dragonofmugloar.backend.model.common.GameInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameRunner implements CommandLineRunner {

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
