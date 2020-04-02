package com.dragonofmugloar.backend.controller;


import com.dragonofmugloar.backend.dto.shop.ItemId;
import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import com.dragonofmugloar.backend.model.shop.Item;
import com.dragonofmugloar.backend.model.shop.ItemStatus;
import com.dragonofmugloar.backend.model.task.Ad;
import com.dragonofmugloar.backend.model.task.AdProbability;
import com.dragonofmugloar.backend.model.task.AdStatus;
import com.dragonofmugloar.backend.service.GameService;
import com.dragonofmugloar.backend.service.ShopService;
import com.dragonofmugloar.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameService gameService;

    private final TaskService taskService;

    private final ShopService shopService;

    ///
    private GameInfo gameInfo;

    private Map<ItemId, Item> alreadyBoughtItems = new HashMap<>(10);

    public GameInfo playRound() {
        if (gameInfo == null) {
            init();
        }

        getTaskAndSolve();

        if (!gameInfo.isStillAlive()) {
            return gameInfo;
        }

        Reputation reputation = gameService.getReputation(gameInfo.getGameId()).orElse(new Reputation());  // <-- unwise?
        shopThings();
        log.info("Reputation {}", reputation);
        return gameInfo;
    }

    private void shopThings() {
        List<Item> lastItemList = shopService.getItemsList(gameInfo.getGameId());

        if (!gameInfo.isFullLives()) {
            lastItemList.stream()
                .filter(item -> ShopService.HPOT_ID.equals(item.getId().getIdentifier()))
                .findFirst()
                .ifPresent(hp -> {
                    if (gameInfo.getGold().compareTo(hp.getCost()) >= 0) {
                        shopService.purchaseHealingPot(gameInfo.getGameId())
                            .ifPresent(hStatus -> {

                                if ("true".equals(hStatus.getShoppingSuccess())) {
                                    log.info("Successfully bough HPOT: {}", hp);
                                } else {
                                    log.error("Shopping HPOT unsuccessful {}", hStatus.getShoppingSuccess());
                                }

                                updateGameInfoAfterShop(hStatus);
                            });
                    } else {
                        log.info("No money for buy HPOT :(");
                    }
                });
        }

        lastItemList.stream()
            .filter(item -> !ShopService.HPOT_ID.equals(item.getId().getIdentifier()))
            .filter(item -> !alreadyBoughtItems.containsKey(item.getId()))    /// Exclude items which we already bought.. hm.. maybe does exist better strategy?
            .findFirst()
            .ifPresent(it -> {
                if (gameInfo.getGold().add(BigDecimal.valueOf(100)).compareTo(it.getCost()) >= 0) {
                    shopService.purchaseItem(gameInfo.getGameId(), it.getId())
                        .ifPresent(itemStatus -> {
                            if ("true".equals(itemStatus.getShoppingSuccess())) {
                                alreadyBoughtItems.put(it.getId(), it);
                                log.info("Successfully bough Item: {}", it);
                                updateGameInfoAfterShop(itemStatus);
                            } else {
                                log.error("Shopping unsuccessful {}  {}", it, itemStatus.getShoppingSuccess());
                            }
                        });
                } else {
                    log.info("No money for buy item {}", it);
                }
            });

    }


    private void getTaskAndSolve() {
        final List<Ad> ads = updateTasks();
        findTask(ads).ifPresent(this::solve);
    }

    private void solve(Ad task) {
        Optional<AdStatus> adStatus = taskService.solveTask(gameInfo.getGameId(), task.getAdId());

        adStatus.ifPresent(this::updateGameInfoAfterTask);

        log.info("Solving task {} with success: {}", task, adStatus.map(AdStatus::isSuccess).orElse(false));
    }

    private Optional<Ad> findTask(List<Ad> tasks) {
        return tasks.stream()
            .filter(task -> !task.getMessage().contains("Steal"))
            //   .filter(task -> task.getProbability().getLevel() > AdProbability.LEVEL_1.getLevel())
            .max(Comparator.comparingDouble(GameController::computeTaskRate))
            ;
    }

    private static double computeTaskRate(Ad ad) {
        return new BigDecimal(ad.getReward()).divide(BigDecimal.valueOf(ad.getProbability().getLevel() + 1), RoundingMode.CEILING).doubleValue();
    }

    private List<Ad> updateTasks() {
        return taskService.getAllMessages(gameInfo.getGameId());
    }

    private void updateGameInfoAfterTask(AdStatus adStatus) {
        gameInfo.setTurn(adStatus.getTurn());
        gameInfo.setGold(adStatus.getGold());
        gameInfo.setLives(adStatus.getLives());
        gameInfo.setScore(adStatus.getScore());
        gameInfo.setHighScore(adStatus.getHighScore());
    }

    private void updateGameInfoAfterShop(ItemStatus itemStatus) {
        gameInfo.setTurn(itemStatus.getTurn());
        gameInfo.setGold(itemStatus.getGold());
        gameInfo.setLevel(itemStatus.getLevel());
        gameInfo.setLives(itemStatus.getLives());
    }

    private void init() {
        gameInfo = gameService.startGame().orElseThrow(() -> new RuntimeException("Fail create new game"));
    }
}
