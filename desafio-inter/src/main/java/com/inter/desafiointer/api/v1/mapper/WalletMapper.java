package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import com.inter.desafiointer.domain.Wallet;
import org.modelmapper.ModelMapper;

public class WalletMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static WalletResponseDTO entityToDTO(Wallet wallet) {
        return mapper.map(wallet, WalletResponseDTO.class);
    }
}
