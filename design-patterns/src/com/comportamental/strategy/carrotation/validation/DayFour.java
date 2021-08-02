package com.comportamental.strategy.carrotation.validation;

import com.comportamental.strategy.carrotation.Car;

import java.util.List;

public class DayFour implements Rotation {

    @Override
    public boolean validateRotation(Car car) {
        int plate = Integer.parseInt(car.getPlate().substring(car.getPlate().length() - 1));
        return List.of(4, 5).contains(plate);
    }
}
