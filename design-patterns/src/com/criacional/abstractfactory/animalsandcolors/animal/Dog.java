package com.criacional.abstractfactory.animalsandcolors.animal;

public class Dog implements Animal {

    @Override
    public String getAnimal() {
        return "cachorro";
    }

    @Override
    public String sound() {
        return "au au";
    }
}
