package com.diego.taskboard.api.v1.controller;

import com.diego.taskboard.api.v1.dto.contact.ContactResponseListDTO;
import com.diego.taskboard.api.v1.dto.user.UserCreateDTO;
import com.diego.taskboard.api.v1.dto.user.UserResponseDTO;
import com.diego.taskboard.api.v1.dto.user.UserResponseListDTO;
import com.diego.taskboard.api.v1.dto.user.UserUpdateDTO;
import com.diego.taskboard.api.v1.mapper.ContactMapper;
import com.diego.taskboard.api.v1.mapper.UserMapper;
import com.diego.taskboard.domain.User;
import com.diego.taskboard.service.UserPersistenceService;
import com.diego.taskboard.service.UserSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "users")
public class UserController {

    private final UserPersistenceService userPersistenceService;
    private final UserSearchService userSearchService;

    public UserController(UserPersistenceService userPersistenceService,
                          UserSearchService userSearchService) {
        this.userPersistenceService = userPersistenceService;
        this.userSearchService = userSearchService;
    }

    @ApiOperation(value = "Return user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(value = "/{id}", produces="application/json")
    public ResponseEntity<UserResponseDTO> getById (
            @NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(UserMapper.entityToDTO(userSearchService.findById(id)));
    }

    @ApiOperation(value = "Return filtered users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<UserResponseListDTO> getAll () {
        return ResponseEntity.ok(new UserResponseListDTO(UserMapper.entitiesToDTOs(userSearchService.findAll())));
    }

    @ApiOperation(value = "Return filtered users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(value = "/filter", produces="application/json")
    public ResponseEntity<UserResponseListDTO> get (
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "contact", required = false) String contact) {
        return ResponseEntity.ok(new UserResponseListDTO(
                UserMapper.entitiesToDTOs(userSearchService.findByNameAndContact(name, contact))));
    }

    @ApiOperation(value = "Return all user contacts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(value = "/{id}/contacts", produces="application/json")
    public ResponseEntity<ContactResponseListDTO> getContacts (
            @NonNull @PathVariable(value = "id") String id) {
        User user = userSearchService.findById(id);
        return ResponseEntity.ok(new ContactResponseListDTO(ContactMapper.entitiesToDTOs(user.getContacts())));
    }

    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Register was created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<UserResponseDTO> create (@RequestBody UserCreateDTO userCreateDTO) {
        User user = userPersistenceService.save(UserMapper.createDtoToEntity(userCreateDTO));
        return new ResponseEntity<>(UserMapper.entityToDTO(user), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity<UserResponseDTO> update (
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userPersistenceService.update(id, UserMapper.updateDtoToEntity(userUpdateDTO));
        return ResponseEntity.ok(UserMapper.entityToDTO(user));
    }

    @ApiOperation(value = "Remove user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable(value = "id") String id) {
        userPersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}