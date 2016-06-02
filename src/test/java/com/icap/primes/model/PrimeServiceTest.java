package com.icap.primes.model;

import com.icap.exceptions.PrimeServiceException;
import com.icap.primes.factory.PrimeServiceFactory;
import com.icap.primes.factory.PrimeType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Running these tests individually gives the real sense of the parallelism
 */
public class PrimeServiceTest
{

    @Test
    public void test63()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        assertFalse(service.isPrime(63));
    }

    @Test
    public void test67()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        assertTrue(service.isPrime(67));
    }

    @Test
    public void test731()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        assertFalse(service.isPrime(731));
    }

    @Test
    public void forkJoin851()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        assertFalse(service.isPrime(851));
    }

    @Test
    public void probablyPrime851()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.PROBABLE);
        assertFalse(service.isPrime(851));
    }

    @Test
    public void forkJoin5003()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        assertTrue(service.isPrime(5003));
    }

    @Test
    public void probablyPrime5003()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.PROBABLE);
        assertTrue(service.isPrime(5003));
    }

    @Test(expected = PrimeServiceException.class)
    public void forkJoinPrime100001()  {
        IPrimeService service = PrimeServiceFactory.getService(PrimeType.FORK_JOIN);
        service.isPrime(1000001);
    }

}
