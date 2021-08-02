package com.comportamental.strategy.carrotation.validation;

import com.comportamental.strategy.carrotation.Car;

public class DayOne implements Rotation {

    @Override
    public boolean validateRotation(Car car) {
        return true;
    }
}
