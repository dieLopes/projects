package com.criacional.abstractfactory.furnituretypes.factories;

import com.criacional.abstractfactory.furnituretypes.furniture.chair.Chair;
import com.criacional.abstractfactory.furnituretypes.furniture.chair.RusticChair;
import com.criacional.abstractfactory.furnituretypes.furniture.table.RusticTable;
import com.criacional.abstractfactory.furnituretypes.furniture.table.Table;

public class RusticFactory implements AbstractFactory {


    @Override
    public Chair createChair() {
        return new RusticChair();
    }

    @Override
    public Table createTable() {
        return new RusticTable();
    }
}