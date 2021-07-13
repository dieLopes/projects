package com.devskiller.orders;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DuplicatedPairs {

    public long findDuplicatedPairs (String[] values) {
        return Arrays.stream(values)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .mapToLong(aLong -> aLong / 2).sum();
    }
}
