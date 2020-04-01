package com.dragonofmugloar.backend.integration.service;

import com.dragonofmugloar.backend.dto.shop.ItemId;
import com.dragonofmugloar.backend.model.shop.Item;
import com.dragonofmugloar.backend.model.shop.ItemStatus;
import com.dragonofmugloar.backend.model.task.Ad;
import com.dragonofmugloar.backend.model.task.AdStatus;
import com.dragonofmugloar.backend.service.ShopService;
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

import java.math.BigDecimal;
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
public class ShopServiceIntTest extends ApplicationTesting {

    @Autowired
    private ShopService shopService;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        assertNotNull(gameInfo.getGameId(), "GameInfo is required for testing");
    }

    @DisplayName("Trying to fetch items list")
    @Test
    void getItemsList() {
        List<Item> itemsList = shopService.getItemsList(gameInfo.getGameId());

        assertFalse(itemsList.isEmpty());

        log.info("Shop items list {}", itemsList);
    }

    @DisplayName("Trying to buy items")
    @Test
    void purchaseItem() {

        if (gameInfo.getGold().compareTo(BigDecimal.valueOf(100)) < 0) {
            log.info("Not enough credit {}", gameInfo.getGold());
            getCredit();
        }

        Optional<ItemStatus> purchaseItem = shopService.purchaseItem(gameInfo.getGameId(), ItemId.of("wax"));

        assertTrue(purchaseItem.isPresent(), "Purchase healing acquired");

        log.info("Purchased {}", purchaseItem.get());

        assertEquals("true", purchaseItem.get().getShoppingSuccess());
    }

    private void getCredit() {
        Optional<AdStatus> adStatus = Optional.of(new AdStatus());

        while (adStatus.isPresent() && adStatus.get().getGold().compareTo(BigDecimal.valueOf(100)) <= 0) {
            Ad ad = taskService.getAllMessages(gameInfo.getGameId()).stream().findFirst().orElseThrow();
            log.info("Task for solving: {}", ad);
            adStatus = solver(ad);
            log.info("Solved {}", adStatus.get());
        }

        assertTrue(adStatus.isPresent(), "Solving result acquired");
        assertTrue(adStatus.get().getGold().compareTo(BigDecimal.valueOf(100)) >= 0, "Solving result is successful");
    }

    private Optional<AdStatus> solver(Ad ad) {
        Optional<AdStatus> adStatus;

        int expiresIn = ad.getExpiresIn();
        log.info("Game: {} Ad: {} Expires {}", gameInfo.getGameId(), ad.getAdId(), expiresIn);
        adStatus = taskService.solveTask(gameInfo.getGameId(), ad.getAdId());
        log.info("Game: {} Ad: {} Status {}", gameInfo.getGameId(), ad.getAdId(), adStatus);
        return adStatus;
    }

}
