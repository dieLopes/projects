package com.diego.manager.config.container;

import org.testcontainers.containers.GenericContainer;

import static com.diego.manager.config.container.TestNetwork.NETWORK;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.2.6";
    public static final int DEFAULT_PORT = 27017;

    public MongoDbContainer() {
        super(DEFAULT_IMAGE_AND_TAG);
        withNetwork(NETWORK).withExposedPorts(DEFAULT_PORT);
    }

    public Integer getPort() {
        return getMappedPort(DEFAULT_PORT);
    }
}