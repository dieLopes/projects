package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseListDTO;
import com.inter.desafiointer.api.v1.mapper.UserMapper;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.service.UserPersistenceService;
import com.inter.desafiointer.service.UserSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "User Service")
@Controller
@RequestMapping(ApiPath.API_PATH + "/users")
public class UserController {

    private final UserSearchService userSearchService;
    private final UserPersistenceService userPersistenceService;

    public UserController(UserSearchService userSearchService,
                          UserPersistenceService userPersistenceService) {
        this.userSearchService = userSearchService;
        this.userPersistenceService = userPersistenceService;
    }

    @ApiOperation(value = "Return filtered users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<UserResponseListDTO> find () {
        return ResponseEntity.ok(new UserResponseListDTO(UserMapper.entitiesToDTOs(userSearchService.findAll())));
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
