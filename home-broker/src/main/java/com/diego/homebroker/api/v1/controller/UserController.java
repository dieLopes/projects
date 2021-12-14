package com.diego.homebroker.api.v1.controller;

import com.diego.homebroker.api.v1.dto.user.UserCreateDTO;
import com.diego.homebroker.api.v1.dto.user.UserResponseDTO;
import com.diego.homebroker.api.v1.mapper.UserMapper;
import com.diego.homebroker.domain.User;
import com.diego.homebroker.service.UserPersistenceService;
import com.diego.homebroker.service.UserSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "/users")
public class UserController {

    private final UserSearchService userSearchService;
    private final UserPersistenceService userPersistenceService;

    public UserController(UserSearchService userSearchService,
                          UserPersistenceService userPersistenceService) {
        this.userSearchService = userSearchService;
        this.userPersistenceService = userPersistenceService;
    }

    @ApiOperation(value = "Return user by cpf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(path = "/{cpf}", produces="application/json")
    public ResponseEntity<UserResponseDTO> findByCpf (
            @ApiParam(value = "User cpf", required = true)
            @NonNull @PathVariable(value = "cpf") String cpf) {
        return ResponseEntity.ok(UserMapper.entityToDTO(userSearchService.findByCpf(cpf)));
    }

    @ApiOperation(value = "Create user")
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
}
