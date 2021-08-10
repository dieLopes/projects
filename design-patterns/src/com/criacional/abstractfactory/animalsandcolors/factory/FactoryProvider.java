package com.criacional.abstractfactory.animalsandcolors.factory;

public class FactoryProvider {

    public static AbstractFactory getFactory(String type) {
        if ("animal".equalsIgnoreCase(type)) {
            return new AnimalFactory();
        } else if ("color".equalsIgnoreCase(type)) {
            return new ColorFactory();
        } else if ("type".equalsIgnoreCase(type)) {
            return new AnimalTypeFactory();
        }
        throw new IllegalArgumentException(String.format("Factory %s not available", type));
    }
}
