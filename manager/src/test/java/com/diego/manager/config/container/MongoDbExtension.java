package com.diego.manager.config.container;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MongoDbExtension implements BeforeAllCallback, AfterAllCallback {

    public static MongoDbContainer mongoDbContainer;

    @Override
    public void beforeAll(ExtensionContext context) {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
        System.setProperty("MONGO_DB_HOST", mongoDbContainer.getContainerIpAddress());
        System.setProperty("MONGO_DB_PORT", String.valueOf(mongoDbContainer.getPort()));
        System.setProperty("MONGO_DB_NAME", "MDB");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        mongoDbContainer.stop();
        System.clearProperty("MONGO_DB_HOST");
        System.clearProperty("MONGO_DB_PORT");
        System.clearProperty("MONGO_DB_NAME");
    }
}
