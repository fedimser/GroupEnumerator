package io.github.fedimser.finfield;

import com.google.common.collect.ImmutableList;
import io.github.fedimser.genum.FinGroup;
import org.junit.Test;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static io.github.fedimser.finfield.FiniteField.canCreate;
import static io.github.fedimser.finfield.FiniteField.create;
import static io.github.fedimser.genum.FinGroup.multiplyGroups;
import static io.github.fedimser.genum.FinGroupFactory.getCyclicGroup;
import static io.github.fedimser.genum.IsoChecker.areIsomorhic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FiniteFieldTest {
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
        for (int i = 1; i < 200; i++) {
            if (!canCreate(i)) continue;
            assertTrue(areIsomorhic(create(i).getMultiplicativeGroup(), getCyclicGroup(i - 1)));
        }
    }

    private void assertFieldElements(int cardinality, String... expectedElements) {
        ImmutableList<String> actualElements = create(cardinality)
                .getElements()
                .stream()
                .map(Polynomial::toString)
                .collect(toImmutableList());
        assertEquals(ImmutableList.copyOf(expectedElements), actualElements);
    }

    @Test
    public void testElements_F2() {
        assertFieldElements(2, "0", "1");
    }

    @Test
    public void testElements_F3() {
        assertFieldElements(3, "0", "1", "2");
    }

    @Test
    public void testElements_F4() {
        assertFieldElements(4, "0", "1", "x", "x+1");
    }

    @Test
    public void testElements_F8() {
        assertFieldElements(8, "0", "1", "x", "x+1", "x^2", "x^2+1", "x^2+x", "x^2+x+1");
    }

    @Test
    public void testElements_F9() {
        assertFieldElements(9, "0", "1", "2", "x", "x+1", "x+2", "2*x", "2*x+1", "2*x+2");
    }

    @Test
    public void testElements_F28() {
        FiniteField field =  create(128);
        assertEquals(field.getElements().get(67).toString(), "x^6+x+1");
    }
}
