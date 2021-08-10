package com.criacional.methodfactory.person;

public class PersonFactory {

    public Person getPerson(String name, String gender) {
        if ("M".equalsIgnoreCase(gender)) {
            return new Man(name);
        } else if ("F".equalsIgnoreCase(gender)) {
            return new Woman(name);
        }
        throw new IllegalArgumentException("O genero deve ser M ou F");
    }
}
