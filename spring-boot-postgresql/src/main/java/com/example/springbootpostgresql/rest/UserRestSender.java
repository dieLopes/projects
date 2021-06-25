package com.example.springbootpostgresql.rest;

import com.example.springbootpostgresql.controller.dto.UserSendDTO;
import com.example.springbootpostgresql.domain.User;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestSender {

    private RestTemplate restTemplate;

    public UserRestSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User send(UserSendDTO user) {
        var createUserUrl = "http://localhost:8081/users";
        //var createUserUrl = "http://importusers.us-e2.cloudhub.io/users";

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var userJsonObject = new JSONObject();
        userJsonObject.put("firstName", user.getFirstName());
        userJsonObject.put("lastName", user.getLastName());
        userJsonObject.put("address", user.getAddress());

        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);
        return restTemplate.postForObject(createUserUrl, request, User.class);
    }
}
