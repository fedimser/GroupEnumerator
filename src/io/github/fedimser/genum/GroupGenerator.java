package io.github.fedimser.genum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupGenerator {

    private List<FinGroup> groups;
    private int[][] g;
    private int n;
    private boolean somethingChanged = false;

    /**
     * @param order
     * @return
     */
    public List<FinGroup> getAllGroups(int order) {
        groups = new ArrayList<FinGroup>();
        n = order;
        g = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            g[0][i] = g[i][0] = i;
        }

        rec();

        return groups;
    }

    private void rec() {
        int ci = -1, cj = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == -1) {
                    ci = i;
                    cj = j;
                    break;
                }
            }
            if (ci != -1) break;
        }

        if (ci == -1) {
            // This means we at leaf of recursion.
            FinGroup candidate = new FinGroup(g);
            boolean isDuplicate = false;
            IsoChecker checker = new IsoChecker(candidate);
            for (FinGroup g : groups) {
                if (checker.isIsomorphic(g)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                groups.add(new FinGroup(g));
            }
        } else {
            int[][] backup = new int[n][n];
            copyArray(backup, g);
            for (int i = 0; i < n; i++) {
                copyArray(g, backup);
                g[ci][cj] = i;
                //System.out.println("Recursive call with:");
                //System.out.println(FinGroup.multTableToString(g));
                if (check()) rec();
            }
        }
    }


    private void copyArray(int[][] a1, int[][] a2) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a1[i][j] = a2[i][j];
            }
        }
    }

    private int getG(int x, int y, boolean permIndex) {
        return (permIndex ? g[y][x] : g[x][y]);
    }

    private void setG(int x, int y, boolean permIndex, int value) {
        if (permIndex) {
            if (g[y][x] != value) somethingChanged = true;
            g[y][x] = value;
        } else {
            if (g[x][y] != value) somethingChanged = true;
            g[x][y] = value;
        }
    }


    private boolean checkInverses(boolean permIndex) {
        for (int i = 1; i < n; i++) {
            int ctrInv = 0;
            int ctrUnd = 0;
            int inv = 0;
            int und = 0;
            for (int j = 1; j < n; j++) {
                int v = getG(i, j, permIndex);
                if (v == 0) {
                    ctrInv++;
                    inv = j;
                } else if (v == -1) {
                    ctrUnd++;
                    und = j;
                }
            }
            if (ctrInv >= 2) return false;
            if (ctrUnd == 0 && ctrInv != 1) return false;

            if (ctrUnd == 1 && ctrInv == 0) {
                inv = und;
                ctrInv = 1;
                ctrUnd = 0;
                setG(i, inv, permIndex, 0);
            }

            if (ctrInv == 1) {
                int v = getG(inv, i, permIndex);
                if (v == -1) {
                    setG(inv, i, permIndex, 0);
                } else if (v != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    // Returns false if group is impossible to complete.
    // Returns true otherwise, having completed the group as much as possible.
    private boolean check() {

        do {
            somethingChanged = false;

            // Inverses in rows.
            if (!checkInverses(false)) return false;

            // Inverses in columns.
            if (!checkInverses(true)) return false;


            // Inverses in columns.
            for (int i = 1; i < n; i++) {
                int ctrInv = 0;
                int ctrUnd = 0;
                int inv = 0;
                int und = 0;
                for (int j = 1; j < n; j++) {
                    if (g[j][i] == 0) {
                        ctrInv++;
                        inv = j;
                    } else if (g[i][j] == -1) {
                        ctrUnd++;
                        und = j;
                    }
                }
                if (ctrInv >= 2) return false;
                if (ctrUnd == 0 && ctrInv != 1) return false;

                if (ctrUnd == 1 && ctrInv == 0) {
                    inv = und;
                    ctrInv = 1;
                    ctrUnd = 0;
                    g[i][inv] = 0;
                }

                if (ctrInv == 1) {
                    if (g[inv][i] == -1) {
                        g[inv][i] = 0;
                        somethingChanged = true;
                    } else if (g[inv][i] != 0) {
                        return false;
                    }
                }
            }

            // Associativity.
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int a = g[i][j];
                    if (a == -1) continue;
                    for (int k = 0; k < n; k++) {
                        int b = g[j][k];
                        if (b == -1) continue;
                        if (g[a][k] != -1 && g[i][b] != -1) {
                            if (g[a][k] != g[i][b]) {
                                // Associativity violated.
                                return false;
                            }
                        } else if (g[a][k] != -1) {
                            g[i][b] = g[a][k];
                            somethingChanged = true;
                        } else if (g[i][b] != -1) {
                            g[a][k] = g[i][b];
                            somethingChanged = true;
                        }
                    }
                }
            }
        } while (somethingChanged);

        return true;
    }

}
