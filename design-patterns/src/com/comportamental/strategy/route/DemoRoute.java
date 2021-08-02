package com.comportamental.strategy.route;

import com.comportamental.strategy.route.strategies.RouteBike;
import com.comportamental.strategy.route.strategies.RouteCar;
import com.comportamental.strategy.route.strategies.RouteStrategy;
import com.comportamental.strategy.route.strategies.RouteWalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DemoRoute {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final Map<Integer, RouteStrategy> routes = new HashMap<>();
    static {
        routes.put(1, new RouteCar());
        routes.put(2, new RouteBike());
        routes.put(3, new RouteWalk());
    }

    public static void main(String[] args) throws IOException {

        System.out.print("Digite o endereço de saída:" + "\n");
        String pointA = reader.readLine();
        System.out.print("Digite o endereço de chegada:" + "\n");
        String pointB = reader.readLine();
        System.out.print("Selecione o meio de transporte:" + "\n" +
                "1 - Carro" + "\n" +
                "2 - Bicicleta" + "\n" +
                "3 - Caminhando" + "\n");
        int transport =  Integer.parseInt(reader.readLine());
        RouteStrategy strategy = Optional.ofNullable(routes.get(transport))
                .orElseThrow(() -> new IllegalArgumentException("Transporte não cadastrado"));
        buildRoute(strategy, pointA, pointB);
    }

    private static void buildRoute (RouteStrategy routeStrategy, String pointA, String pointB) {
        System.out.println(routeStrategy.buildRoute(pointA, pointB));
    }
}