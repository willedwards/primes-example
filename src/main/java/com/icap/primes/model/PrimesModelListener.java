package com.icap.primes.model;

/**
 * Created by will on 01/06/2016.
 */
public interface PrimesModelListener {

    void onTrigger(Long newPrimeFound, boolean isPrime);
}
