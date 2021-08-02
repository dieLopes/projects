package com.criacao.methodfactory.person;

public class Man extends Person {

    public Man(String name) {
        this.name = name;
        System.out.println("Olá Senhor " + this.name);
    }
}
