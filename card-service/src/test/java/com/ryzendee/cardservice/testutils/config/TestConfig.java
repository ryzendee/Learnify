package com.ryzendee.cardservice.testutils.config;

import com.ryzendee.cardservice.testutils.facade.TestDatabaseFacade;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public TestDatabaseFacade testDBFacade() {
        return new TestDatabaseFacade();
    }
}
