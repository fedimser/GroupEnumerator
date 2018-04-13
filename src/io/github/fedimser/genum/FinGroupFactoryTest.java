package io.github.fedimser.genum;

import static org.junit.Assert.*;

public class FinGroupFactoryTest {

    @org.junit.Test
    public void test_S5_has_order_120() throws Exception {
        FinGroup s5 = FinGroupFactory.getSymmetricGroup(5);
        assertEquals(120, s5.getOrder());
    }

    @org.junit.Test
    public void test_Z6_is_Z3xZ2() throws Exception {
        FinGroup z6 = FinGroupFactory.getCyclicGroup(6);
        FinGroup z2 = FinGroupFactory.getCyclicGroup(2);
        FinGroup z3 = FinGroupFactory.getCyclicGroup(3);
        FinGroup z2xz3 = FinGroup.multiplyGroups(z2, z3);
        assertTrue(IsoChecker.areIsomorhic(z6, z2xz3));
    }

    @org.junit.Test
    public void test_Z2_is_S2() throws Exception {
        FinGroup z2 = FinGroupFactory.getCyclicGroup(2);
        FinGroup s2 = FinGroupFactory.getSymmetricGroup(2);
        assertTrue(IsoChecker.areIsomorhic(z2, s2));
    }

    @org.junit.Test
    public void test_D2_is_Z2xZ2() throws Exception {
        FinGroup d2 = FinGroupFactory.getDihedralGroup(2);
        FinGroup z2 = FinGroupFactory.getCyclicGroup(2);
        FinGroup z2xz2 = FinGroup.multiplyGroups(z2, z2);
        assertTrue(IsoChecker.areIsomorhic(d2, z2xz2));
    }

    @org.junit.Test
    public void test_D3_is_S3() throws Exception {
        FinGroup d3 = FinGroupFactory.getDihedralGroup(3);
        FinGroup s3 = FinGroupFactory.getSymmetricGroup(3);
        assertTrue(IsoChecker.areIsomorhic(d3, s3));
    }

}