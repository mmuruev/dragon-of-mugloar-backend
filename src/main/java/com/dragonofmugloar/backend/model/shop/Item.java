package com.dragonofmugloar.backend.model.shop;

import com.dragonofmugloar.backend.dto.shop.ItemId;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private ItemId id;

    private String name;

    private BigDecimal cost;
}
