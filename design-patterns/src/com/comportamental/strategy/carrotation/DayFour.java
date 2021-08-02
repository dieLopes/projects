package com.comportamental.strategy.carrotation;

import java.util.List;

public class DayFour implements Rotation {

    @Override
    public boolean validateRotation(Integer lastNumber) {
        return List.of(4, 5).contains(lastNumber);
    }
}
