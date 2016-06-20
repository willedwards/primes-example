package com.icap.primes.factory;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by will on 20/06/2016.
 */
public class FunctionalPrimeTest {


    private FunctionalPrime functionalPrime = new FunctionalPrime();
    private List<Long> expectedFirst20 = Arrays.asList(2L,
                                                        3L,
                                                        5L,
                                                        7L,
                                                        11L,
                                                        13L,
                                                        17L,
                                                        19L,
                                                        23L,
                                                        29L,
                                                        31L,
                                                        37L,
                                                        41L,
                                                        43L,
                                                        47L,
                                                        53L,
                                                        59L,
                                                        61L,
                                                        67L,
                                                        71L

                                                );
    @Test
    public void firstTwenty() {
        List<Long> foundPrimes = functionalPrime.getAllPrimes(20);
        foundPrimes.stream().forEach( p -> assertTrue(expectedFirst20.contains(p)));
    }
}