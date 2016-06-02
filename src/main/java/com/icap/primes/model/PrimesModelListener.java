package com.icap.primes.model;


public interface PrimesModelListener {

    void onTrigger(Long newPrimeFound, boolean isPrime);
}
