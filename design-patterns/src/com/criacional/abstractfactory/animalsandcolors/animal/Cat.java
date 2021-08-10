package com.criacional.abstractfactory.animalsandcolors.animal;

public class Cat implements Animal {
    
    @Override
    public String getAnimal() {
        return "gato";
    }

    @Override
    public String sound() {
        return "miau";
    }
}
