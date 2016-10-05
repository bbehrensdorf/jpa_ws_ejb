/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.testdatengenerierung;

import java.util.Date;

/**
 *
 * @author gz1
 */

// konfiguration der testdatengenerierung für eine ziehung
// das meiste ausgelesen aus testdatengenerator.xml

public class ZiehungGeneratorConfig {
    
    private Date ziehungsdatum;
    
    private long zahlenAlsBits;
    private int superzahl;
    private int spiel77;
    private int super6;
    
    // alle zahlen sind absolut
    private int anzahlLottoscheine;
    private int anzahlTipps;
    private int anzahlSpiel77;
    private int anzahlSuper6;
    
    // absolute anzahlen in den gewinnklassen von 6 aus 49 (1-9)
    // index 0 bleibt unbesetzt
    private int[] gkl6Aus49;
    // absolute anzahlen in den gewinnklassen von spiel77 (1-7)
    // index 0 bleibt unbesetzt
    private int[] gklSpiel77;
    // absolute anzahlen in den gewinnklassen von super6 (1-6)
    // index 0 bleibt unbesetzt
    private int[] gklSuper6;
    
}
