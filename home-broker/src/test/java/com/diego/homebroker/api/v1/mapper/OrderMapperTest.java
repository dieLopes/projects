package com.diego.homebroker.api.v1.mapper;

import com.diego.homebroker.api.v1.dto.order.OrderCreateDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseRandomDTO;
import com.diego.homebroker.builder.CompanyBuilder;
import com.diego.homebroker.builder.OrderBuilder;
import com.diego.homebroker.builder.UserBuilder;
import com.diego.homebroker.builder.WalletBuilder;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.domain.OrderStatus;
import com.diego.homebroker.domain.OrderType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.diego.homebroker.domain.OrderStatus.OK;
import static com.diego.homebroker.domain.OrderStatus.PENDING;
import static com.diego.homebroker.domain.OrderType.BUY;
import static com.diego.homebroker.domain.OrderType.SELL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class OrderMapperTest {

    @Test
    public void whenConvertCreateDTOToEntityThenReturn () {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .amount(5)
                .type(BUY.toString())
                .code("SOME4")
                .cpf("11111111111")
                .build();
        Order order = OrderMapper.createDtoToEntity(orderCreateDTO);
        assertThat(order.getId()).isNullOrEmpty();
        assertThat(order.getAmount()).isEqualTo(5);
        assertThat(order.getType()).isEqualTo(BUY);
        assertThat(order.getCode()).isEqualTo("SOME4");
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        Order order = createOrder("some-id", PENDING, BUY);
        OrderResponseDTO orderResponseDTO = OrderMapper.entityToDTO(order);
        assertThat(orderResponseDTO.getId()).isEqualTo(order.getId());
        assertThat(orderResponseDTO.getAmount()).isEqualTo(order.getAmount());
        assertThat(orderResponseDTO.getCompanyCode()).isEqualTo(order.getCompany().getCode());
        assertThat(orderResponseDTO.getCpf()).isEqualTo("11111111111");
        assertThat(orderResponseDTO.getStatus()).isEqualTo(order.getStatus().toString());
        assertThat(orderResponseDTO.getDate()).isEqualTo(order.getDate());
        assertThat(orderResponseDTO.getType()).isEqualTo(order.getType().toString());
        assertThat(orderResponseDTO.getUnitPrice()).isEqualTo(order.getUnitPrice());
        assertThat(orderResponseDTO.getTotalPrice()).isEqualTo(order.getTotalPrice());
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<Order> orders = List.of(
                createOrder("some-id", PENDING, BUY),
                createOrder("another-id", OK, SELL));
        List<OrderResponseDTO> orderResponseDTOS = OrderMapper.entitiesToDTOs(orders);
        assertThat(orderResponseDTOS).hasSize(2)
                .extracting(OrderResponseDTO::getId,
                        OrderResponseDTO::getStatus,
                        OrderResponseDTO::getType)
                .containsExactlyInAnyOrder(
                        tuple("some-id", PENDING.toString(), BUY.toString()),
                        tuple("another-id", OK.toString(), SELL.toString())
                );
    }

    @Test
    public void whenConvertEntitiesToDTOThenReturn () {
        List<Order> orders = List.of(
                createOrder("some-id", OK, BUY),
                createOrder("another-id", OK, BUY));
        OrderResponseRandomDTO responseDTO = OrderMapper.entitiesListToDTO(orders, new BigDecimal(101));
        assertThat(responseDTO.getOrders().size()).isEqualTo(2);
        assertThat(responseDTO.getTotal()).isEqualTo(new BigDecimal(100));
        assertThat(responseDTO.getChange()).isEqualTo(new BigDecimal(1));
    }

    private Order createOrder (String id, OrderStatus status, OrderType type) {
        return OrderBuilder.of()
                .id(id)
                .amount(5)
                .company(CompanyBuilder.of()
                        .code("SOME4")
                        .build())
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .user(UserBuilder.of()
                                .cpf("11111111111")
                                .build())
                        .build())
                .status(status)
                .date(LocalDateTime.now())
                .type(type)
                .unitPrice(new BigDecimal(10))
                .totalPrice(new BigDecimal(50))
                .build();
    }
}
