package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.service.UserPersistenceService;
import com.example.springbootmongodb.service.UserSearchService;
import com.example.springbootmongodb.controller.dto.UserCreateDTO;
import com.example.springbootmongodb.controller.dto.UserResponseDTO;
import com.example.springbootmongodb.controller.dto.UserResponseListDTO;
import com.example.springbootmongodb.controller.dto.UserUpdateDTO;
import com.example.springbootmongodb.domain.User;
import com.example.springbootmongodb.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserPersistenceService userPersistenceService;
    private UserSearchService userSearchService;

    public UserController(UserPersistenceService userPersistenceService,
        UserSearchService userSearchService) {
        this.userPersistenceService = userPersistenceService;
        this.userSearchService = userSearchService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> getById (@NonNull @PathVariable(value = "id") String id) {
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
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        User user = userPersistenceService.update(id, UserMapper.updateDtoToEntity(userUpdateDTO));
        return ResponseEntity.ok(UserMapper.entityToDTO(user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable(value = "id") String id) {
        userPersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}