package com.icap.primes.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by will on 01/06/2016.
 */
public class ForkPrimeTest {

    private static final Logger log = LoggerFactory.getLogger(ForkPrimeTest.class);

    @Test
    public void testEvenlySplitSeedPrimes() throws Exception {
        Long[] seedPrimes = new Long[]{ 3L,5L,7L, 11L, 13L };
        ForkPrime forkPrime = new ForkPrime(67,seedPrimes);
        List<Long[]> splitSeeds = forkPrime.evenlySplitSeedPrimes(seedPrimes);

        log.info(ForkPrime.toString(splitSeeds.get(0)));
        log.info(ForkPrime.toString(splitSeeds.get(1)));
    }
}