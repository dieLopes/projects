package com.comportamental.strategy.carrotation.validation;

import com.comportamental.strategy.carrotation.Car;

public class DaySeven implements Rotation {

    @Override
    public boolean validateRotation(Car car) {
        return true;
    }
}
