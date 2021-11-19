package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.dto.company.CompanyResponseListDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseListDTO;
import com.inter.desafiointer.api.v1.mapper.CompanyMapper;
import com.inter.desafiointer.api.v1.mapper.OrderMapper;
import com.inter.desafiointer.service.WalletPersistenceService;
import com.inter.desafiointer.service.WalletSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Wallet Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "/wallets")
public class WalletController {

    private final WalletSearchService walletSearchService;

    public WalletController(WalletSearchService walletSearchService) {
        this.walletSearchService = walletSearchService;
    }

    @ApiOperation(value = "Return all wallet orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request responses OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(path = "/{id}/orders", produces="application/json")
    public ResponseEntity<OrderResponseListDTO> findOrders (
            @ApiParam(value = "Wallet id", required = true)
            @NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(new OrderResponseListDTO(
                OrderMapper.entitiesToDTOs(walletSearchService.findOrders(id))));
    }
}
