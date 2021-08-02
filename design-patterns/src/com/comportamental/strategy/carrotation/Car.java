package com.comportamental.strategy.carrotation;

public class Car {

    private String name;
    private String plate;
    private String color;

    public Car (String name, String plate, String color) {
        this.name = name;
        this.plate = plate;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
