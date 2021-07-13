package com.devskiller.orders;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DuplicatedPairsTest {

    private DuplicatedPairs duplicatedPairs = new DuplicatedPairs();

    @Test
    public void shouldReturnAmountOfDuplicatedPairs() {
        assertThat(duplicatedPairs.findDuplicatedPairs(new String[]{"10","10","20","30","30","10","20","10","40", "20","20"}))
                .isEqualTo(5);
        assertThat(duplicatedPairs.findDuplicatedPairs(new String[]{"10","20","30","30","10","20","10","40", "20","20"}))
                .isEqualTo(4);
        assertThat(duplicatedPairs.findDuplicatedPairs(new String[]{"10","30","10","20","10","40", "20","20"}))
                .isEqualTo(2);
    }
}
