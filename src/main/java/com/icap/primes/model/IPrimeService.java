package com.icap.primes.model;


public interface IPrimeService {
    Long[] getSeedPrimes();

    boolean isPrime(long candidateUnderTest);

    void addNewPrime(Long prime);

    void addListener(PrimesModelListener listener);

}
