package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Order;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Order createDtoToEntity(OrderCreateDTO orderCreateDTO) {
        Order order = mapper.map(orderCreateDTO, Order.class);
        order.setWallet(WalletBuilder.of()
                .id(orderCreateDTO.getWalletId())
                .build());
        return order;
    }

    public static OrderResponseDTO entityToDTO(Order order) {
        return mapper.map(order, OrderResponseDTO.class);
    }

    public static List<OrderResponseDTO> entitiesToDTOs (List<Order> orders) {
        return orders
            .stream()
            .map(OrderMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
