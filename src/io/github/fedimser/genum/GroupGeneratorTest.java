package io.github.fedimser.genum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GroupGeneratorTest {

    private GroupGenerator gen = new GroupGenerator();

    @org.junit.Test
    public void tes_order1() throws Exception {
        List<FinGroup> correct = Collections.singletonList(gen.getCyclicGroup(1));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(1)));
    }

    @org.junit.Test
    public void test_order2() throws Exception {
        List<FinGroup> correct = Collections.singletonList(gen.getCyclicGroup(2));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(2)));
    }

    @org.junit.Test
    public void test_order3() throws Exception {
        List<FinGroup> correct = Collections.singletonList(gen.getCyclicGroup(3));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(3)));
    }

    @org.junit.Test
    public void test_order4_are_Z4_and_Z2xZ2() throws Exception {
        List<FinGroup> correct = Arrays.asList(
                gen.getCyclicGroup(4),
                FinGroup.multiplyGroups(gen.getCyclicGroup(2), gen.getCyclicGroup(2))
        );
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(4)));
    }

    @org.junit.Test
    public void test_order5() throws Exception {
        List<FinGroup> correct = Collections.singletonList(gen.getCyclicGroup(5));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(5)));
    }

    @org.junit.Test
    public void test_order6_are_Z6_and_S3() throws Exception {
        List<FinGroup> correct = Arrays.asList(
                gen.getCyclicGroup(6),
                gen.getSymmetricGroup(3)
        );
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(6)));
    }

    @org.junit.Test
    public void test_order7() throws Exception {
        List<FinGroup> correct = Collections.singletonList(gen.getCyclicGroup(7));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(7)));
    }

    @org.junit.Test
    public void test_Z6_is_Z3xZ2() throws Exception {
        FinGroup g1 = gen.getCyclicGroup(6);
        FinGroup g2 = FinGroup.multiplyGroups(gen.getCyclicGroup(2), gen.getCyclicGroup(3));
        assertTrue(IsoChecker.areIsomorhic(g1,g2));
    }

    @org.junit.Test
    public void test_Z2_is_S2() throws Exception {
        FinGroup g1 = gen.getCyclicGroup(2);
        FinGroup g2 = gen.getSymmetricGroup(2);
        assertTrue(IsoChecker.areIsomorhic(g1,g2));
    }
}