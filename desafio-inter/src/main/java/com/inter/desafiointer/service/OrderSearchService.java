package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSearchService {

    private final OrderRepository orderRepository;

    public OrderSearchService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> find(String code) {
        return orderRepository.find(code);
    }
}