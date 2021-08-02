package com.comportamental.strategy.route.strategies;

public class RouteBike implements RouteStrategy {

    @Override
    public String buildRoute(String pointA, String pointB) {
        return "Seu caminho de bicicleta do ponto " + pointA + " ao ponto " + pointB + " vai levar 10h e 30m";
    }
}
