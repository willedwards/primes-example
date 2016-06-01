package com.icap.primes.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PrimesServiceTest
{
    private static final Logger log = LoggerFactory.getLogger(PrimesServiceTest.class);

    PrimesService model = new PrimesService();

    @Test
    public void test63()  {
        assertFalse(model.isPrime(63));
    }

    @Test
    public void test67()  {
        assertTrue(model.isPrime(67));
    }

    @Test
    public void test731()  {
        assertFalse(model.isPrime(731));
    }

    @Test
    public void test851()  {
        assertFalse(model.isPrime(851));
    }

    @Test
    public void test5003()  {
        assertTrue(model.isPrime(5003));
    }
    
//    @Test
//    public void largePrime(){
//        assertFalse(model.isPrime(600851475143L));
//    }

    @Test
    public void testInitialSeedsFor2Arrays(){
        Long[] allSeedPrimes = model.getSeedPrimes();
        List<Long> splitSeeds1 =   model.evenlyDistributeSeedPrimesAcrossProcessors(2).get(0);
        List<Long> splitSeeds2 =   model.evenlyDistributeSeedPrimesAcrossProcessors(2).get(1);
//        List<Long> remainderSeeds =   model.evenlyDistributeSeedPrimesAcrossProcessors(2).get(2);

        int i=0;
        while(i<allSeedPrimes.length){

            Long currentSeed = allSeedPrimes[i];

            if(splitSeeds1.contains(currentSeed)){
                assertFalse(splitSeeds2.contains(currentSeed));
            }
            else if(splitSeeds2.contains(currentSeed)){
                assertFalse(splitSeeds1.contains(currentSeed));
            }

            i++;
        }

        log.info("splitSeeds1 = " + asList(splitSeeds1));
        log.info("splitSeeds2 = " + asList(splitSeeds2));
//        log.info("remainder = " + asList(remainderSeeds));
    }

    static String asList(final List<Long> longList) {
        StringBuilder sb = new StringBuilder();
        for(Long current : longList){
            sb.append(current + " ");
        }

        return sb.toString();
    }

}
