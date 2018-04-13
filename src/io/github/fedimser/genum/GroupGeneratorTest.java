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
        List<FinGroup> correct = Collections.singletonList(FinGroupFactory.getCyclicGroup(1));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(1)));
    }

    @org.junit.Test
    public void test_order2() throws Exception {
        List<FinGroup> correct = Collections.singletonList(FinGroupFactory.getCyclicGroup(2));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(2)));
    }

    @org.junit.Test
    public void test_order3() throws Exception {
        List<FinGroup> correct = Collections.singletonList(FinGroupFactory.getCyclicGroup(3));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(3)));
    }

    @org.junit.Test
    public void test_order4_are_Z4_and_Z2xZ2() throws Exception {
        FinGroup z2 = FinGroupFactory.getCyclicGroup(2);
        FinGroup z4 = FinGroupFactory.getCyclicGroup(4);
        List<FinGroup> correct = Arrays.asList(z4, FinGroup.multiplyGroups(z2, z2));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(4)));
    }

    @org.junit.Test
    public void test_order5() throws Exception {
        List<FinGroup> correct = Collections.singletonList(FinGroupFactory.getCyclicGroup(5));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(5)));
    }

    @org.junit.Test
    public void test_order6_are_Z6_and_S3() throws Exception {
        List<FinGroup> correct = Arrays.asList(
                FinGroupFactory.getCyclicGroup(6),
                FinGroupFactory.getSymmetricGroup(3)
        );
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(6)));
    }

    @org.junit.Test
    public void test_order7() throws Exception {
        List<FinGroup> correct = Collections.singletonList(FinGroupFactory.getCyclicGroup(7));
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(7)));
    }

    @org.junit.Test
    public void test_order8() throws Exception {
        FinGroup z2 = FinGroupFactory.getCyclicGroup(2);
        FinGroup z4 = FinGroupFactory.getCyclicGroup(4);
        FinGroup z8 = FinGroupFactory.getCyclicGroup(8);
        FinGroup z2xz2 = FinGroup.multiplyGroups(z2, z2);
        FinGroup z2xz4 = FinGroup.multiplyGroups(z2, z4);
        FinGroup z2xz2xz2 = FinGroup.multiplyGroups(z2xz2, z2);
        FinGroup d4 = FinGroupFactory.getDihedralGroup(4);
        FinGroup q8 = FinGroupFactory.quaternionGroup();
        List<FinGroup> correct = Arrays.asList(z8, z2xz4, z2xz2xz2, d4, q8);
        assertTrue(IsoChecker.areListsIsomorhic(correct, gen.getAllGroups(8)));
    }

}