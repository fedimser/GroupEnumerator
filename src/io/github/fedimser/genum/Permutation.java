package io.github.fedimser.genum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {
    private int n;
    private int[] perm;
    private int[] inv;

    private Permutation(Permutation p) {
        this.n = p.n;
        this.perm = new int[n];
        this.inv = new int[n];
        for(int i=0;i<n;i++) {
            this.perm[i] = p.perm[i];
            this.inv[i] = p.inv[i];
        }
    }



    public Permutation(int[] perm) {
        this.n = perm.length;
        this.perm = new int[n];
        this.inv = new int[n];
        for(int i=0;i<n;i++){
            this.perm[i]=perm[i];
            this.inv[perm[i]] = i;
        }
    }


    public Permutation(int n) {
        this.n = n;
        perm = new int[n];
        inv = new int[n];
        for(int i=0;i<n;i++)perm[i]=inv[i]=-1;
    }

    public void set(int pos, int value) {
        assert(perm[pos]==-1);
        assert (inv[value] == -1);
        perm[pos] = value;
        inv[value] = pos;
    }

    public int get(int pos) {
        return perm[pos];
    }

    public int getInverse(int pos) {
        return inv[pos];
    }



    public List<Integer> getPossible() {
        List<Integer> ans = new ArrayList<Integer>();
        for(int i=0;i<n;i++) {
            if(inv[i]==-1){
                ans.add(i);
            }
        }
        return ans;
    }

    public int getEmptyPos() {
        for(int i=0;i<n;i++) {
            if(perm[i]==-1) return i;
        }
        return -1;
    }

    public Permutation clone() {
        return new Permutation(this);
    }

    @Override
    public String toString() {
        return Arrays.toString(perm);
    }

    public Permutation getNext() {
        int k = -1;
        for(int i=n-2;i>=0;i--) {
            if(perm[i]<perm[i+1]) {
                k = i;
                break;
            }
        }
        if(k==-1) {
            return null;
        }

        int l = k+1;
        while(l!=n-1 && perm[l+1]>perm[k])l++;

        int[] newPerm = new int[n];
        for(int i=0;i<k;i++)newPerm[i] = perm[i];
        for(int i=k+1;i<n;i++) newPerm[i] = perm[k+n-i];
        newPerm[k] = perm[l];
        newPerm[k+n-l] = perm[k];

        return new Permutation(newPerm);
    }

    public static Permutation apply(Permutation p1, Permutation p2) {
        int n = p1.n;
        assert(p2.n == n);
        int[] perm = new int[n];
        for(int i=0;i<n;i++) {
            perm[i] = p1.get(p2.get(i));
        }
        return new Permutation(perm);
    }



    public static List<Permutation> getAllPermutations(int n) {
        List<Permutation> ans = new ArrayList<Permutation>();
        int[] p = new int[n];
        for(int i=0;i<n;i++)p[i]=i;
        Permutation perm = new Permutation(p);
        while(perm!=null) {
            ans.add(perm);
            perm = perm.getNext();
        }
        return ans;
    }

    public static void main(String[] args) {
        List<Permutation> perms = getAllPermutations(4);

        for(Permutation p : perms) {
            System.out.println(p.toString());
        }

    }

    public static int factorial(int n) {
        int ans=1;
        for(int i=2;i<=n;i++)ans*=i;
        return ans;
    }
}
