package com.diego.homebroker.service;

import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.repository.OrderRepository;
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

    public Order findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }
}