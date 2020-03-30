package com.dragonofmugloar.backend.test;


import com.dragonofmugloar.backend.App;
import com.dragonofmugloar.backend.app.GameRunner;
import com.dragonofmugloar.backend.model.common.GameInfo;
import com.dragonofmugloar.backend.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
@TestExecutionListeners(inheritListeners = false,
    listeners =
        {
            DependencyInjectionTestExecutionListener.class,
            DirtiesContextTestExecutionListener.class
        }
)
public abstract class ApplicationTesting {
    @MockBean
    private GameRunner gameRunner; // avoid app run


    @Autowired
    private GameService gameService;


    protected GameInfo gameInfo;

    @BeforeEach
    public void setUpGame() {
        gameInfo = Optional.ofNullable(gameInfo).or(() -> gameService.startGame()).orElse(null);
        assertNotNull(gameInfo, "No Game data from server");
    }
}
