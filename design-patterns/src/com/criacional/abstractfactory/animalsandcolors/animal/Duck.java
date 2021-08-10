package com.criacional.abstractfactory.animalsandcolors.animal;

public class Duck implements Animal {

    @Override
    public String getAnimal() {
        return "pato";
    }

    @Override
    public String sound() {
        return "quack";
    }
}
