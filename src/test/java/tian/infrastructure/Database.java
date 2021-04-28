package tian.infrastructure;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

public class Database implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>(
            "postgres:latest")
            .withDatabaseName("pg_container")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(5432);

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        return Collections.singletonMap("quarkus.datasource.jdbc.url", DATABASE.
                getJdbcUrl());
    }

    @Override
    public void stop() {
        DATABASE.stop();

    }
}
