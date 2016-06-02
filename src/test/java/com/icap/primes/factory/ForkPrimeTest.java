package com.icap.primes.factory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;


public class ForkPrimeTest {

    private static final Logger log = LoggerFactory.getLogger(ForkPrimeTest.class);

    @Test
    public void testEvenlySplitSeedPrimes() throws Exception {
        Long[] seedPrimes = new Long[]{ 3L,5L,7L, 11L, 13L };
        ForkPrime forkPrime = new ForkPrime(67,seedPrimes);
        List<Long[]> splitSeeds = forkPrime.evenlySplitSeedPrimes(seedPrimes);

        Long[] topHalf = splitSeeds.get(0);
        Long[] bottomHalf = splitSeeds.get(1);

        List<Long> bottomHalfList = Arrays.asList(bottomHalf);

        for(Long current :topHalf){
            assertFalse(bottomHalfList.contains(current));
        }

        log.info(ForkPrime.toString(topHalf));
        log.info(ForkPrime.toString(bottomHalf));


    }
}