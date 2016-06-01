package com.icap.primes.factory;

import com.icap.primes.model.IPrimeService;
import com.icap.primes.model.PrimesModelListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * This class concernes iteslf with determining if a number is prime or not
 *
 */
class PrimesForkJoinService implements IPrimeService {

    private static final Logger log = LoggerFactory.getLogger(PrimesForkJoinService.class);
    private static final int NUM_PROCESSORS = 2;//Runtime.getRuntime().availableProcessors();

    private static final Long[] seedPrimes = new Long[]{3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L};

    private PrimesModelListener listener;


    public PrimesForkJoinService() {
    }


    @Override //Allows for inspection and testing
    public Long[] getSeedPrimes(){
        return Arrays.copyOf(seedPrimes, seedPrimes.length);//not great, Immutable would be better...
    }

    @Override
    public boolean isPrime(long candidateUnderTest) {
        if (candidateUnderTest % 2 == 0) {
            return false;
        }

        ForkPrime.factorFound = false; //reset;

        ForkJoinPool pool = new ForkJoinPool(NUM_PROCESSORS);
        ForkPrime forkPrimes = new ForkPrime(candidateUnderTest, seedPrimes);
        pool.execute(forkPrimes);

        pool.awaitQuiescence(60, TimeUnit.SECONDS);

        try {
            pool.awaitTermination(100,TimeUnit.MILLISECONDS);//without this, we end up with out of memory errors.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info(candidateUnderTest + " is " + (ForkPrime.factorFound ? " not " : "") + " prime");

        boolean isPrime = !ForkPrime.factorFound;

        if(listener !=null){
            listener.onTrigger(candidateUnderTest,isPrime);
        }

        return isPrime;

    }

    @Override
    public void addListener(PrimesModelListener listener) {
        this.listener = listener;
    }

}
