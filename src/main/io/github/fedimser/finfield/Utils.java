package io.github.fedimser.finfield;

import com.google.common.collect.ImmutableList;

public class Utils {

    private Utils() {
    }

    static long pow(long x, long y) {
        long ans = 1;
        long m = x;
        while (y != 0) {
            if (y % 2 == 1) ans = ans * m;
            m = m * m;
            y /= 2;
        }
        return ans;
    }

    static long powMod(long x, long y, long p) {
        long ans = 1;
        long m = x;
        while (y != 0) {
            if (y % 2 == 1) ans = (ans * m) % p;
            m = (m * m) % p;
            y /= 2;
        }
        return ans;
    }

    /**
     * p must be prime.
     */
    static long divMod(long x, long y, long p) {
        return (x * powMod(y, p - 2, p)) % p;
    }

    static long smallestPrimeDivisor(long x) {
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return i;
        }
        return x;
    }

    static boolean isPrime(long x) {
        return smallestPrimeDivisor(x) == x;
    }

    /**
     * Returns n such that x=p^n, or -1 if such n doesn't exist.
     */
    static long intLog(long x, long p) {
        long curX = 1;
        for (int i = 1; ; i++) {
            curX *= p;
            if (curX == x) return i;
            if (curX > x) return -1;
        }
    }

    static ImmutableList<Long> convertListToLong(ImmutableList<Integer> list) {
        ImmutableList.Builder<Long> ans = ImmutableList.builder();
        for(int x: list) ans.add((long)x);
        return ans.build();
    }
}
