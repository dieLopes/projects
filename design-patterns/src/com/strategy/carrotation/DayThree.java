package com.strategy.carrotation;

import java.util.List;

public class DayThree implements Rotation {

    @Override
    public boolean validateRotation(Integer lastNumber) {
        return List.of(2, 3).contains(lastNumber);
    }
}
