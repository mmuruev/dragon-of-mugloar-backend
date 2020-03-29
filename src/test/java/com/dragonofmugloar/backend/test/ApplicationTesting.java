package com.dragonofmugloar.backend.test;


import com.dragonofmugloar.backend.App;
import com.dragonofmugloar.backend.app.GameRunner;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

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
}
