package com.dragonofmugloar.backend.dto.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class AdId {
    private final String identifier;

    public String toString() {
        return identifier;
    }
}
