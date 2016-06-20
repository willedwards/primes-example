package com.icap.primes.factory;

import com.icap.primes.model.IPrimeService;
import com.icap.primes.model.PrimesModelListener;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by will on 20/06/2016.
 */
public class PrimeFunctionService implements IPrimeService {

    FunctionalPrime functionalPrime = new FunctionalPrime();
    List<Long> seedPrimes = functionalPrime.getAllPrimes(10);
    List<Long> foundPrimes = seedPrimes.stream().collect(Collectors.toList());
    PrimesModelListener primesModelListener;

    @Override
    public Long[] getSeedPrimes() {
        return seedPrimes.toArray(new Long[10]);
    }

    @Override
    public boolean isPrime(long candidateUnderTest) {

        boolean isAPrime = FunctionalPrime.isPrime(candidateUnderTest);



        return isAPrime;

    }

    @Override
    public void addNewPrime(Long prime) {
        foundPrimes.add(prime);
    }

    @Override
    public void addListener(PrimesModelListener listener) {
        this.primesModelListener = listener;
    }

}
