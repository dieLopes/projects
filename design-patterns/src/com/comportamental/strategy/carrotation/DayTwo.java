package com.comportamental.strategy.carrotation;

import java.util.List;

public class DayTwo implements Rotation {

    @Override
    public boolean validateRotation(Integer lastNumber) {
        return List.of(0, 1).contains(lastNumber);
    }
}
