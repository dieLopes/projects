package com.comportamental.strategy.route.strategies;

public class RouteWalk implements RouteStrategy {

    @Override
    public String buildRoute(String pointA, String pointB) {
        return "Seu caminho a pé do ponto " + pointA + " ao ponto " + pointB + " vai levar 23h e 30m";
    }
}
