package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.walletstock.WalletStockResponseDTO;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.builder.WalletStockBuilder;
import com.inter.desafiointer.domain.WalletStock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class WalletStockMapperTest {

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        WalletStock stock = createWalletStock("some-id", "BIDI11");
        WalletStockResponseDTO walletStockResponseDTO = WalletStockMapper.entityToDTO(stock);
        assertThat(walletStockResponseDTO.getId()).isEqualTo(stock.getId());
        assertThat(walletStockResponseDTO.getAmount()).isEqualTo(stock.getAmount());
        assertThat(walletStockResponseDTO.getBalance()).isEqualTo(stock.getBalance());
        assertThat(walletStockResponseDTO.getAveragePrice()).isEqualTo(stock.getBalance()
                .divide(new BigDecimal(stock.getAmount()), 2, RoundingMode.HALF_UP));
        assertThat(walletStockResponseDTO.getCompanyCode()).isEqualTo(stock.getCompanyCode());

    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<WalletStock> stocks = List.of(
                createWalletStock("some-id", "BIDI11"),
                createWalletStock("another-id", "SOME4"));
        List<WalletStockResponseDTO> walletStockResponseDTOS = WalletStockMapper.entitiesToDTOs(stocks);
        assertThat(walletStockResponseDTOS).hasSize(2)
                .extracting(WalletStockResponseDTO::getId,
                        WalletStockResponseDTO::getAmount,
                        WalletStockResponseDTO::getBalance,
                        WalletStockResponseDTO::getCompanyCode)
                .containsExactlyInAnyOrder(
                        tuple("some-id", 5, new BigDecimal(10), "BIDI11"),
                        tuple("another-id", 5, new BigDecimal(10), "SOME4")
                );
    }

    private WalletStock createWalletStock (String id, String companyCode) {
        return WalletStockBuilder.of()
                .id(id)
                .amount(5)
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .balance(BigDecimal.TEN)
                .companyCode(companyCode)
                .build();
    }
}
