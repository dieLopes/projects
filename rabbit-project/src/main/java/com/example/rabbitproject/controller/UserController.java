package com.example.rabbitproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.rabbitproject.controller.dto.UserCreateDTO;
import com.example.rabbitproject.controller.dto.UserResponseDTO;
import com.example.rabbitproject.controller.dto.UserResponseListDTO;
import com.example.rabbitproject.controller.dto.UserSendDTO;
import com.example.rabbitproject.controller.dto.UserUpdateDTO;
import com.example.rabbitproject.domain.User;
import com.example.rabbitproject.mapper.UserMapper;
import com.example.rabbitproject.queue.UserQueueSender;
import com.example.rabbitproject.rest.UserRestSender;
import com.example.rabbitproject.service.UserPersistenceService;
import com.example.rabbitproject.service.UserSearchService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserPersistenceService userPersistenceService;
    private UserSearchService userSearchService;
    private UserQueueSender userQueueSender;
    private UserRestSender userRestSender;

    public UserController(UserPersistenceService userPersistenceService,
        UserSearchService userSearchService,
        UserQueueSender userQueueSender,
        UserRestSender userRestSender) {
        this.userPersistenceService = userPersistenceService;
        this.userSearchService = userSearchService;
        this.userQueueSender = userQueueSender;
        this.userRestSender = userRestSender;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> getById (@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(UserMapper.entityToDTO(userSearchService.findById(id)));
    }

    @GetMapping(value = "/")
    public ResponseEntity<UserResponseListDTO> getAll () {
        return ResponseEntity.ok(new UserResponseListDTO(UserMapper.entitiesToDTOs(userSearchService.findAll())));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create (@RequestBody UserCreateDTO userCreateDTO) throws Exception {
        User user = userPersistenceService.save(UserMapper.createDtoToEntity(userCreateDTO));
        return new ResponseEntity<>(UserMapper.entityToDTO(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> update (
        @PathVariable(value = "id") String id,
        @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        User user = userPersistenceService.update(id, UserMapper.updateDtoToEntity(userUpdateDTO));
        return ResponseEntity.ok(UserMapper.entityToDTO(user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable(value = "id") String id) {
        userPersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rabbit")
    public ResponseEntity send (@RequestBody UserSendDTO user) {
        userQueueSender.send(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rest")
    public ResponseEntity sendAPI (@RequestBody UserSendDTO user) {
        userRestSender.sendAPI(user);
        return ResponseEntity.noContent().build();
    }
}