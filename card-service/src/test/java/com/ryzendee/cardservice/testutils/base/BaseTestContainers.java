package com.ryzendee.cardservice.testutils.base;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseTestContainers {

    private static final String POSTGRES_IMAGE = "postgres:15.6";

    @ServiceConnection
    protected static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE);


}
