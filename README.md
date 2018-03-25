# Finite Group Enumerator.

This program explicitly enumerates all non-isomorphic finite groups of given order.
It works in reasonable time for orders up to 9.
Result can be seen in file groups.txt in this repository.

Groups are represented by their Cayley table. Elements are represented by 
integers 0, 1, .. n-1, where n i sorder of group. Identity element is
always represented by 0.

Enumerating algorithm is just somewhat clever bruteforce. It generates all
Cayley tables (in above mentioned form) which describe valid groups. 
Different Cayley tables can describe the isomorphic groups,
so than algorithm filters them, leaving only one table
for each class of isomorphic Groups.

Also this program provides tools for generating cyclic and symmetric groups,
checking group isomorphism, finding direct product of groups, working 
with permutations.

See also:

* http://oeis.org/wiki/Number_of_groups_of_order_n
* https://groupprops.subwiki.org/wiki/Number_of_groups_of_given_order

