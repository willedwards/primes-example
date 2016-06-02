package com.icap.primes.factory;

import com.icap.service.primes.model.primes.tools.MathsFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;


class ForkPrime extends RecursiveTask<Boolean> {
    private static final Logger log = LoggerFactory.getLogger(ForkPrime.class);
    private final long primeCandidate;
    private final Long[] primeFactorsToTry;
    private int maxFactorToTry;



    private static volatile int currentParallelTasks = 1;
    public static volatile boolean factorFound = false;

    public ForkPrime(long primeCandidate, final Long[] seedsToFork) {
        log.debug("will try the following primes " + toString(seedsToFork));
        this.primeCandidate = primeCandidate;
        this.primeFactorsToTry = Arrays.copyOfRange(seedsToFork, 0, seedsToFork.length);
        setMaxFactorToTry(primeCandidate);
    }



    /**
     *
     * @return true if the number is prime
     */
    protected Boolean compute() {

        List<Long[]> seeds = evenlySplitSeedPrimes(primeFactorsToTry);
        Long[] seedsToFork =  seeds.get(0);
        Long[] seedsForThis =  seeds.get(1);

        if(currentParallelTasks < 2) {
            ForkPrime left = new ForkPrime(primeCandidate,seedsToFork);
            ForkPrime right = new ForkPrime(primeCandidate,seedsForThis);

            currentParallelTasks += 1;
            left.fork();

            boolean rightAnswer = right.compute();
            boolean leftAnswer = left.join();
            return rightAnswer && leftAnswer;
        }
        else {
            return computeDirectly();
        }
    }

    final List<Long[]> evenlySplitSeedPrimes(Long[] seedPrimes) {

        int integralCount = Math.floorDiv(seedPrimes.length, 2);
        int remainderCount = seedPrimes.length - integralCount;

        Long[] seedArray1 = new Long[integralCount];
        Long[] seedArray2 = new Long[remainderCount];

        for (int i = 0; i <  seedPrimes.length -1; i++ ) {
            if(i % 2 == 0) {
                seedArray1[i/2] = seedPrimes[i];
            }
            else{
                seedArray2[(i - 1)/2] = seedPrimes[i];
            }
        }

        if(seedPrimes.length % 2 == 1){ //an odd number
            seedArray2[seedArray2.length -1] = seedPrimes[seedPrimes.length-1];
        }

        return Arrays.asList(seedArray1,seedArray2);

    }

    private Boolean computeDirectly(){
        log.debug("directly computing");
        long startTime = System.currentTimeMillis();
        long delta;
        boolean isPrime = true;
        int i=0;
        while(i < primeFactorsToTry.length) {

            long currentPrime = primeFactorsToTry[i];
            log.debug("tried " + currentPrime);

            if (primeCandidate % currentPrime == 0){
                isPrime = false;
                log.debug(currentPrime + " is a factor !");
                factorFound = true;
                break;
            }

            if(factorFound){
                log.info("stopping due to external discovery");
                break;
            }

            i++;

        }

        delta = System.currentTimeMillis() - startTime;
        log.debug("took " + delta + " ms");

        return isPrime;

    }


    static String toString(Long[] values){
        StringBuilder sb = new StringBuilder();
        for(Long l : values){
            sb.append(l.toString() +  " ");
        }
        return sb.toString();
    }

    public int getMaxFactorToTry() {
        return maxFactorToTry;
    }

    private void setMaxFactorToTry(long candidateUnderTest) {
        maxFactorToTry = MathsFunctions.floorSqrt(candidateUnderTest);
        if (maxFactorToTry % 2 == 0) {
            maxFactorToTry--; //ensure it is odd.
        }

        if (maxFactorToTry % 5 == 0) {
            maxFactorToTry -= 2; //5 is a waste of time to try, go for 3 at least
        }

        log.debug("maxFactor to try = " + maxFactorToTry);
    }
}
