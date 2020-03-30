package com.dragonofmugloar.backend.service;

import com.dragonofmugloar.backend.dto.common.GameId;
import com.dragonofmugloar.backend.dto.task.AdId;
import com.dragonofmugloar.backend.model.task.Ad;
import com.dragonofmugloar.backend.model.task.AdStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    List<Ad> getAllMessages(GameId gameId);

    Optional<AdStatus> solveTask(GameId gameId, AdId adventureId);
}
