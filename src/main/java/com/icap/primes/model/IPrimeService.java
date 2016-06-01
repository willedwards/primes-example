package com.icap.primes.model;


public interface IPrimeService {
    Long[] getSeedPrimes();

    boolean isPrime(long candidateNumber);

    void addListener(PrimesModelListener listener);

}
