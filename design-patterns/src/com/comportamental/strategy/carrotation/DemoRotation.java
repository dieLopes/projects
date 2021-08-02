package com.comportamental.strategy.carrotation;

import com.comportamental.strategy.carrotation.validation.DayFive;
import com.comportamental.strategy.carrotation.validation.DayFour;
import com.comportamental.strategy.carrotation.validation.DayOne;
import com.comportamental.strategy.carrotation.validation.DaySeven;
import com.comportamental.strategy.carrotation.validation.DaySix;
import com.comportamental.strategy.carrotation.validation.DayThree;
import com.comportamental.strategy.carrotation.validation.DayTwo;
import com.comportamental.strategy.carrotation.validation.Rotation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DemoRotation {

    /**
     * Verify cars rotation using the actual day and the last number of car plate
     * Day 1 - All cars
     * Day 2 - 0-1
     * Day 3 - 2-3
     * Day 4 - 4-5
     * Day 5 - 6-7
     * Day 6 - 8-9
     * Day 7 - All cars
     */

    private static final List<Car> cars = new ArrayList<>();
    static {
        cars.add(new Car("Corsa", "AAA2001", "Red"));
        cars.add(new Car("Celta",  "BBB5893", "Blue"));
        cars.add(new Car("Gol",  "CCC7439", "Yellow"));
        cars.add(new Car("HB20",  "DDD5646", "Green"));
    }

    private static final Map<Integer, Rotation> rotations = new HashMap<>();
    static {
        rotations.put(1, new DayOne());
        rotations.put(2, new DayTwo());
        rotations.put(3, new DayThree());
        rotations.put(4, new DayFour());
        rotations.put(5, new DayFive());
        rotations.put(6, new DaySix());
        rotations.put(7, new DaySeven());
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        cars.forEach(car -> System.out.println(car.getName() + " com a place " + car.getPlate() + ": " + rotationIfElse(day, car)));
        System.out.println("----------------------------------");
        cars.forEach(car -> System.out.println(car.getName() + " com a place " + car.getPlate() + ": " + rotationStrategy(day, car)));
    }

    private static boolean rotationStrategy (int day, Car car) {
        Rotation rotation = rotations.get(day);
        return rotation.validateRotation(car);
    }

    private static boolean rotationIfElse (int day, Car car) {
        int lastNumber = Integer.parseInt(car.getPlate().substring(car.getPlate().length() - 1));
        if (day > 7) {
            throw new IllegalArgumentException("O dia deve ser entre 0 e 7");
        }
        if (day == 1 || day == 7) {
            return true;
        } else if (day == 2 && (List.of(0, 1).contains(lastNumber))) {
            return true;
        } else if (day == 3 && (List.of(2, 3).contains(lastNumber))) {
            return true;
        } else if (day == 4 && (List.of(4, 5).contains(lastNumber))) {
            return true;
        } else if (day == 5 && (List.of(6, 7).contains(lastNumber))) {
            return true;
        }
        return day == 6 && (List.of(8, 9).contains(lastNumber));
    }
}
