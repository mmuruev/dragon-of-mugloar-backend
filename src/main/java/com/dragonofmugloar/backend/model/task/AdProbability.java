package com.dragonofmugloar.backend.model.task;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum AdProbability {

    LEVEL_0(0),
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3),
    LEVEL_4(4),
    LEVEL_5(5),
    LEVEL_6(6),
    LEVEL_7(7),
    LEVEL_8(8),
    LEVEL_9(9),
    LEVEL_10(10);

    private int level;

    @JsonCreator
    public static AdProbability of(String probability) {
        return getDifficulty(probability);
    }

    private static AdProbability getDifficulty(String probability) {
        switch (probability) {

            case "Walk in the park":  // How it's different?
            case "Piece of cake":
                return LEVEL_0;

            case "Sure thing":
                return LEVEL_1;
            case "Quite likely":
                return LEVEL_2;
            case "Hmmm....":
                return LEVEL_3;
            case "Gamble":
                return LEVEL_4;
            case "Risky":
                return LEVEL_5;
            case "Rather detrimental":
                return LEVEL_6;
            case "Playing with fire":
                return LEVEL_7;
            case "Suicide mission":
                return LEVEL_8;
            case "Impossible":
                return LEVEL_9;

            default:
                return LEVEL_10;
        }
    }
}
