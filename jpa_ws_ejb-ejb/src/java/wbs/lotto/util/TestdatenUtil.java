package wbs.lotto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestdatenUtil {

    // Ein paar Konstanten, die man vielleicht mal braucht
    public static final Integer[] ARRAY1_49 = {
        1, 2, 3, 4, 5, 6, 7,
        8, 9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21,
        22, 23, 24, 25, 26, 27, 28,
        29, 30, 31, 32, 33, 34, 35,
        36, 37, 38, 39, 40, 41, 42,
        43, 44, 45, 46, 47, 48, 49
    };
    public static final List<Integer> LIST1_49 = new ArrayList<>(Arrays.asList(ARRAY1_49));
    public static final long LONG1_49 = 1125899906842622L;

    // Allerlei Konvertierungsmethoden
    public static long testdatenListToLong(List<Integer> list) {
        long result = 0L;
        for (int i : list) {
            result = result | (1L << i);
        }
        return result;
    }

    public static List<Integer> testdatenLongToList(long zahl) {

        List<Integer> list = new ArrayList<>();

        while (zahl != 0) {
            list.add(Long.numberOfTrailingZeros(zahl));
            zahl &= ~Long.lowestOneBit(zahl);
        }

        return list;
    }

    public static long testdatenArrayToLong(int[] array) {
        long result = 0L;
        for (int i : array) {
            result = result | (1L << i);
        }
        return result;
    }

    // Hier wird einer (Lotto-)Lostrommel eine Anzahl "Kugeln" (=Integers) entnommen
    // und die gezogenen Zahlen einer evtl. vorhandenen Teilziehung hinzugefügt
    public static long testdatenRandomTippAsLong(List<Integer> trommel, int anzahl, long teilZiehung) {
        Random rnd = new Random();
        // Neue Liste, damit die übergebene unangetastet bleibt
        List<Integer> ziehungsTrommel = new ArrayList<>(trommel);

        // Die übergebene "Teilziehung" hat evtl. schon Bits gesetzt
        // Wir fügen einfach die neu gezogenen Zahlen als Bits hinzu
        long result = teilZiehung;
        int kugelnInTrommel = ziehungsTrommel.size();
        for (int i = 0; i < anzahl; i++) {
            int index = rnd.nextInt(kugelnInTrommel);
            int zahl = ziehungsTrommel.remove(index);
            kugelnInTrommel--;
            result = result | (1L << zahl);
        }
        return result;
    }

    // Normale 6aus49 Ziehung
    public static long testdatenRandomTippAsLong() {
        return testdatenRandomTippAsLong(LIST1_49, 6, 0L);
    }

    /**
     * Es wird ein zufälliger Lottotipp generiert, der eine vorgegebene Anzahl
     * an Gewinnzahlen aus einer vorliegenden Ziehung enthält
     *
     * @param ziehung Eine vorliegende Ziehung als long (6 Bits gesetzt)
     * @param anzahlRichtige Die gewünsche Anzahl an richtigen Zahlen im Tipp
     * @return der generierte Tipp
     */
    public static long getWinTipp(long ziehung, int anzahlRichtige) {

        // 6 Richtige - wir sind schon fertig
        if (anzahlRichtige == 6) {
            return ziehung;
        }

        long teilziehung = 0L;

        // erstmal "tippen" wir aus der übergebenen Ziehung die gewünschte Anzahl richtige
        if (anzahlRichtige > 0) {
            // Als "Minitrommel" wird die Ziehung übergeben aus der wir 
            // "anzahlRichtige" ziehen
            teilziehung = testdatenRandomTippAsLong(testdatenLongToList(ziehung), anzahlRichtige, 0L);
        }

        // Nun müssen wir die restlichen Tippzahlen ziehen, die aber keine Gewinnzahlen mehr sein dürfen
        // Darum werden die "Ziehungskugeln" aus der Trommel entfernt.
        long trommel = LONG1_49 & ~ziehung;

        // Und nun ziehen wir die restlichen 6-anzahlRichtige Zahlen,
        // die garantiert Nieten sind
        //
        // Die Methode fügt die gezogenen Zahlen als Bits dem long-Wert teilziehung hinzu
        // Das Ziehungsergebnis wird direkt zurückgegeben
        return testdatenRandomTippAsLong(testdatenLongToList(trommel), 6 - anzahlRichtige, teilziehung);
    }

    /*
     * Zum Testen ist es ganz hilfreich, wenn man Tipps/Ziehungen
     * als String eingeben oder ausgeben kann
     * Dafür sind diese Hilfsmethoden gedacht
     */
    public static long testdatenStringAsLong(String value) {
        long zahlenAlsBits = 0L;
        int zahl;
        try {
            String[] sZahlen = value.split("\\s+");
            if (sZahlen.length == 6) {
                for (String sZahl : sZahlen) {
                    zahl = Integer.parseInt(sZahl);
                    if (zahl < 1 || zahl > 49) {
                        zahlenAlsBits = Long.MIN_VALUE;
                        break;
                    }
                    zahlenAlsBits = zahlenAlsBits | (1L << zahl);
                }
            } else {
                zahlenAlsBits = Long.MIN_VALUE;
            }
        } catch (RuntimeException e) {
            zahlenAlsBits = Long.MIN_VALUE;
        }
        return zahlenAlsBits;
    }

    public static String testdatenLongAsString(long value) {
        StringBuffer sb = new StringBuffer();

        while (value != 0) {
            sb.append(Long.numberOfTrailingZeros(value)).append(" ");
            value &= ~Long.lowestOneBit(value);
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {

        long ziehung = testdatenRandomTippAsLong();
        int richtige = 4;
        System.out.println("Start...");
        List<Long> list = new ArrayList<>();
        final long timeStart = System.nanoTime();
        ziehung = testdatenRandomTippAsLong();
        for (int i = 0; i < 5_000_000; i++) {
            //list.add(LottoUtil.randomTippAsLong());

            //list.add(testdatenRandomTippAsLong());
            list.add(getWinTipp(ziehung, richtige));
        }
        final long timeEnd = System.nanoTime();
        double seconds = (double) (timeEnd - timeStart) / 1000000000.0;

        System.out.println("... Ende.\nLaufzeit: " + (seconds) + " Sekunden");

        for (long l : list) {
            // System.out.println(Long.bitCount(l));
        }

    }
}
