package com.dragonofmugloar.backend.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class GameId {

    private final String identifier;

    public String toString() {
        return identifier;
    }
}
