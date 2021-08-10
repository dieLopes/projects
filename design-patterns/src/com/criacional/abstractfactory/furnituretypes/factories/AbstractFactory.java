package com.criacional.abstractfactory.furnituretypes.factories;

import com.criacional.abstractfactory.furnituretypes.furniture.chair.Chair;
import com.criacional.abstractfactory.furnituretypes.furniture.table.Table;

public interface AbstractFactory {

    Chair createChair();
    Table createTable();
}
