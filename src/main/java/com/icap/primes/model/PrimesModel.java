package com.icap.primes.model;

import com.icap.primes.model.IPrimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 01/06/2016.
 */
public class PrimesModel {
    private static final Logger log = LoggerFactory.getLogger(PrimesModel.class);

    private final IPrimeService primesService;
    private static final long STARTING_PRIME = 67L;
    private long currentPrimeCandidate = STARTING_PRIME;
    private volatile boolean stop;
    private List<Long> primesSoFar = new ArrayList<Long>();

    public PrimesModel(final IPrimeService primesService) {
        this.primesService = primesService;
        //this.primesSoFar = primesService.getSeedPrimes();
    }

    public void seekPrimes(){
        while(!stop) {
           if(primesService.isPrime(currentPrimeCandidate)){
               primesSoFar.add(currentPrimeCandidate);
               log.info("found new prime : " + currentPrimeCandidate);
           }
        }


    }

    public List<Long> getPrimesSoFar(){
        return primesSoFar;
    }

    public void stop(){
        this.stop = true;
    }
}
