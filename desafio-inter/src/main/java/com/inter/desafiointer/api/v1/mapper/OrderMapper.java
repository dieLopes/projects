package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseRandomListDTO;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Order;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Order createDtoToEntity(OrderCreateDTO orderCreateDTO) {
        Order order = mapper.map(orderCreateDTO, Order.class);
        order.setId(null);
        order.setWallet(WalletBuilder.of()
                .id(orderCreateDTO.getWalletId())
                .build());
        return order;
    }

    public static OrderResponseDTO entityToDTO(Order order) {
        OrderResponseDTO orderResponseDTO = mapper.map(order, OrderResponseDTO.class);
        orderResponseDTO.setCompanyCode(order.getCompany().getCode());
        orderResponseDTO.setWalletId(order.getWallet().getId());
        return orderResponseDTO;
    }

    public static List<OrderResponseDTO> entitiesToDTOs (List<Order> orders) {
        return orders
            .stream()
            .map(OrderMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    public static OrderResponseRandomListDTO entitiesListToDTO(List<Order> orders, BigDecimal toOrders) {
        OrderResponseRandomListDTO orderResponseRandomListDTO = new OrderResponseRandomListDTO(orders.stream()
                .map(OrderMapper::entityToDTO)
                .collect(Collectors.toList()));
        BigDecimal total = orders
                .stream()
                .flatMap(orderLine -> Stream.of(orderLine.getTotalPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderResponseRandomListDTO.setTotal(total);
        orderResponseRandomListDTO.setChange(toOrders.subtract(total));
        return orderResponseRandomListDTO;
    }
}
