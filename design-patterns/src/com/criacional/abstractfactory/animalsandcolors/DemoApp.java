package com.criacional.abstractfactory.animalsandcolors;

import com.criacional.abstractfactory.animalsandcolors.animal.Animal;
import com.criacional.abstractfactory.animalsandcolors.color.Color;
import com.criacional.abstractfactory.animalsandcolors.factory.AbstractFactory;
import com.criacional.abstractfactory.animalsandcolors.factory.FactoryProvider;
import com.criacional.abstractfactory.animalsandcolors.type.AnimalType;

public class DemoApp {

    public static void main(String[] args) {
        AbstractFactory abstractFactory = FactoryProvider.getFactory("animal");
        Animal animal = (Animal) abstractFactory.create("cat");

        abstractFactory = FactoryProvider.getFactory("type");
        AnimalType animalType = (AnimalType) abstractFactory.create("wild");

        abstractFactory = FactoryProvider.getFactory("color");
        Color color = (Color) abstractFactory.create("brown");

        System.out.printf("Um %s %s %s faz %s", animal.getAnimal(), animalType.getType(), color.getColor(), animal.sound());
    }
}
