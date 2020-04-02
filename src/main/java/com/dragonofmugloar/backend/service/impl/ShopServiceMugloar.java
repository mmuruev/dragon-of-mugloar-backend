package com.dragonofmugloar.backend.service.impl;

import com.dragonofmugloar.backend.dto.common.GameId;

import com.dragonofmugloar.backend.dto.shop.ItemId;
import com.dragonofmugloar.backend.model.shop.Item;
import com.dragonofmugloar.backend.model.shop.ItemStatus;
import com.dragonofmugloar.backend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceMugloar implements ShopService {

    private final WebClient webClient;

    /**
     * %s - GameId
     */
    private final static String SHOP_ITEMS_ENDPOINT = "/%s/shop";

    /**
     * %s - GameId, %s- Unique item ID from the shop listing
     */
    private final static String PURCHASE_ITEM_ENDPOINT = "/%s/shop/buy/%s";

    public List<Item> getItemsList(GameId gameId) {
        return webClient.get()
            .uri(String.format(SHOP_ITEMS_ENDPOINT, gameId))
            .retrieve()
            .bodyToFlux(Item.class)
            .collectList()
            .block();
    }

    public Optional<ItemStatus> purchaseItem(GameId gameId, ItemId itemId) {
        return webClient.post()
            .uri(String.format(PURCHASE_ITEM_ENDPOINT, gameId, itemId))
            .retrieve()
            .bodyToMono(ItemStatus.class)
            .blockOptional();
    }
}
