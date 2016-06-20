package com.icap.primes.factory;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;


class FunctionalPrime {

    public List<Long> getAllPrimes(int numberOfPrimes){
        return getPrimes().limit(numberOfPrimes).collect(toList());
    }

    //This is quite inefficient
    static boolean isPrime(Long number) {
        return LongStream.range(2, number).noneMatch(index -> number % index == 0);
    }

    private static Long next(Long number) {
        if (isPrime(number + 1))
            return number + 1;
        else
            return next(number + 1);
    }

    private static Stream<Long> getPrimes() {
        return Stream.iterate(2L, FunctionalPrime::next);
    }


}
