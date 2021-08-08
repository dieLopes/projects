package com.comportamental.strategy.carrotation;

import com.comportamental.strategy.carrotation.validation.ColorRotationStrategy;
import com.comportamental.strategy.carrotation.validation.DayRotationStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DemoRotation {

    private static final List<Car> cars = new ArrayList<>();
    static {
        cars.add(new Car("Corsa", "AAA2001", "Red"));
        cars.add(new Car("Celta",  "BBB5893", "Blue"));
        cars.add(new Car("Gol",  "CCC7439", "Yellow"));
        cars.add(new Car("HB20",  "DDD5646", "Green"));
    }

    public static void main(String[] args) {
        cars.forEach(car -> System.out.println(car.getName() + " com a place " + car.getPlate() + ": " + rotationIfElse(car)));
        System.out.println("----------------------------------");
        cars.forEach(car -> System.out.println(car.getName() + " com a place " + car.getPlate() + ": " + rotationStrategy(car)));
    }

    private static boolean rotationStrategy (Car car) {
        boolean plate = new DayRotationStrategy().validateRotation(car);
        boolean color = new ColorRotationStrategy().validateRotation(car);
        return plate && color;
    }

    private static boolean rotationIfElse (Car car) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int lastNumber = Integer.parseInt(car.getPlate().substring(car.getPlate().length() - 1));
        return validationDay(day, lastNumber) && validationColor(day, car.getColor());
    }

    private static boolean validationDay (Integer day, int carPlate) {
        if (day > 7) {
            throw new IllegalArgumentException("O dia deve ser entre 0 e 7");
        }
        if (day == 1 || day == 7) {
            return true;
        } else if (day == 2 && (List.of(0, 1).contains(carPlate))) {
            return true;
        } else if (day == 3 && (List.of(2, 3).contains(carPlate))) {
            return true;
        } else if (day == 4 && (List.of(4, 5).contains(carPlate))) {
            return true;
        } else if (day == 5 && (List.of(6, 7).contains(carPlate))) {
            return true;
        }
        return day == 6 && (List.of(8, 9).contains(carPlate));
    }

    private static boolean validationColor (Integer day, String color) {
        if (day == 1 && "Blue".equalsIgnoreCase(color)) {
            return true;
        } else if (day == 2 && "Green".equalsIgnoreCase(color)) {
            return true;
        } else if (day == 3 && "Purple".equalsIgnoreCase(color)) {
            return true;
        } else if (day == 4 && "Brown".equalsIgnoreCase(color)) {
            return true;
        } else if (day == 5 && "White".equalsIgnoreCase(color)) {
            return true;
        } else if (day == 6 && "Yellow".equalsIgnoreCase(color)) {
            return true;
        }
        return day == 7 && "Red".equalsIgnoreCase(color);
    }
}
