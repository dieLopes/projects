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
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<UserResponseDTO> getById (@PathVariable(value = "id") String id) throws NotFoundException {
        return new ResponseEntity<>(UserMapper.entityToDTO(userSearchService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/")
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<UserResponseListDTO> getAll () {
        return new ResponseEntity<>(new UserResponseListDTO(UserMapper.entitiesToDTOs(userSearchService.findAll())),
            HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ResponseEntity<UserResponseDTO> create (@RequestBody UserCreateDTO userCreateDTO) throws Exception {
        User user = userPersistenceService.save(UserMapper.createDtoToEntity(userCreateDTO));
        return new ResponseEntity<>(UserMapper.entityToDTO(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<UserResponseDTO> update (
        @PathVariable(value = "id") String id,
        @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        User user = userPersistenceService.update(id, UserMapper.updateDtoToEntity(userUpdateDTO));
        return new ResponseEntity<>(UserMapper.entityToDTO(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public ResponseEntity delete (@PathVariable(value = "id") String id) throws Exception {
        userPersistenceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/rabbit")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public ResponseEntity send (@RequestBody UserSendDTO user) {
        userQueueSender.send(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/rest")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public ResponseEntity sendAPI (@RequestBody UserSendDTO user) {
        userRestSender.sendAPI(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
