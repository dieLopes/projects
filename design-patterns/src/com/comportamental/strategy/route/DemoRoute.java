package com.comportamental.strategy.route;

import com.comportamental.strategy.route.strategies.RouteBike;
import com.comportamental.strategy.route.strategies.RouteCar;
import com.comportamental.strategy.route.strategies.RouteWalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DemoRoute {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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

        if (transport == 1) {
            System.out.println(new RouteCar().buildRoute(pointA, pointB));
        } else if (transport == 2) {
            System.out.println(new RouteBike().buildRoute(pointA, pointB));
        } else {
            System.out.println(new RouteWalk().buildRoute(pointA, pointB));
        }
    }
}