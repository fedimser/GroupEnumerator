package io.github.fedimser.finfield;

import io.github.fedimser.genum.FinGroup;

public class FiniteField {

    // Characteristic of a field. Prime number.
    private final long p;

    // Such that cardinality of this field is p^n;
    private final long n;

    // Such that this field is F_p[x]
    private final Polynomial irreducibleGenerator;

    private Polynomial elements[];
    private int[] inverseTable;
    private int[][] additionTable;
    private int[][] multiplicationTable;
    private int[][] divisionTable;
    private FinGroup additiveGroup;
    private FinGroup multiplicativeGroup;

    private FiniteField(long p, long n, Polynomial irreducibleGenerator) {
        assert (irreducibleGenerator.getModulo() == p);
        this.p = p;
        this.n = n;
        this.irreducibleGenerator = irreducibleGenerator;
        prepareTables();
        prepareGroups();
    }

    public static FiniteField create(long cardinality) {
        if (!canCreate(cardinality)) {
            throw new IllegalArgumentException("No field exists with such cardinality.");
        }
        long p = Utils.smallestPrimeDivisor(cardinality);
        long n = Utils.intLog(cardinality, p);
        return new FiniteField(p, n, Polynomial.createIrreducible(p, (int)n));
    }

    public static boolean canCreate(long cardinality) {
        if (cardinality <= 1) return false;
        return Utils.intLog(cardinality, Utils.smallestPrimeDivisor(cardinality)) != -1;
    }

    public long getCardinality() {
        return Utils.pow(p, n);
    }

    /**
     * Prints characteristic, irreducible polynomial and explicit addition, division and
     * multiplication tables.
     */
    public String describe() {
        String fieldName = "F_" + getCardinality();

        StringBuilder sb = new StringBuilder();
        sb
                .append(fieldName)
                .append(" is F_")
                .append(p)
                .append("[x]/(")
                .append(irreducibleGenerator.toString())
                .append(").\n");

        sb.append("Addition table for ").append(fieldName).append(":\n");
        printTable(additionTable, "+", sb);
        sb.append("\n");
        sb.append("Multiplication table for ").append(fieldName).append(":\n");
        printTable(multiplicationTable, "*", sb);
        sb.append("\n");
        sb.append("Division table for ").append(fieldName).append(":\n");
        printTable(divisionTable, "/", sb);
        sb.append("\n\n");

        return sb.toString();
    }

    private void prepareTables() {
        int card = (int) getCardinality();
        elements = new Polynomial[card];
        inverseTable = new int[card];
        additionTable = new int[card][card];
        multiplicationTable = new int[card][card];
        divisionTable = new int[card][card];

        for (int i = 0; i < card; i++) {
            elements[i] = Polynomial.createFromCompactForm(i, p);
        }

        for (int i = 0; i < card; i++) {
            Polynomial p1 = elements[i];
            for (int j = 0; j < card; j++) {
                Polynomial p2 = elements[j];
                int sum = (int) Polynomial.add(p1, p2).toCompactForm();
                int product = (int) Polynomial.residual(Polynomial.multiply(p1, p2),
                        irreducibleGenerator)
                        .toCompactForm();
                additionTable[i][j] = sum;
                multiplicationTable[i][j] = product;
                if (product == 1L) {
                    inverseTable[i] = j;
                    inverseTable[j] = i;
                }
            }
        }

        for (int i = 0; i < card; i++) {
            divisionTable[i][0] = -1;
            for (int j = 1; j < card; j++) {
                divisionTable[i][j] = multiplicationTable[i][(int) inverseTable[j]];
            }
        }
    }

    private void prepareGroups() {
        additiveGroup = new FinGroup(additionTable);

        int card = (int) getCardinality();
        int[][] multTableWithoutZero = new int[card - 1][card - 1];
        for (int i = 1; i < card; i++) {
            for (int j = 1; j < card; j++) {
                if (multiplicationTable[i][j] == 0) {
                    throw new IllegalStateException(String.format("Divisors of zero: %s, %s.",
                            elementToString(i), elementToString(j)));
                }
                multTableWithoutZero[i - 1][j - 1] = multiplicationTable[i][j] - 1;
            }
        }
        multiplicativeGroup = new FinGroup(multTableWithoutZero);
    }

    private String elementToString(long element) {
        if (element == -1) {
            return "N/A";
        } else {
            return elements[(int) element].toString("a");
        }
    }

    private void printTable(int[][] table, String opSymbol, StringBuilder sb) {
        int card = (int) getCardinality();
        for (int i = 0; i < card; i++) {
            for (int j = 0; j < card; j++) {
                sb.append(elementToString(i))
                        .append(" ")
                        .append(opSymbol)
                        .append(" ")
                        .append(elementToString(j))
                        .append(" = ")
                        .append(elementToString(table[i][j]))
                        .append("\n");
            }
        }
    }

    public FinGroup getAdditiveGroup() {
        return additiveGroup;
    }

    public FinGroup getMultiplicativeGroup() {
        return multiplicativeGroup;
    }
}
