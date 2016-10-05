/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Master
 */
public class LottoUtilTest {

    public LottoUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }

    @Before
    public void setUp() {
        System.out.println("setUp()");
    }

    @After
    public void tearDown() {
        System.out.println("tearDown()");
    }

    /**
     * Test of randomTippAsLong method, of class LottoUtil.
     */
    @Test
    public void testRandomTippAsLong() {
        System.out.println("randomTippAsLong");
        for (int i = 0; i < 100; i++) {
            long tippAsBits = LottoUtil.randomTippAsLong();
            System.out.println(LottoUtil.tippsAsString(tippAsBits));

        }
        long tippAsBits = LottoUtil.randomTippAsLong();

        long highestOneBit = Long.highestOneBit(tippAsBits);
        long lowestOneBit = Long.lowestOneBit(tippAsBits);
        int bitCount = Long.bitCount(tippAsBits);
        assertTrue(bitCount == 6 && highestOneBit < (1L << 50) && lowestOneBit > 0L);
    }

    /**
     * Test of randomTippsAsByteArray method, of class LottoUtil.
     */
    //@Test
    public void testRandomTippsAsByteArray() {
        System.out.println("randomTippsAsByteArray");
        int anzahl = 5;
        byte[] expResult = null;
        byte[] result = LottoUtil.randomTippsAsByteArray(anzahl);
        if (Math.random() < 0.5) {
            throw new RuntimeException();
        }
        assertTrue(result != null);
    }

    /**
     * Test of tippsAsString method, of class LottoUtil.
     */
    public void testTippsAsString() {
        System.out.println("tippsAsString");
        long tipps = 0L;
        String expResult = "";
        String result = LottoUtil.tippsAsString(tipps);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gkl6Aus49 method, of class LottoUtil.
     */
    public long getLongFromIntArray(int[] zahlen) {
        long result = 0;
        for (int i : zahlen) {
            result = result | (1L << i);
        }
        return result;
    }

    @Test
    public void testGkl6Aus49() {
        System.out.println("gkl6Aus49");

        boolean hasMatchingSuperzahl = true;
        
        int richtige = 4;
        for (int i = 0; i < 100;i++) {
            
            long ziehung=LottoUtil.randomTippAsLong();
            long tipp = TestdatenUtil.getWinTipp(ziehung, richtige);
            int result = LottoUtil.gkl6Aus49(ziehung, tipp, hasMatchingSuperzahl);
            int expResult = 5;
            assertEquals(expResult, result);

        }

    }

    /**
     * Test of gklSuper6 method, of class LottoUtil.
     */
    //@Test
    public void testGklSuper6() {
        System.out.println("gklSuper6");
        int ziehungSuper6 = 0;
        int losNummer = 0;
        int expResult = 0;
        int result = LottoUtil.gklSuper6(ziehungSuper6, losNummer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gklSpiel77 method, of class LottoUtil.
     */
    //@Test
    public void testGklSpiel77() {
        System.out.println("gklSpiel77");
        int ziehungSpiel77 = 0;
        int losNummer = 0;
        int expResult = 0;
        int result = LottoUtil.gklSpiel77(ziehungSpiel77, losNummer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
