package io.github.fedimser.finfield;

import io.github.fedimser.genum.FinGroup;
import org.junit.Test;

import static io.github.fedimser.finfield.FiniteField.canCreate;
import static io.github.fedimser.finfield.FiniteField.create;
import static io.github.fedimser.genum.FinGroup.multiplyGroups;
import static io.github.fedimser.genum.FinGroupFactory.getCyclicGroup;
import static io.github.fedimser.genum.IsoChecker.areIsomorhic;
import static org.junit.Assert.assertTrue;

public class FinGroupTest {
    private static final FinGroup C2 = getCyclicGroup(2);
    private static final FinGroup C3 = getCyclicGroup(3);
    private static final FinGroup C5 = getCyclicGroup(5);

    @Test
    public void testAdditiveGroups() {
        assertTrue(areIsomorhic(create(2).getAdditiveGroup(), C2));
        assertTrue(areIsomorhic(create(3).getAdditiveGroup(), getCyclicGroup(3)));
        assertTrue(areIsomorhic(create(4).getAdditiveGroup(), multiplyGroups(C2, C2)));
        assertTrue(areIsomorhic(create(5).getAdditiveGroup(), C5));
        assertTrue(areIsomorhic(create(7).getAdditiveGroup(), getCyclicGroup(7)));
        assertTrue(areIsomorhic(create(8).getAdditiveGroup(), multiplyGroups(C2, C2, C2)));
        assertTrue(areIsomorhic(create(9).getAdditiveGroup(), multiplyGroups(C3, C3)));
        assertTrue(areIsomorhic(create(11).getAdditiveGroup(), getCyclicGroup(11)));
        assertTrue(areIsomorhic(create(27).getAdditiveGroup(), multiplyGroups(C3, C3, C3)));
        assertTrue(areIsomorhic(create(125).getAdditiveGroup(), multiplyGroups(C5, C5, C5)));
        assertTrue(areIsomorhic(create(128).getAdditiveGroup(), multiplyGroups(C2, C2, C2, C2, C2,
                C2, C2)));
    }

    @Test
    public void testMultiplicativeGroups() {
        for(int i=1;i<200;i++) {
            if(!canCreate(i)) continue;
            assertTrue(areIsomorhic(create(i).getMultiplicativeGroup(), getCyclicGroup(i-1)));
        }
    }
}
