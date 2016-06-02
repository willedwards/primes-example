package com.icap.primes.factory;

import com.icap.exceptions.PrimeServiceException;
import com.icap.primes.model.IPrimeService;
import com.icap.primes.model.PrimesModelListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * This class concerns itself with determining if a number is prime or not.
 * It does not go looking for candidates or filling the gap for primes. That is the job of the PrimeModel
 *
 */
class PrimesForkJoinService implements IPrimeService {

    private static final Logger log = LoggerFactory.getLogger(PrimesForkJoinService.class);
    private static final int NUM_PROCESSORS = 2;//Runtime.getRuntime().availableProcessors();

    private Long[] seedPrimes = new Long[]{3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L};
    private ForkJoinPool pool = new ForkJoinPool(NUM_PROCESSORS);

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

        if(checkifIsInSeedPrimes(candidateUnderTest)){
            return true;
        }

        ForkPrime.factorFound = false; //reset;

        ForkPrime forkPrimes = new ForkPrime(candidateUnderTest, seedPrimes);

        checkEnoughSeedPrimes(forkPrimes.getMaxFactorToTry());

        pool.execute(forkPrimes);

        pool.awaitQuiescence(60, TimeUnit.SECONDS);

//        try {
//            pool.awaitTermination(100,TimeUnit.MILLISECONDS);//without this, we end up with out of memory errors.
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        log.info(candidateUnderTest + " is " + (ForkPrime.factorFound ? " not " : "") + " prime");

        boolean isPrime = !ForkPrime.factorFound;

        if(listener !=null){
            listener.onTrigger(candidateUnderTest,isPrime);
        }

        return isPrime;

    }

    @Override
    public void addNewPrime(final Long prime) {

        List<Long> currentPrimes = Arrays.asList(seedPrimes);
        if(currentPrimes.contains(prime)){
            return;
        }

        currentPrimes.add(prime);

        this.seedPrimes = currentPrimes.toArray(new Long[currentPrimes.size()]);
    }

    private boolean checkifIsInSeedPrimes(long candidateUnderTest) {
        return Arrays.asList(seedPrimes).contains(candidateUnderTest);
    }

    private void checkEnoughSeedPrimes(int maxFactorToTry) {
        if(maxFactorToTry > seedPrimes[seedPrimes.length -1]){
            throw new PrimeServiceException("not enough seed primes right now");
        }
    }

    @Override
    public void addListener(PrimesModelListener listener) {
        this.listener = listener;
    }

}
