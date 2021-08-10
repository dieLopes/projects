package com.criacional.abstractfactory.furnituretypes;

import com.criacional.abstractfactory.furnituretypes.factories.AbstractFactory;
import com.criacional.abstractfactory.furnituretypes.factories.ModernFactory;
import com.criacional.abstractfactory.furnituretypes.factories.RusticFactory;
import com.criacional.abstractfactory.furnituretypes.factories.VictorianFactory;

public class FurnitureApp {

    public static void main(String[] args) {

        AbstractFactory modernFactory = new ModernFactory();
        System.out.printf("Uma %s com uma %s \n", modernFactory.createTable().getName(),
                modernFactory.createChair().getName());

        AbstractFactory rusticFactory = new RusticFactory();
        System.out.printf("Uma %s com uma %s \n", rusticFactory.createTable().getName(),
                rusticFactory.createChair().getName());

        AbstractFactory victorianFactory = new VictorianFactory();
        System.out.printf("Uma %s com uma %s \n", victorianFactory.createTable().getName(),
                victorianFactory.createChair().getName());
    }
}
