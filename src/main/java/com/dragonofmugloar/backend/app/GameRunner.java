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


    private void oldTestLogic() {
        final Optional<GameInfo> gameInfo = gameService.startGame();

        log.info("Game info {}", gameInfo);

        Optional<Reputation> reputation = gameService.getReputation(gameInfo.get().getGameId());

        log.info("Reputation {}", reputation);

        List<Ad> allTasks = taskService.getAllMessages(gameInfo.get().getGameId());

        log.info("All Tasks {}", allTasks);

//        allTasks.forEach(task -> {
//            Optional<AdStatus> adStatus = taskService.solveTask(gameInfo.get().getGameId(), task.getAdId());
//            log.info("Task {} solved status {}", task.getAdId(), adStatus);
//        });


        List<Item> itemsList = shopService.getItemsList(gameInfo.get().getGameId());

        log.info("All items {}", itemsList);


        itemsList.stream().findFirst().ifPresent(item -> {
            Optional<ItemStatus> itemStatus = shopService.purchaseItem(gameInfo.get().getGameId(), item.getId());
            log.info("Item {} puchased status {}", item.getId(), itemStatus);
        });

        reputation = gameService.getReputation(gameInfo.get().getGameId());

        log.info("Reputation {}", reputation);
    }
}
