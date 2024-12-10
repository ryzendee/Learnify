package com.ryzendee.cardservice;

import com.ryzendee.cardservice.testutils.base.BaseTestContainers;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CardServiceApplicationTests extends BaseTestContainers {

    @BeforeAll
    static void startContainers() {
        POSTGRE_SQL_CONTAINER.start();
    }

    @Test
    void contextLoads() {

    }

}
