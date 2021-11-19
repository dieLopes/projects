package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.walletstock.WalletStockResponseDTO;
import com.inter.desafiointer.domain.WalletStock;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class WalletStockMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static WalletStockResponseDTO entityToDTO(WalletStock walletStock) {
        WalletStockResponseDTO walletStockResponseDTO =  mapper.map(walletStock, WalletStockResponseDTO.class);
        walletStockResponseDTO.setAveragePrice(walletStockResponseDTO.getBalance()
                .divide(new BigDecimal(walletStockResponseDTO.getAmount()), 2, RoundingMode.HALF_UP));
        return walletStockResponseDTO;
    }

    public static List<WalletStockResponseDTO> entitiesToDTOs (List<WalletStock> orders) {
        return orders
                .stream()
                .map(WalletStockMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
