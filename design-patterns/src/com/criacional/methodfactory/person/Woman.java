package com.criacional.methodfactory.person;

public class Woman extends Person {

    public Woman(String name) {
        this.name = name;
        System.out.println("Olá Senhora " + this.name);
    }
}
