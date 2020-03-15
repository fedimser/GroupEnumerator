package io.github.fedimser.finfield;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents polynomial over remainders modulo p.
 */
public class Polynomial {
    private final long p;
    private final ImmutableList<Long> coefs;
    private final int degree;

    private Polynomial(List<Long> coefs, long p) {
        for (Long coef : coefs) assert (coef >= 0 && coef < p);
        assert (coefs.get(coefs.size() - 1) != 0 || coefs.size() == 1);

        this.p = p;
        this.coefs = ImmutableList.copyOf(coefs);
        this.degree = this.coefs.size() - 1;
    }

    @VisibleForTesting
    static Polynomial createFromArray(Long[] array, long p) {
        int last = array.length;
        while (last != 1 && array[last - 1] == 0) last--;
        if (last != array.length) {
            array = Arrays.copyOf(array, last);
        }
        return new Polynomial(ImmutableList.copyOf(array), p);
    }

    public static Polynomial createFromCompactForm(long compactForm, long p) {
        ImmutableList.Builder<Long> coefs = ImmutableList.builder();
        if (compactForm == 0L) {
            coefs.add(0L);
        }
        while (compactForm != 0) {
            coefs.add(compactForm % p);
            compactForm /= p;
        }
        return new Polynomial(coefs.build(), p);
    }

    // TODO: implement more efficient algorithm.
    public static Polynomial createIrreducible(long p, int degree) {
        if (degree == 1) {
            return createFromCompactForm(p, p);
        }
        int pPowN = (int) Utils.pow(p, degree);
        List<List<Polynomial>> monicsByDegree = new ArrayList<List<Polynomial>>();
        for(int i=0;i<=degree;i++) {
            monicsByDegree.add(new ArrayList<Polynomial>());
        }
        for (long i = 0; i < pPowN; i++) {
            Polynomial poly = createFromCompactForm(i, p);
            if (poly.isMonic()) {
                monicsByDegree.get(poly.degree).add(poly);
            }
        }

        Boolean[] reducible = new Boolean[2 * pPowN];
        Arrays.fill(reducible, false);
        for (int deg1 = 1; deg1 < degree; deg1++) {
            int deg2 = degree - deg1;
            if (deg2 < deg1) break;

            for (Polynomial poly1 : monicsByDegree.get(deg1)) {
                for (Polynomial poly2 : monicsByDegree.get(deg2)) {
                    reducible[(int) multiply(poly1, poly2).toCompactForm()] = true;
                }
            }
        }

        for (int i = pPowN; i < 2 * pPowN; i++) {
            if (!reducible[i]) {
                return createFromCompactForm(i, p);
            }
        }
        throw new IllegalStateException("Irreducible polynomial not found.");
    }

    public static Polynomial add(Polynomial p1, Polynomial p2) {
        assert (p1.p == p2.p);
        Long[] ans = new Long[Math.max(p1.degree, p2.degree) + 1];
        Arrays.fill(ans, 0L);
        for (int i = 0; i <= p1.degree; i++) ans[i] = p1.coefs.get(i);
        for (int i = 0; i <= p2.degree; i++) ans[i] = (ans[i] + p2.coefs.get(i)) % p1.p;
        return createFromArray(ans, p1.p);
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        assert (p1.p == p2.p);
        int deg = p1.degree + p2.degree;
        Long[] ans = new Long[deg + 1];
        Arrays.fill(ans, 0L);
        for (int i = 0; i <= p1.degree; i++) {
            for (int j = 0; j <= p2.degree; j++) {
                ans[i + j] += (p1.coefs.get(i) * p2.coefs.get(j));
            }
        }
        for (int i = 0; i <= p1.degree + p2.degree; i++) ans[i] = ans[i] % p1.p;
        return createFromArray(ans, p1.p);
    }

    public Polynomial multiplyBy(long x) {
        Long[] ans = new Long[degree + 1];
        for (int i = 0; i <= degree; i++) ans[i] = (x * coefs.get(i) % p + p) % p;
        return createFromArray(ans, p);
    }

    public Polynomial shiftBy(int x) {
        Long[] ans = new Long[degree + 1 + x];
        Arrays.fill(ans, 0L);
        for (int i = 0; i <= degree; i++) ans[i + x] = coefs.get(i);
        return createFromArray(ans, p);
    }

    /**
     * Returns such r, that p = a*q + r.
     */
    public static Polynomial residual(Polynomial p, Polynomial q) {
        assert (p.p == q.p);
        while (p.degree >= q.degree) {
            long k = Utils.divMod(p.coefs.get(p.degree), q.coefs.get(q.degree), p.p);
            Polynomial toAdd = q.multiplyBy(-k).shiftBy(p.degree - q.degree);
            p = add(p, toAdd);
        }
        return p;
    }

    public long valueAt(long x) {
        long x_pow = 1;
        long ans = 0;
        for (int i = 0; i <= degree; i++) {
            ans = (ans + x_pow * coefs.get(i)) % p;
            x_pow = (x_pow * x) % p;
        }
        return ans;
    }

    public long toCompactForm() {
        long x_pow = 1;
        long ans = 0;
        for (int i = 0; i <= degree; i++) {
            ans += x_pow * coefs.get(i);
            x_pow = x_pow * p;
        }
        return ans;
    }

    public long getDegree() {
        return coefs.size() - 1;
    }

    public long getModulo() {
        return p;
    }

    public boolean isMonic() {
        return coefs.get(coefs.size() - 1) == 1L;
    }

    public static String polynomialToString(ImmutableList<Long> coefs, String variableName) {
        int degree = coefs.size() - 1;
        assert (degree == 0 || coefs.get(degree) !=0);
        if (degree == 0) {
            return "" + coefs.get(0);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = degree; i >= 0; i--) {
            if (coefs.get(i) != 0) {
                if (sb.length() > 0) sb.append("+");
                if (coefs.get(i) != 1 || i == 0) sb.append(coefs.get(i));
                if (coefs.get(i) != 1 && i > 0) sb.append("*");
                if (i > 0) sb.append(variableName);
                if (i > 1) sb.append("^").append(i);
            }
        }
        return sb.toString();
    }

    public static String polynomialToString(ImmutableList<Integer> coefs) {
        return  polynomialToString(Utils.convertListToLong(coefs), "x");
    }

    public String toString(String variableName) {
        return polynomialToString(this.coefs, variableName);
    }

    @Override
    public String toString() {
        return toString("x");
    }
}
