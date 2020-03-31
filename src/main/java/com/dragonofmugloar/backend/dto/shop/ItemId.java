package com.dragonofmugloar.backend.dto.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class ItemId {
    private final String identifier;

    public String toString() {
        return identifier;
    }
}
