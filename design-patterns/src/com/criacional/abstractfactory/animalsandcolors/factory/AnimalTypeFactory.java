package com.criacional.abstractfactory.animalsandcolors.factory;

import com.criacional.abstractfactory.animalsandcolors.type.AnimalType;
import com.criacional.abstractfactory.animalsandcolors.type.Domestic;
import com.criacional.abstractfactory.animalsandcolors.type.Wild;

public class AnimalTypeFactory implements AbstractFactory<AnimalType> {

    @Override
    public AnimalType create(String type) {
        if ("domestic".equalsIgnoreCase(type)) {
            return new Domestic();
        } else if ("wild".equalsIgnoreCase(type)) {
            return new Wild();
        }
        throw new IllegalArgumentException("Available Animal Types: Domestic and Wild");
    }
}
