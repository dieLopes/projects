package com.criacional.abstractfactory.animalsandcolors.factory;

import com.criacional.abstractfactory.animalsandcolors.animal.Animal;
import com.criacional.abstractfactory.animalsandcolors.animal.Cat;
import com.criacional.abstractfactory.animalsandcolors.animal.Dog;
import com.criacional.abstractfactory.animalsandcolors.animal.Duck;

public class AnimalFactory implements AbstractFactory<Animal> {

    @Override
    public Animal create(String type) {
        if ("dog".equalsIgnoreCase(type)) {
            return new Dog();
        } else if ("cat".equalsIgnoreCase(type)) {
            return new Cat();
        } else if ("duck".equalsIgnoreCase(type)) {
            return new Duck();
        }
        throw new IllegalArgumentException("Available Animals: Dog, Cat or Duck");
    }
}
