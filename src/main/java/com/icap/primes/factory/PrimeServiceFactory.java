package com.icap.primes.factory;

import com.icap.primes.model.IPrimeService;

/**
 * Created by will on 01/06/2016.
 */
public class PrimeServiceFactory {

    public static IPrimeService getService(PrimeType type){
        switch(type){
            case FORK_JOIN:
                return new PrimesForkJoinService();
            case PROBABLE:
                return new BigIntegerService();
            default:
                throw new RuntimeException("no such type" + type);
        }
    }
}
