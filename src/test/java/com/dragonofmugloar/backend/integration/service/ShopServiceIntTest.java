package com.dragonofmugloar.backend.integration.service;

import com.dragonofmugloar.backend.conf.IntegrationTest;
import com.dragonofmugloar.backend.dto.shop.ItemId;
import com.dragonofmugloar.backend.dto.task.AdId;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestExecutionListeners(inheritListeners = false,
    listeners =
        {
            DependencyInjectionTestExecutionListener.class,
            DirtiesContextTestExecutionListener.class
        }
)
@Slf4j
@IntegrationTest
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

    @DisplayName("Trying to byu items")
    @Test
    void purchaseItem() {

        if (gameInfo.getGold() < 100) {
            log.info("Not enough credit {}", gameInfo.getGold());
            getCredit();
        }

        Optional<ItemStatus> purchaseItem = shopService.purchaseItem(gameInfo.getGameId(), ItemId.of("wax"));

        assertTrue(purchaseItem.isPresent(), "Purchase healing acquired");

        log.info("Purchased {}", purchaseItem.get());

        assertEquals("true", purchaseItem.get().getShoppingSuccess());
    }

    private void getCredit() {
        List<Ad> allMessages = taskService.getAllMessages(gameInfo.getGameId());

        Ad ad = allMessages.stream().findFirst().orElseThrow();

        log.info("Task for solving: {}", ad);

        Optional<AdStatus> adStatus = taskService.solveTask(gameInfo.getGameId(), ad.getAdId());

        assertTrue(adStatus.isPresent(), "Solving result acquired");

        while (adStatus.isPresent() && adStatus.get().getLives() > 0 && !adStatus.get().isSuccess()) {
            adStatus = taskService.solveTask(gameInfo.getGameId(), ad.getAdId());
        }

        assertTrue(adStatus.isPresent() && adStatus.get().getGold() > 100, "Solving result is successful");
    }

}
