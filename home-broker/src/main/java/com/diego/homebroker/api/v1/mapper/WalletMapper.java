package com.diego.homebroker.api.v1.mapper;

import com.diego.homebroker.api.v1.dto.wallet.WalletResponseDTO;
import com.diego.homebroker.domain.Wallet;
import org.modelmapper.ModelMapper;

public class WalletMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static WalletResponseDTO entityToDTO(Wallet wallet) {
        return mapper.map(wallet, WalletResponseDTO.class);
    }
}
