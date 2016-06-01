package com.icap.service.primes.model;

import com.icap.service.primes.tools.MathsFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by will on 25/05/2016.
 */
public class PrimesModel {

    private static final Logger log = LoggerFactory.getLogger(PrimesModel.class);
    private List<Long> existingPrimes = new ArrayList<Long>();

    private static final long[] seedPrimes1 = new long[]{ 2,  3,  5,  7, 11, 13, 17, 19, 23};
    private static final long[] seedPrimes2 = new long[]{29, 31, 37, 41, 43, 47, 53, 59, 61};

    private volatile long candidateUnderTest;
    private volatile long maxFactorToTry;

    public PrimesModel(long candidateUnderTest){
        for(long prime : seedPrimes1) {
            existingPrimes.add(prime);
        }

        for(long prime : seedPrimes2) {
            existingPrimes.add(prime);
        }

        this.candidateUnderTest = candidateUnderTest;
        maxFactorToTry = MathsFunctions.floorSqrt(candidateUnderTest);

        ForkPrime.factorFound = false; //reset;
    }

    public Boolean hasFactors() throws InterruptedException {

        ForkJoinPool pool = new ForkJoinPool(2);
        ForkPrime forkPrimes1 = new ForkPrime(candidateUnderTest,seedPrimes1);
        ForkPrime forkPrimes2 = new ForkPrime(candidateUnderTest,seedPrimes2);


       ForkJoinTask<Boolean> isPrime =  pool.submit(forkPrimes1);
       ForkJoinTask<Boolean> isPrime2 =  pool.submit(forkPrimes2);

        pool.awaitQuiescence(20, TimeUnit.SECONDS);

        log.info(candidateUnderTest + " is " + (ForkPrime.factorFound ? " not ":"") + " prime" );

        return ForkPrime.factorFound;

    }
}
