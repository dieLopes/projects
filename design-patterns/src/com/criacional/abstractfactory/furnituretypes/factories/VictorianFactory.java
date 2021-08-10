package com.criacional.abstractfactory.furnituretypes.factories;

import com.criacional.abstractfactory.furnituretypes.furniture.chair.Chair;
import com.criacional.abstractfactory.furnituretypes.furniture.chair.VictorianChair;
import com.criacional.abstractfactory.furnituretypes.furniture.table.Table;
import com.criacional.abstractfactory.furnituretypes.furniture.table.VictorianTable;

public class VictorianFactory implements AbstractFactory {

    @Override
    public Chair createChair() {
        return new VictorianChair();
    }

    @Override
    public Table createTable() {
        return new VictorianTable();
    }
}
