package com.comportamental.strategy.carrotation.validation;

import com.comportamental.strategy.carrotation.Car;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Verify cars rotation using the actual day and the car color
 * Day 1 - Blue
 * Day 2 - Green
 * Day 3 - Purple
 * Day 4 - Brown
 * Day 5 - White
 * Day 6 - Yellow
 * Day 7 - Red
 */
public class ColorRotationStrategy implements RotationStrategy {

    private static final Map<Integer, String> rotations = new HashMap<>();
    static {
        rotations.put(1, "Blue");
        rotations.put(2, "Green");
        rotations.put(3, "Purple");
        rotations.put(4, "Brown,");
        rotations.put(5, "White");
        rotations.put(6, "Yellow");
        rotations.put(7, "Red");
    }

    @Override
    public boolean validateRotation(Car car) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (rotations.containsKey(day)) {
            return rotations.get(day).equalsIgnoreCase(car.getColor());
        }
        return true;
    }
}
