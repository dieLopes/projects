package com.criacional.abstractfactory.animalsandcolors.factory;

public interface AbstractFactory<T> {

    T create(String type);
}
