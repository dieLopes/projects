package com.diego.homebroker.api.v1.controller;

import com.diego.homebroker.api.v1.dto.order.OrderRandomCreateDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseRandomDTO;
import com.diego.homebroker.api.v1.dto.order.OrderCreateDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseListDTO;
import com.diego.homebroker.api.v1.mapper.OrderMapper;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.service.OrderPersistenceService;
import com.diego.homebroker.service.OrderSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Order Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "/orders")
public class OrderController {

    private final OrderSearchService orderSearchService;
    private final OrderPersistenceService orderPersistenceService;

    public OrderController(OrderSearchService orderSearchService,
                           OrderPersistenceService orderPersistenceService) {
        this.orderSearchService = orderSearchService;
        this.orderPersistenceService = orderPersistenceService;
    }

    @ApiOperation(value = "Return all orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<OrderResponseListDTO> find (
            @ApiParam(value = "Company code for search")
            @RequestParam(value = "code", required = false) String code) {
        return ResponseEntity.ok(new OrderResponseListDTO(
                OrderMapper.entitiesToDTOs(orderSearchService.find(code))));
    }

    @ApiOperation(value = "Return order by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(path = "/{id}", produces="application/json")
    public ResponseEntity<OrderResponseDTO> findById (
            @ApiParam(value = "Company code for search")
            @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(OrderMapper.entityToDTO(orderSearchService.findById(id)));
    }

    @ApiOperation(value = "Create order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Register was created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<OrderResponseDTO> create (@RequestBody OrderCreateDTO orderCreateDTO) {
        Order order = orderPersistenceService.save(OrderMapper.createDtoToEntity(orderCreateDTO));
        return new ResponseEntity<>(OrderMapper.entityToDTO(order), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Create random orders")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Register was created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PostMapping(path = "/random", produces="application/json", consumes="application/json")
    public ResponseEntity<OrderResponseRandomDTO> create (
            @RequestBody OrderRandomCreateDTO orderDTO) {
        List<Order> orders = orderPersistenceService.createRandomOrders(orderDTO.getCpf(),
                orderDTO.getTotal(), orderDTO.getAmount());
        return new ResponseEntity<>(OrderMapper.entitiesListToDTO(orders, orderDTO.getTotal()),
                HttpStatus.CREATED);
    }
}
