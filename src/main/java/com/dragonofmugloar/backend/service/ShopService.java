package com.dragonofmugloar.backend.service;

import com.dragonofmugloar.backend.dto.common.GameId;
import com.dragonofmugloar.backend.dto.shop.ItemId;
import com.dragonofmugloar.backend.model.shop.Item;
import com.dragonofmugloar.backend.model.shop.ItemStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ShopService {

    String HPOT_ID = "hpot";

    List<Item> getItemsList(GameId gameId);

    Optional<ItemStatus> purchaseItem(GameId gameId, ItemId itemId);

    default Optional<ItemStatus> purchaseHealingPot(GameId gameId) {
        return purchaseItem(gameId, ItemId.of(HPOT_ID));
    }
}
