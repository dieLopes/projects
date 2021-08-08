package com.comportamental.strategy.carrotation.validation;

import com.comportamental.strategy.carrotation.Car;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class DayRotationStrategy implements RotationStrategy {

    private static final Map<Integer, List<Integer>> rotations = new HashMap<>();
    static {
        rotations.put(2, List.of(1,2));
        rotations.put(3, List.of(3,4));
        rotations.put(4, List.of(5,6));
        rotations.put(5, List.of(7,8));
        rotations.put(6, List.of(9,0));
    }

    @Override
    public boolean validateRotation(Car car) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int lastNumber = Integer.parseInt(car.getPlate().substring(car.getPlate().length() - 1));
        if (rotations.containsKey(day)) {
            return rotations.get(day).contains(lastNumber);
        }
        return true;
    }
}