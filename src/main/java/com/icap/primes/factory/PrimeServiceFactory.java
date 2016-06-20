package com.icap.primes.factory;

import com.icap.primes.model.IPrimeService;


public class PrimeServiceFactory {

    public static IPrimeService getService(PrimeType type){
        switch(type){
            case FORK_JOIN:
                return new PrimesForkJoinService();
            case PROBABLE:
                return new BigIntegerService();
            case FUNCTIONAL:
                return new PrimeFunctionService();
            default:
                throw new RuntimeException("no such type" + type);
        }
    }
}
