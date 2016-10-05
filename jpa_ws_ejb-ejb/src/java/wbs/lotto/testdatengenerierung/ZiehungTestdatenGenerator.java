/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.testdatengenerierung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ejb.Stateless;
import wbs.lotto.util.TestdatenUtil;

/**
 *
 * @author gz1
 */
// skizze eines generators für testdaten, auf deren grundlage die korrektheit der quotenermittlung und 
// der jackpotverwaltung überprüft werden kann.
// die existenz passender stammdaten (kunden, spiele, gebuehren, gewinnklassen,...) wird vorausgesetzt
// Anzahl der ziehungen, an denen ein Lottoschein im Mittel teilnimmt
// (Varianten Mittwoch / Samstag /beides)
class Tipp {

    public long tipp6;
    public int losnummer;
}

@Stateless
public class ZiehungTestdatenGenerator implements ZiehungTestdatenGeneratorLocal {

    @Override
    public void generiereTestDatenFuerMehrereZiehungen(Date datumErsteZiehung, List<ZiehungGeneratorConfig> configList) {
        Date datum = datumErsteZiehung;
        for (ZiehungGeneratorConfig config : configList) {
            //generiereTestDatenFuerEineZiehung(datum, config);
            datum = naechstesZiehungsdatum(datum);
        }
    }

    private Date naechstesZiehungsdatum(Date datum) {
        return null;
    }

    static private Long getTipp(Integer gkl, long ziehung) {
        int anzahlRichtige = (int) Math.floor((14 - gkl) / 2);
        return TestdatenUtil.getWinTipp(ziehung, anzahlRichtige);
    }

    static private Long getNiete(long ziehung) {
        return TestdatenUtil.getWinTipp(ziehung, 0);
    }

    static private int getLosnummer(Integer gkl, int superzahl) {

        Random rnd = new Random();
        List<Integer> ziffern = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));

        int letzteZiffer;
        if (gkl % 2 == 1) {
            letzteZiffer = superzahl;
        } else {
            ziffern.remove(superzahl);
            letzteZiffer = ziffern.get(rnd.nextInt(9));
        }

        int result = rnd.nextInt(1000000) * 10 + letzteZiffer;
        return result;
    }

    // generierung von testdaten für eine ziehung
    //private void generiereTestDatenFuerEineZiehung(Date datum, ZiehungGeneratorConfig config) {
    static private List<Tipp> generiereTestDatenFuerEineZiehung() {
        int[] ziehungArray = {4, 15, 18, 21, 32, 45, 4};
        long ziehung = TestdatenUtil.testdatenArrayToLong(ziehungArray);
        int superzahl = 4;

        List<Tipp> tipps = new ArrayList<>();

        Map<Integer, Integer> anzahlInGewinnklasse = new HashMap<>();
        anzahlInGewinnklasse.put(1, 1);
        anzahlInGewinnklasse.put(2, 7);
        anzahlInGewinnklasse.put(3, 201);
        anzahlInGewinnklasse.put(4, 1948);
        anzahlInGewinnklasse.put(5, 4897);
        anzahlInGewinnklasse.put(6, 42663);
        anzahlInGewinnklasse.put(7, 80676);
        anzahlInGewinnklasse.put(8, 714150);
        anzahlInGewinnklasse.put(9, 575597);

        for (Map.Entry<Integer, Integer> entry : anzahlInGewinnklasse.entrySet()) {
            int gkl = entry.getKey();
            int anzahl = entry.getValue();
            for (int i = 0; i < anzahl; i++) {
                Tipp tipp = new Tipp();
                tipp.tipp6 = getTipp(gkl, ziehung);
                tipp.losnummer = getLosnummer(gkl, superzahl);
                tipps.add(tipp);
            }
        }
        return tipps;
    }

    static private List<Long[]> generiereNietenFuerEineZiehung() {
        int[] ziehungArray = {4, 15, 18, 21, 32, 45, 4};
        long ziehung = TestdatenUtil.testdatenArrayToLong(ziehungArray);
        int superzahl = 4;
        int anzahl = 4_647_873;
        List<Long[]> tipps = new ArrayList<>();
        //int anzahl = 44_647_873;

        for (int i = 0; i < anzahl; i++) {
            Long[] tipp = new Long[2];
            tipp[0] = getNiete(ziehung);
            tipp[1] = (long) getLosnummer(8, superzahl);
            tipps.add(tipp);
        }
        return tipps;
    }

    static private List<Tipp> generiereNietenFuerEineZiehung1() {
        List<Tipp> tipps = new ArrayList<>();
        return generiereNietenFuerEineZiehung1(tipps);
    }
    static private List<Tipp> generiereNietenFuerEineZiehung1(List<Tipp> tipps) {
        int[] ziehungArray = {4, 15, 18, 21, 32, 45, 4};
        Tipp tipp;
        long ziehung = TestdatenUtil.testdatenArrayToLong(ziehungArray);
        int superzahl = 4;
        int anzahl = 41_647_873;
        //int anzahl = 44_647_873;

        for (int i = 0; i < anzahl; i++) {
            tipp = new Tipp();
            tipp.tipp6 = getNiete(ziehung);
            tipp.losnummer = getLosnummer(8, superzahl);
            tipps.add(tipp);
        }
        return tipps;
    }

    static private List<Long> generiereNietenFuerEineZiehung0() {
        int[] ziehungArray = {4, 15, 18, 21, 32, 45, 4};
        long ziehung = TestdatenUtil.testdatenArrayToLong(ziehungArray);
        int superzahl = 4;
        
        int anzahl = 4_647_873;
        List<Long> tipps = new ArrayList<>(anzahl);
        //int anzahl = 44_647_873;
        for (int i = 0; i < anzahl; i++) {
            tipps.add(getNiete(ziehung));
        }
        return tipps;
    }

    public static void main(String[] args) {

        System.out.println("Start...");
        List<Tipp> tipps = generiereTestDatenFuerEineZiehung();
        System.out.println("Fertig!");
        System.out.println("Anzahl Gewinne: " + tipps.size());
        System.out.println("Start...");
        final long timeStart = System.nanoTime();
        //ist<Long[]> nieten = generiereNietenFuerEineZiehung();
        //     List<Long> nieten = generiereNietenFuerEineZiehung0();
        List<Tipp> nieten = generiereNietenFuerEineZiehung1(tipps);
        System.out.println("Fertig!");
        System.out.println("Anzahl Gesamt: " + tipps.size());
        Collections.shuffle(tipps);
        final long timeEnd = System.nanoTime();
        double seconds = (double) (timeEnd - timeStart) / 1000000000.0;

        System.out.println("... Ende.\nLaufzeit: " + (seconds) + " Sekunden");
        

    }

}
