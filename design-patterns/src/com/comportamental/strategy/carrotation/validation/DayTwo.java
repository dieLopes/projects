package com.comportamental.strategy.carrotation.validation;

import com.comportamental.strategy.carrotation.Car;

import java.util.List;

public class DayTwo implements Rotation {

    @Override
    public boolean validateRotation(Car car) {
        int plate = Integer.parseInt(car.getPlate().substring(car.getPlate().length() - 1));
        return List.of(0, 1).contains(plate);
    }
}
