package io.github.fedimser.genum;

import java.util.List;

public class IsoChecker {
    private int[][] g1;
    private int[][] g2;
    private int n;
    private Permutation curPerm;
    private boolean answer;

    public IsoChecker(FinGroup group) {
        n = group.getOrder();
        g1 = group.getMultTable();
    }

    public boolean isIsomorphic(FinGroup group) {
        if (group.getOrder() != n) return false;
        g2 = group.getMultTable();
        curPerm = new Permutation(n);
        curPerm.set(0, 0);
        answer = false;

        rec();

        return answer;
    }

    private void rec() {
        if (answer) return;

        // Must be:
        // g2[p(i)][p(j)] == p(g1[i][j])

        // Try fill up the permutation, as possible.
        for (int i = 0; i < n; i++) {
            int i1 = curPerm.get(i);
            if (i1 == -1) continue;
            for (int j = 0; j < n; j++) {
                int j1 = curPerm.get(j);
                if (j1 == -1) continue;
                int e = curPerm.get(g1[i][j]);
                int toPut = g2[i1][j1];
                if (e == -1) {
                    if (curPerm.getInverse(toPut) != -1) {
                        // Isomorphism impossible.
                        return;
                    }
                    curPerm.set(g1[i][j], toPut);
                } else if (e != toPut) {
                    // Isomorphism impossible.
                    return;
                }
            }
        }

        // Invariant: all elements in permutation can cause isomorphism.

        int pos = curPerm.getEmptyPos();

        if (pos == -1) {
            answer = true;
        } else {
            Permutation backupPerm = curPerm.clone();
            for (int i : backupPerm.getPossible()) {
                curPerm = backupPerm.clone();
                curPerm.set(pos, i);
                rec();
            }
        }

    }

    public static boolean areIsomorhic(FinGroup g1, FinGroup g2) {
        return (new IsoChecker(g1)).isIsomorphic(g2);
    }

    // Checks whether there is one-to one isomorphism between lists of groups.
    // Namely, that exists such permutation p, that for each i, group g1[i] is isomorphic to g2[p[i]].
    public static boolean areListsIsomorhic(List<FinGroup> groups1, List<FinGroup> groups2) {
        int n = groups1.size();
        if (n != groups2.size()) return false;

        boolean used[] = new boolean[n]; // Indices in g2, to which we already found isomorphic groups.
        for (int i = 0; i < n; i++) {
            IsoChecker checker = new IsoChecker(groups1.get(i));
            boolean found = false;
            for (int j = 0; j < n; j++) {
                if (used[j]) continue;
                if (checker.isIsomorphic(groups2.get(j))) {
                    found = true;
                    used[j] = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

}
