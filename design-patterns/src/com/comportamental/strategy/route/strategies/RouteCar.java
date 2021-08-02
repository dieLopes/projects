package com.comportamental.strategy.route.strategies;

public class RouteCar implements RouteStrategy {

    @Override
    public String buildRoute(String pointA, String pointB) {
        return "Seu caminho de carro do ponto " + pointA + " ao ponto " + pointB + " vai levar 2h e 30m";
    }
}
