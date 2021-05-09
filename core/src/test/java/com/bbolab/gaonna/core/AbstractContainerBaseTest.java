package com.bbolab.gaonna.core;

import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {

    private static final String containerName = "mysql:8.0.23";
    private static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer(containerName);
        MY_SQL_CONTAINER.start();
    }
}