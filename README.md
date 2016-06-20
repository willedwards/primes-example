Background
==========

Given that there is no formula for calculating primes (unlike Fibonacci numbers), algorithms must be used.

The most famous is the sieve of eratosthenes
http://introcs.cs.princeton.edu/java/14array/PrimeSieve.java.html

At the core of the algorithm is the need to test the candidate number by every prime greater than 2, and the first prime less than the integer square root of the candidate.

As the size of the candidate increases, brute force computing power becomes slower for calculation. So speed becomes a concern.

The prime number theorem (https://en.wikipedia.org/wiki/Prime_number_theorem) is related to the Riemann zeta function, and gives a good overview of the probability of a candidate being prime.

Approach
========

The most simplistic approach would be to have a single loop that runs the algorithm.

However for a single machine with multi-core, each processor should be set to work in parallel, to test a subset of primes on the candidate.
Once a factor is found, then the processor "broadcasts" this so that the other processors stop.
Only if all processors work through their assigned primes, then the candidate is prime.

The found prime is then added to the set of existing primes, the processors are re-seeded and triggered to find the next prime.

The two computing candidates that naturally fit are MapReduce, and ForkJoin.

I will focus on the ForkJoin approach here. As a side note, Java 8 streams can also be employed to solve this, as they
use parallelism under the covers.

I have also added a streams / lambda implementation which works, but is ripe for a bit more optimisation.


Speedup
=======
Rather than a brute force divisibility, a smarter technique could be used to determine if a number is prime or not, via the following link:
http://www.savory.de/maths1.htm

This would act as a fast filter to weed out the vast majority of candidates that have low prime factors


Limitations
===========
There are an infinite set of primes.

Therefore, using java.lang.long will eventually be exceeded, and turn negative. 
The same would apply to BigInteger, however BigInteger offers the probability that a number is prime, and this could 

I wont deal with edge cases like 1,2, or negative numbers.

Out of scope improvements
=========================
1) For a multi-machine approach, the same technique could be employed, so that all machines are searching through a set of allocated primes, and if any factor is found, then it is broadcasted.
Ideally, this approach would replicate itself (using cassandra) so that it could "fan out" across a cloud environment.

2)The assignment of primes to test to each available core would potentially be a tuning exercise to increase performance

3)Beyond this, it would require an adaptive tuning approach, which is the realm of dynamic control theory.



