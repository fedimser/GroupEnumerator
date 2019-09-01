package io.github.fedimser.finfield;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testPow() {
        assertEquals(2L, Utils.pow(2L,1L));
        assertEquals(1024L, Utils.pow(2L,10L));
        assertEquals(81L, Utils.pow(3L,4L));
    }
}
