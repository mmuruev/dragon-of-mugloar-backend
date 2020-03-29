package com.dragonofmugloar.backend.log;


import com.dragonofmugloar.backend.model.character.Reputation;
import com.dragonofmugloar.backend.model.common.GameInfo;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @AfterReturning(
        value = "execution(*  com.dragonofmugloar.backend.service.impl.GameServiceMugloar.startGame(..))",
        returning = "gameInfo")
    public void logWebClientGameInfoRequest(GameInfo gameInfo) {
        log.info("Game info {}", gameInfo);
    }

    @AfterReturning(
        value = "execution(*  com.dragonofmugloar.backend.service.impl.GameServiceMugloar.getReputation(..))",
        returning = "reputation")
    public void logWebClientReputationRequest(Reputation reputation) {
        log.info("Reputation {}", reputation);
    }
}
