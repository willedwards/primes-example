package com.icap.primes.model;

import com.google.common.collect.ImmutableList;
import com.icap.service.primes.model.primes.tools.MathsFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Created by will on 25/05/2016.
 */
public class PrimesService implements IPrimeService {

    private static final Logger log = LoggerFactory.getLogger(PrimesService.class);
    private List<Long> existingPrimes = new ArrayList<Long>();
    private static final int NUM_PROCESSORS = 2;//Runtime.getRuntime().availableProcessors();

    private static final Long[] seedPrimes = new Long[]{3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L};

    private volatile long candidateUnderTest;
    private volatile long maxFactorToTry;

    public PrimesService() {
    }

    public List<Long> getSeedPrimes(){
        return ImmutableList.copyOf(seedPrimes);
    }

    @Override
    public boolean isPrime(long candidateUnderTest) {
        if (candidateUnderTest % 2 == 0) {
            return false;
        }

        ForkPrime.factorFound = false; //reset;
        setMaxFactorToTry(candidateUnderTest);

        List<List<Long>> primeSeedsProcessorList = setupSeedsPerProcessor();

        ForkJoinPool pool = new ForkJoinPool(NUM_PROCESSORS);
        for(int i = 0; i<NUM_PROCESSORS; i++) {
            ForkPrime forkPrimes = new ForkPrime(candidateUnderTest, primeSeedsProcessorList.get(i));
            pool.execute(forkPrimes);
        }

        pool.awaitQuiescence(20, TimeUnit.SECONDS);


        log.info(candidateUnderTest + " is " + (ForkPrime.factorFound ? " not " : "") + " prime");

        return !ForkPrime.factorFound;

    }

    void setMaxFactorToTry(long candidateUnderTest) {
        this.candidateUnderTest = candidateUnderTest;
        maxFactorToTry = MathsFunctions.floorSqrt(candidateUnderTest);
        if (maxFactorToTry % 2 == 0) {
            maxFactorToTry--; //ensure it is odd.
        }

        if (maxFactorToTry % 5 == 0) {
            maxFactorToTry -= 2; //5 is a waste of time to try, go for 3 at least
        }

        log.info("maxFactor to try = " + maxFactorToTry);
    }

    private List<List<Long>> setupSeedsPerProcessor() {

        int numProcessors = 2;//Runtime.getRuntime().availableProcessors();

        return  evenlyDistributeSeedPrimesAcrossProcessors(numProcessors);


    }

    final List<List<Long>> evenlyDistributeSeedPrimesAcrossProcessors(int numProcessors) {

        int integralCount = Math.floorDiv(seedPrimes.length, numProcessors);
        int remainderCount = seedPrimes.length - numProcessors * integralCount;

        List<List<Long>> seedArrays = new ArrayList<List<Long>>(numProcessors + remainderCount);

        for (int i = 0; i < numProcessors; i++) {
            seedArrays.add(i, new ArrayList<Long>(integralCount));
        }


        for (int i = 0; i <  integralCount * numProcessors; i++) {
                List<Long> currentProcessorSeeds = seedArrays.get(i % numProcessors);
                currentProcessorSeeds.add(seedPrimes[i]);
        }


        for(int i=integralCount * numProcessors; i<(integralCount * numProcessors + remainderCount); i++){
            List<Long> currentProcessorSeeds = seedArrays.get(i % numProcessors);
            currentProcessorSeeds.add(seedPrimes[i]);
        }

        return seedArrays;

    }

}
