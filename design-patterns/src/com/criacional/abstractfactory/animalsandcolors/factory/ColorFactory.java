package com.criacional.abstractfactory.animalsandcolors.factory;

import com.criacional.abstractfactory.animalsandcolors.color.Black;
import com.criacional.abstractfactory.animalsandcolors.color.Brown;
import com.criacional.abstractfactory.animalsandcolors.color.Color;
import com.criacional.abstractfactory.animalsandcolors.color.White;

public class ColorFactory implements AbstractFactory<Color> {

    @Override
    public Color create(String type) {
        if ("white".equalsIgnoreCase(type)) {
            return new White();
        } else if ("black".equalsIgnoreCase(type)) {
            return new Black();
        } else if ("brown".equalsIgnoreCase(type)) {
            return new Brown();
        }
        throw new IllegalArgumentException("Available Colors: White, Black or Brown");
    }
}
