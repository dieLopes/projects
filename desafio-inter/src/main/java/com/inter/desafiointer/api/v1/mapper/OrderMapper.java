package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseRandomDTO;
import com.inter.desafiointer.domain.Order;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Order createDtoToEntity(OrderCreateDTO orderCreateDTO) {
        return mapper.map(orderCreateDTO, Order.class);
    }

    public static OrderResponseDTO entityToDTO(Order order) {
        OrderResponseDTO orderResponseDTO = mapper.map(order, OrderResponseDTO.class);
        orderResponseDTO.setCompanyCode(order.getCompany().getCode());
        orderResponseDTO.setCpf(order.getWallet().getUser().getCpf());
        return orderResponseDTO;
    }

    public static List<OrderResponseDTO> entitiesToDTOs (List<Order> orders) {
        return orders
            .stream()
            .map(OrderMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    public static OrderResponseRandomDTO entitiesListToDTO(List<Order> orders, BigDecimal toOrders) {
        OrderResponseRandomDTO orderResponseRandomDTO = new OrderResponseRandomDTO(orders.stream()
                .map(OrderMapper::entityToDTO)
                .collect(Collectors.toList()));
        BigDecimal total = orders
                .stream()
                .flatMap(orderLine -> Stream.of(orderLine.getTotalPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderResponseRandomDTO.setTotal(total);
        orderResponseRandomDTO.setChange(toOrders.subtract(total));
        return orderResponseRandomDTO;
    }
}
