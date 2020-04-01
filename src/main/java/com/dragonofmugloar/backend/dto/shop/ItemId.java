package com.dragonofmugloar.backend.dto.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class ItemId {
    private final String identifier;

    public String toString() {
        return identifier;
    }
}
