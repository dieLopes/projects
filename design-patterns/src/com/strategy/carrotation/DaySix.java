package com.strategy.carrotation;

import java.util.List;

public class DaySix implements Rotation {

    @Override
    public boolean validateRotation(Integer lastNumber) {
        return List.of(8, 9).contains(lastNumber);
    }
}
