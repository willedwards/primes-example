package com.icap.service;

import com.icap.service.primes.model.PrimesModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PrimesModelTest
{


    @Test
    public void test63() throws InterruptedException {
        expectIsPrime(63,false);

    }

    private void expectIsPrime(long number, boolean isPrime) throws InterruptedException {
        PrimesModel model = new PrimesModel(number);
        assertEquals(!isPrime, model.hasFactors());
    }

    @Test
    public void test67() throws InterruptedException {
        expectIsPrime(67, true);
    }

    @Test
    public void test731() throws InterruptedException {
        expectIsPrime(731,false);
    }

    @Test
    public void test851() throws InterruptedException {
        expectIsPrime(851,false);
    }

    @Test
    public void test5003() throws InterruptedException {
        expectIsPrime(5003,true);
    }

}
