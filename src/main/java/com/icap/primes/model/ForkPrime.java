package com.icap.primes.model;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;


public class ForkPrime extends RecursiveTask<Boolean> {
    private static final Logger log = LoggerFactory.getLogger(ForkPrime.class);
    private final long primeCandidate;
    private final long[] primeFactorsToTry;

    public ForkPrime(long primeCandidate, final long[] primeFactorsToTry) {
        log.info("will try the following primes " + Arrays.toString(primeFactorsToTry));
        this.primeCandidate = primeCandidate;
        this.primeFactorsToTry = primeFactorsToTry;
    }

    public static volatile boolean factorFound = false;

    public ForkPrime(long candidateUnderTest, List<Long> longs) {
        this(candidateUnderTest, Longs.toArray(longs));
    }

    /**
     *
     * @return true if the number is prime
     */
    protected Boolean compute() {
        long startTime = System.currentTimeMillis();
        long delta;
        boolean isPrime = true;
        int i=0;
        while(i < primeFactorsToTry.length) {

            long currentPrime = primeFactorsToTry[i++];
            log.info("tried " + currentPrime);

            if (primeCandidate % currentPrime == 0){
                isPrime = false;
                log.info(currentPrime + " is a factor !");
                factorFound = true;
                break;
            }

            if(factorFound){
                log.info("stopping due to external discovery");
                break;
            }

        }

        delta = System.currentTimeMillis() - startTime;
        log.info("took " + delta + " ms");

        return isPrime;
    }


}
