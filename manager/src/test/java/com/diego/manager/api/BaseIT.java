package com.diego.manager.api;

import com.mongodb.BasicDBObject;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class BaseIT {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @LocalServerPort
    public int port;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUpApiAccess() {
        RestAssured.port = port;
        RestAssured.basePath = contextPath;
    }

    @BeforeEach
    public void initDb () {
        for (String collectionName : mongoTemplate.getCollectionNames()) {
            if (!collectionName.startsWith("system.")) {
                mongoTemplate.getCollection(collectionName).deleteMany(new BasicDBObject());
            }
        }
    }

    public int getPort() {
        return port;
    }
}
