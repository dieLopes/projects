package com.criacional.abstractfactory.furnituretypes.factories;

import com.criacional.abstractfactory.furnituretypes.furniture.chair.Chair;
import com.criacional.abstractfactory.furnituretypes.furniture.chair.ModernChair;
import com.criacional.abstractfactory.furnituretypes.furniture.table.ModernTable;
import com.criacional.abstractfactory.furnituretypes.furniture.table.Table;

public class ModernFactory implements AbstractFactory {


    @Override
    public Chair createChair() {
        return new ModernChair();
    }

    @Override
    public Table createTable() {
        return new ModernTable();
    }
}
