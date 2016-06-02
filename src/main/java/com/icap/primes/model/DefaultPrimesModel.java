package com.icap.primes.model;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DefaultPrimesModel implements PrimesModel, PrimesModelListener {
    private static final Logger log = LoggerFactory.getLogger(DefaultPrimesModel.class);

    private final IPrimeService primesService;
    private static final long STARTING_PRIME = 67L;
    private long currentPrimeCandidate = STARTING_PRIME;
    private final List<Long> primesSoFar = new ArrayList<Long>();

    public DefaultPrimesModel(final IPrimeService primesService) {
        this.primesService = primesService;
        this.primesService.addListener(this);
        this.primesSoFar.addAll(Arrays.asList(primesService.getSeedPrimes()));
    }

    @Override
    public void seekPrimes(){
           if(primesService.isPrime(currentPrimeCandidate)){
               primesSoFar.add(currentPrimeCandidate);
               log.info("found new prime : " + currentPrimeCandidate);
           }
    }

    @Override
    public List<Long> getPrimesSoFar(){
        return ImmutableList.copyOf(primesSoFar);
    }


    @Override
    public void onTrigger(Long candidateFinished,boolean isPrime) {
        if(isPrime) {
            primesSoFar.add(candidateFinished);
            log.info("found new prime : " + candidateFinished);
        }

        currentPrimeCandidate += 2;
        primesService.isPrime(currentPrimeCandidate);
    }
}
