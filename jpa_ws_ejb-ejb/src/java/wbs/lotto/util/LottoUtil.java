/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import org.kohsuke.rngom.digested.Main;

public class LottoUtil {

    public static long randomTippAsLong() {

        long result = 0L;
        Set<Integer> lottozahlen = new HashSet<>();
        while (lottozahlen.size() < 6) {
            lottozahlen.add(new Random().nextInt(49) + 1);
        }
        for (int zahl : lottozahlen) {
            result = result | (1L << zahl);
        }
        return result;
    }

    public static byte[] randomTippsAsByteArray(int anzahl) {
        long[] tipps = new long[anzahl];

        for (int i = 0; i < anzahl; i++) {
            tipps[i] = randomTippAsLong();
        }

        return ByteLongConverterBB.longToByte(tipps);
    }

    public static String tippsAsString(long tipps) {
        StringBuffer sb = new StringBuffer();

        while (tipps != 0) {
            sb.append("<span class=\"badge\">").append(Long.numberOfTrailingZeros(tipps)).append("</span> ");
            //sb.append(Long.numberOfTrailingZeros(tipps)).append(";");
            tipps &= ~Long.lowestOneBit(tipps);
        }
        return sb.toString().trim();
    }

    public static String tippsAsString2(long tipps) {
        StringBuffer sb = new StringBuffer();

        while (tipps != 0) {
            //sb.append(Long.numberOfTrailingZeros(tipps));
            sb.append(Long.numberOfTrailingZeros(tipps)).append(" ");
            tipps &= ~Long.lowestOneBit(tipps);
        }
        return sb.toString().trim();
    }

    public static int gkl6Aus49(long ziehungsZahlenAsBits, long tippAlsBits, boolean hasMatchingSuperzahl) {

        int richtige = Long.bitCount(ziehungsZahlenAsBits & tippAlsBits);
        int s = hasMatchingSuperzahl ? 1 : 0;

        int g = 14 - richtige * 2 - s;

        return (g > 9) ? 0 : g;

    }

    public static int gklSuper6(int ziehungSuper6, int losNummer) {
        int gkl = 7;
        int teiler = 10;
        for (; gkl > 1; gkl--) {
            if (ziehungSuper6 % teiler == losNummer % teiler) {
                teiler *= 10;
            } else {
                break;
            }
        }
        return gkl > 6 ? 0 : gkl;
    }

    public static int gklSpiel77(int ziehungSpiel77, int losNummer) {
        int gkl = 8;
        int teiler = 10;
        for (; gkl > 1; gkl--) {
            if (ziehungSpiel77 % teiler == losNummer % teiler) {
                teiler *= 10;
            } else {
                break;
            }
        }

        return gkl > 7 ? 0 : gkl;
    }

}
