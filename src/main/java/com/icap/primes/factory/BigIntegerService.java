package com.icap.primes.factory;

import com.icap.primes.model.IPrimeService;
import com.icap.primes.model.PrimesModelListener;

import java.math.BigInteger;

class BigIntegerService implements IPrimeService {

    @Override
    public Long[] getSeedPrimes() {
        return new Long[0];//not implemented
    }

    @Override
    public boolean isPrime(long candidateNumber) {
        BigInteger candidateBigInteger = BigInteger.valueOf(candidateNumber);
        return candidateBigInteger.isProbablePrime(10);
    }

    @Override
    public void addListener(PrimesModelListener listener) {

    }
}
