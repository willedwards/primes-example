package com.icap.service.primes.model.primes.tools;

import java.math.BigInteger;


public class MathsFunctions {

    public static final int floorSqrt(final long x) {
        if ((x & 0xfff0000000000000L) == 0L) return (int) StrictMath.sqrt(x);
        final long result = (long) StrictMath.sqrt(2.0d*(x >>> 1));
        return result*result - x > 0L ? (int) result - 1 : (int) result;
    }

    // floorSqrt :: BigInteger -> BigInteger
    // Gives the exact floor of the square root of x, returning null (like Math.sqrt's NaN) if x is negative.
    public static final BigInteger floorSqrt(final BigInteger x) {

        int bit = Math.max(0, (x.bitLength() - 63) & 0xfffffffe); // last even numbered bit in first 64 bits
        BigInteger result = BigInteger.valueOf(floorSqrt(x.shiftRight(bit).longValue()) & 0xffffffffL);
        bit >>>= 1;
        result = result.shiftLeft(bit);
        while (bit != 0) {
            bit--;
            final BigInteger resultHigh = result.setBit(bit);
            if (resultHigh.multiply(resultHigh).compareTo(x) <= 0) result = resultHigh;
        }

        return result;
    }
}
