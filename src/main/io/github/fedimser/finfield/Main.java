package io.github.fedimser.finfield;

import com.google.common.collect.ImmutableList;

public class Main {

    public static void main(String[] args) {
        findRoots();
    }

    private static void fieldDescribe() {
        FiniteField field = FiniteField.create(27);
        System.out.println(field.describe(true));
    }

    private static void findRoots() {
        FiniteField field = FiniteField.create(4);
        ImmutableList<Integer> poly = ImmutableList.of(0, 0, 0, 1);
        field.analyzePolynomial(poly);
    }
}
