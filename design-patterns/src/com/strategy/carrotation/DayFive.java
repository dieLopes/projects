package com.strategy.carrotation;

import java.util.List;

public class DayFive implements Rotation {

    @Override
    public boolean validateRotation(Integer lastNumber) {
        return List.of(6, 7).contains(lastNumber);
    }
}
