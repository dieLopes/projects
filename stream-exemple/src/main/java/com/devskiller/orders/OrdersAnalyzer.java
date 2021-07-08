package com.devskiller.orders;

import com.devskiller.orders.model.Customer;
import com.devskiller.orders.model.Order;
import com.devskiller.orders.model.OrderLine;
import com.devskiller.orders.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrdersAnalyzer {

    /**
     * Should return at most three most popular products. Most popular product is the product that have the most occurrences
     * in given orders (ignoring product quantity).
     * If two products have the same popularity, then products should be ordered by name
     *
     * @param orders orders stream
     * @return list with up to three most popular products
     */
    public List<Product> findThreeMostPopularProducts(Stream<Order> orders) {
        Map<Product, Long> result = orders
                .flatMap(order -> order.getOrderLines().stream())
                .flatMap(orderLine -> Stream.of(orderLine.getProduct()))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return result
                .keySet()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Should return the most valuable customer, that is the customer that has the highest value of all placed orders.
     * If two customers have the same orders value, then any of them should be returned.
     *
     * @param orders orders stream
     * @return Optional of most valuable customer
     */
    public Optional<Customer> findMostValuableCustomer(Stream<Order> orders) {
        Map<Customer, BigDecimal> result = new HashMap<>();
        orders.forEach(order -> {
            BigDecimal totalLinesForOrder = order.getOrderLines()
                    .stream()
                    .flatMap(orderLine -> Stream.of(orderLine.getProduct().getPrice()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (result.containsKey(order.getCustomer())) {
                result.put(order.getCustomer(), totalLinesForOrder.add(result.get(order.getCustomer())));
            } else {
                result.put(order.getCustomer(), totalLinesForOrder);
            }
        });
        return Optional.of(result.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey());
    }
}