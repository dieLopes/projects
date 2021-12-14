package com.diego.homebroker.api.v1;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

public abstract class BaseIT {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @LocalServerPort
    public int port;

    @BeforeEach
    public void setUpApiAccess() {
        RestAssured.port = port;
        RestAssured.basePath = contextPath;
    }

    public int getPort() {
        return port;
    }
}
