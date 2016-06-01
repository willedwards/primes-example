package com.icap.primes.model;

import com.icap.primes.factory.PrimeServiceFactory;
import com.icap.primes.factory.PrimeType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.jayway.awaitility.Awaitility.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class PrimesModelTest {

    private static final Logger log = LoggerFactory.getLogger(PrimesModelTest.class);
    PrimesModel model;

    @Before
    public void before(){
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        model = new PrimesModel(service);
    }

    @Test
    @Ignore //unfinished.
    public void testSeekPrimes() throws Exception {

        model.seekPrimes();

        await().atMost(10, SECONDS);

        List<Long> primesSoFar =  model.getPrimesSoFar();

        log.info(Arrays.toString(primesSoFar.toArray()));
    }


}