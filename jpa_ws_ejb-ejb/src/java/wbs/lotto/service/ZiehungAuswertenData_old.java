/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.service;

import wbs.lotto.domain.Ziehung;

/**
 *
 * @author Master
 */
public class ZiehungAuswertenData_old {
    
    public static final int UNDEFINIERT = 0; 
    public static final int BEREIT_ZUR_AUSWERTUNG = 1; 
    public static final int AUSWERTUNG_LAEUFT = 2; 
    public static final int AUSWERTUNG_FERTIG = 3; 
    public static final int FEHLER = 9; 

    private Ziehung ziehung;
    private int anzahlScheine;
    private int anzahlTipps;
    private int[] gklLotto;
    private int[] gklSpiel77;
    private int[] gklSuper6;
    private long[] quoteGklLotto;
    private long[] quoteGklSpiel77;
    private long[] quoteGklSuper6;
    private double laufzeit;
    private int status;

    public ZiehungAuswertenData_old() {
    }

    public Ziehung getZiehung() {
        return ziehung;
    }

    public void setZiehung(Ziehung ziehung) {
        this.ziehung = ziehung;
    }


    public void addgklLotto(int[] gklLottoAddition) {
        if (gklLotto == null) {
            this.gklLotto = gklLottoAddition;
        } else {
            for (int i = 0; i < gklLotto.length; i++) {
                this.gklLotto[i] += gklLottoAddition[i];
            }
        }
    }

    public void addgklSpiel77(int[] gklSpiel77Addition) {
        if (gklSpiel77 == null) {
            this.gklSpiel77 = gklSpiel77Addition;
        } else {
            for (int i = 0; i < gklSpiel77.length; i++) {
                this.gklSpiel77[i] += gklSpiel77Addition[i];
            }
        }
    }

    public void addgklSuper6(int[] gklSuper6Addition) {
        if (gklSuper6 == null) {
            this.gklSuper6 = gklSuper6Addition;
        } else {
            for (int i = 0; i < gklSuper6.length; i++) {
                this.gklSuper6[i] += gklSuper6Addition[i];
            }
        }
    }

    public void setAnzahlScheine(int anzahlScheine) {
        this.anzahlScheine = anzahlScheine;
    }

    public int getAnzahlTipps() {
        return anzahlTipps;
    }

    public void addAnzahlTipps(int anzahlTipps) {
        this.anzahlTipps += anzahlTipps;
    }

    public void addAnzahlScheine(int anzahlScheine) {
        this.anzahlScheine += anzahlScheine;
    }

    public int getAnzahlScheine() {
        return anzahlScheine;
    }
    

    public void setAnzahlTipps(int anzahlTipps) {
        this.anzahlTipps = anzahlTipps;
    }

    public int[] getGklLotto() {
        return gklLotto;
    }

    public void setGklLotto(int[] gklLotto) {
        this.gklLotto = gklLotto;
    }

    public int[] getGklSpiel77() {
        return gklSpiel77;
    }

    public void setGklSpiel77(int[] gklSpiel77) {
        this.gklSpiel77 = gklSpiel77;
    }

    public double getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(double laufzeit) {
        this.laufzeit = laufzeit;
    }

    public int[] getGklSuper6() {
        return gklSuper6;
    }

    public void setGklSuper6(int[] gklSuper6) {
        this.gklSuper6 = gklSuper6;
    }

    public long[] getQuoteGklLotto() {
        return quoteGklLotto;
    }

    public void setQuoteGklLotto(long[] quoteGklLotto) {
        this.quoteGklLotto = quoteGklLotto;
    }

    public long[] getQuoteGklSpiel77() {
        return quoteGklSpiel77;
    }

    public void setQuoteGklSpiel77(long[] quoteGklSpiel77) {
        this.quoteGklSpiel77 = quoteGklSpiel77;
    }

    public long[] getQuoteGklSuper6() {
        return quoteGklSuper6;
    }

    public void setQuoteGklSuper6(long[] quoteGklSuper6) {
        this.quoteGklSuper6 = quoteGklSuper6;
    }
    
    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
