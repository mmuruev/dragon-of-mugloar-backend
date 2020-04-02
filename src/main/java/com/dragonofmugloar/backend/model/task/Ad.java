package com.dragonofmugloar.backend.model.task;


import com.dragonofmugloar.backend.dto.task.AdId;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ad {
    private AdId adId;

    private String message;

    private String reward;

    private int expiresIn;

    private AdProbability probability;

    private String encrypted; // WTF? no mention in API doc
}
