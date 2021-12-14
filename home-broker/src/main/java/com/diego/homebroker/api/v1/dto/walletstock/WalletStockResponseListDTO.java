package com.diego.homebroker.api.v1.dto.walletstock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "WalletStockResponseList")
public class WalletStockResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of stocks")
    private List<WalletStockResponseDTO> stocks;

    public WalletStockResponseListDTO() { }

    public WalletStockResponseListDTO(List<WalletStockResponseDTO> stocks) {
        this.stocks = stocks;
    }

    public List<WalletStockResponseDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<WalletStockResponseDTO> stocks) {
        this.stocks = stocks;
    }
}
