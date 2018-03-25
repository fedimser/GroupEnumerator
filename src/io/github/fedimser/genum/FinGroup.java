package io.github.fedimser.genum;

import javax.swing.plaf.multi.MultiTabbedPaneUI;

public class FinGroup {
    private int order;
    private int g[][];  // Multiplication table.
    private int inv[];  // Inverse element table.

    private boolean correct = false;


    // FinGroup represents correct finite group and is immutable.
    // Elements are denoted bu integers 0..order-1.
    // Unity is always element 0.
    // This constructor takes table of multiplication.
    public FinGroup(int[][] mulTable) throws IllegalArgumentException {
        order = mulTable.length;
        if(mulTable[0].length != order) throw new IllegalArgumentException("Bad size.");
        g = new int[order][order];
        inv = new int[order];

        for(int i=0;i<order;i++) {
            for(int j=0;j<order;j++) {
                g[i][j] = mulTable[i][j];
                if(g[i][j]<0 || g[i][j]>=order) throw new IllegalArgumentException("Bad mult table.");
                if(g[i][j]==0) {
                    inv[i] = j;
                    inv[j] = i;
                }
            }
        }

        // Check unity.
        for(int i=0;i<order; i++) {
            if(g[0][i]!=i || g[i][0]!=i) throw new IllegalArgumentException("Bad unity.");
        }

        // Check inverses.
        for(int i=0;i<order; i++) {
            if(g[i][inv[i]]!=0 || g[inv[i]][i]!=0) throw new IllegalArgumentException("Bad inverses.");

            for(int j=0;j<order; j++) {
                if( (g[i][j]==0) !=  (j==inv[i])) throw new IllegalArgumentException("Bad inverses.");
            }
        }

        // Check associativity.
        for(int i=0;i<order; i++) {
            for(int j=0;j<order; j++) {
                for(int k=0;k<order; k++) {
                    if(g[g[i][j]][k]!=g[i][g[j][k]]) throw new IllegalArgumentException("Not associative");
                }
            }
        }
    }


    public boolean isAbelian() {
        for(int i=0;i<order; i++) {
            for(int j=0;j<order;j++) {
                if (g[i][j]!=g[j][i]) return false;
            }
        }
        return true;
    }

    public String writeMultTable() {
        return multTableToString(g);
    }

    public static String multTableToString(int[][] g) {
        int n = g.length;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                sb.append(String.valueOf(g[i][j])+"\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getOrder() {
        return order;
    }

    public int[][] getMultTable() {
        int[][] ans = new int[order][order];
        for(int i=0;i<order; i++) {
            for(int j=0;j<order; j++) {
                ans[i][j] = g[i][j];
            }
        }
        return ans;
    }

    public int getInverse(int el) {
        return inv[el];
    }

    public int groupOp(int el1, int el2) {
        return g[el1][el2];
    }


    public static FinGroup multiplyGroups(FinGroup g1, FinGroup g2) {
        int n1 = g1.getOrder();
        int n2 = g2.getOrder();
        int n = n1*n2;
        int[][] g = new int[n][n];
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                // (a1, a2) * (b1, b2) = (a1*b1, a2*b2)
                // a1, b1 from g1; a2, b2 from g2.
                int a1 = i / n2;
                int a2 = i % n2;
                int b1 = j / n2;
                int b2 = j % n2;
                g[i][j] = n2*(g1.groupOp(a1,b1)) + g2.groupOp(a2, b2);
            }
        }
        return new FinGroup(g);
    }
}
