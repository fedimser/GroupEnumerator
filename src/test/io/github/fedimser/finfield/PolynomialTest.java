package io.github.fedimser.finfield;

import static io.github.fedimser.finfield.Polynomial.createFromArray;
import static io.github.fedimser.finfield.Polynomial.multiply;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class PolynomialTest {

    @Test
    public void testAddition() {
        Polynomial p1 = createFromArray(new Long[]{1L, 2L, 3L}, 10);
        Polynomial p2 = createFromArray(new Long[]{3L, 8L, 8L, 4L}, 10);

        Polynomial p3 = Polynomial.add(p1, p2);

        assertEquals("4*x^3+x^2+4", p3.toString());
    }

    @Test
    public void testMultiplication() {
        testMul(createFromArray(new Long[]{1L, 1L}, 10),
                createFromArray(new Long[]{1L, 1L}, 10),
                "x^2+2*x+1");

    }


    @Test
    public void testResidual() {
        Polynomial p1 = createFromArray(new Long[]{1L, 1L, 1L, 1L}, 10);
        Polynomial p2 = createFromArray(new Long[]{3L, 2L, 1L}, 10);

        Polynomial p3 = Polynomial.residual(p1, p2);

        assertEquals("4", p3.toString());
    }

    @Test
    public void testResidual_2() {
        Polynomial p1 = createFromArray(new Long[]{1L, 1L}, 10);
        Polynomial p2 = createFromArray(new Long[]{0L, 0L, 0L, 2L}, 10);

        Polynomial p3 = Polynomial.residual(p1, p2);

        assertEquals("x+1", p3.toString());
    }

    @Test
    public void testToString_constantZero() {
        assertEquals("0", createFromArray(new Long[]{0L}, 10).toString(), "0");
    }


    private static void testMul(Polynomial p1, Polynomial p2, String expectedProduct) {
        assertEquals(multiply(p1, p2).toString(), expectedProduct);
    }


}
