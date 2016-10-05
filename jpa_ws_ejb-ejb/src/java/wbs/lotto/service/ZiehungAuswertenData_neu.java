/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import wbs.lotto.domain.Gebuehr;
import wbs.lotto.domain.Ziehung;
import wbs.lotto.jpafeatures.JPQLQueryLocal;
import wbs.lotto.persistence.ZiehungFacadeLocal;
import wbs.lotto.util.LottoUtil;

/**
 *
 * @author Bernd
 */
@Startup
@Singleton
public class ZiehungAuswertenData_neu implements ZiehungAuswertenDataLocal_neu {

    private long ziehungId;
    private Ziehung ziehung;
    private Gebuehr gebuehr;
    private long anzahlScheine;
    private long anzahlScheineBearbeitet;
    private long anzahlTipps;
    private long anzahlSpiel77;
    private long anzahlSuper6;
    private String lottoZahlenAsString;
    private int[] gklLotto;
    private int[] gklSpiel77;
    private int[] gklSuper6;
    private long[] quoteGklLotto;
    private long[] quoteGklSpiel77;
    private long[] quoteGklSuper6;
    private String auswertungsMeldung;
    private double auswertungsstartzeit;
    private double auswertungslaufzeit;
    private Future<String> auswertungsFuture;
    private int auswertungsstatus;

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    @EJB
    private JPQLQueryLocal jPQLQueryLocal;
    @EJB
    private ZiehungFacadeLocal ziehungFacadeLocal;

    protected ZiehungAuswertenData_neu() {

    }

    public void reset() {
        anzahlScheine = 0;
        anzahlScheineBearbeitet = 0;
        anzahlTipps = 0;
        anzahlSpiel77 = 0;
        anzahlSuper6 = 0;
        gklLotto = new int[10];
        gklSpiel77 = new int[8];
        gklSuper6 = new int[7];
        quoteGklLotto = new long[10];
        quoteGklSpiel77 = new long[8];
        quoteGklSuper6 = new long[7];
        auswertungsstartzeit = 0.0;
        auswertungslaufzeit = 0.0;
        auswertungsstatus = 0;
        if (ziehung != null) {
            ziehung.setEinsatzLotto(BigInteger.ZERO);
            ziehung.setEinsatzSpiel77(BigInteger.ZERO);
            ziehung.setEinsatzSuper6(BigInteger.ZERO);
        }
    }

    public Ziehung getZiehung() {
        return ziehung;
    }

    public void initZiehung(Ziehung ziehung) {
        reset();
        if (ziehung != null) {
            setAnzahlScheine(getAnzahlScheineFromZiehung(ziehung, false));
            setAnzahlScheineBearbeitet(getAnzahlScheineFromZiehung(ziehung, true));
            setAnzahlTipps(getAnzahlTippsFromZiehung(ziehung));
            setAnzahlSpiel77(getAnzahlSpiel77FromZiehung(ziehung));
            setAnzahlSuper6(getAnzahlSuper6FromZiehung(ziehung));

            this.gebuehr = jPQLQueryLocal.gebuehr(ziehung.getZiehungsdatum());
            BigInteger einsatzLotto = BigInteger.valueOf(anzahlTipps).multiply(BigInteger.valueOf(gebuehr.getEinsatzProTipp()));
            ziehung.setEinsatzLotto(einsatzLotto);

            BigInteger einsatzSpiel77 = BigInteger.valueOf(anzahlSpiel77).multiply(BigInteger.valueOf(gebuehr.getEinsatzSpiel77()));
            ziehung.setEinsatzSpiel77(einsatzSpiel77);

            BigInteger einsatzSuper6 = BigInteger.valueOf(anzahlSuper6).multiply(BigInteger.valueOf(gebuehr.getEinsatzSuper6()));
            ziehung.setEinsatzSuper6(einsatzSuper6);
            ziehungFacadeLocal.edit(ziehung);
        }
    }

    public void initZiehung() {
        initZiehung(this.ziehung);
    }

    public void setZiehung(Ziehung ziehung) {
        initZiehung(ziehung);
        this.ziehung = ziehung;

    }

    public long getAnzahlScheine() {
        return anzahlScheine;
    }

    public void setAnzahlScheine(long anzahlScheine) {
        this.anzahlScheine = anzahlScheine;
    }

    public long getAnzahlScheineFromZiehung(Ziehung ziehung, boolean nurBearbeitete) {

        String sql;
        /*
        sql = "SELECT COUNT(l) FROM Lottoschein l "
                + "JOIN Lottoscheinziehung lz ON lz.lottoscheinId = l "
                + "JOIN Ziehung z ON lz.ziehungId = z "
                + "WHERE z.ziehungId = " + ziehung.getZiehungId();
         */

        sql = "SELECT COUNT(lz) FROM Lottoscheinziehung lz "
                + "JOIN Ziehung z ON lz.ziehungId = z "
                + "WHERE z.ziehungId = " + ziehung.getZiehungId();
        if (nurBearbeitete) {
            sql += " AND lz.isAbgeschlossen IS NOT NULL";
        }
        Query q = em.createQuery(sql);
        Long result = (Long) q.getSingleResult();
        System.out.println(q.toString());
        return result;

    }

    public long getAnzahlScheineFromZiehung() {
        return getAnzahlScheineFromZiehung(this.ziehung, false);
    }

    public long getAnzahlScheineBearbeitet() {
        return anzahlScheineBearbeitet;
    }

    public long getAnzahlScheineBearbeitetFromZiehung() {
        return getAnzahlScheineFromZiehung(this.ziehung, true);
    }

    public void setAnzahlScheineBearbeitet(long anzahlScheineBearbeitet) {
        this.anzahlScheineBearbeitet = anzahlScheineBearbeitet;
    }

    public long getAnzahlTippsFromZiehung(Ziehung ziehung) {

        String sql;

        sql = "SELECT SUM(LENGTH(tipps) / 8) FROM lottoschein l "
                + "JOIN lottoscheinziehung lz ON lz.lottoscheinId = l.lottoscheinId "
                + "JOIN ziehung z ON lz.ziehungId = z.ziehungId "
                + "WHERE z.ziehungId = " + ziehung.getZiehungId();
        Query q = em.createNativeQuery(sql);
        BigDecimal result = (BigDecimal) q.getSingleResult();

        return result.toBigInteger().longValue();

    }

    public long getAnzahlSpiel77FromZiehung(Ziehung ziehung) {

        String sql;

        sql = "SELECT COUNT(l) FROM Lottoschein l "
                + "JOIN Lottoscheinziehung lz ON lz.lottoscheinId = l "
                + "JOIN Ziehung z ON lz.ziehungId = z "
                + "WHERE z.ziehungId = " + ziehung.getZiehungId() + " "
                + "AND l.isSpiel77 = true";
        Query q = em.createQuery(sql);
        Long result = (Long) q.getSingleResult();
        return result;

    }

    public long getAnzahlSpiel77FromZiehung() {
        return getAnzahlSpiel77FromZiehung(this.ziehung);
    }

    public long getAnzahlSuper6FromZiehung(Ziehung ziehung) {

        String sql;

        sql = "SELECT COUNT(l) FROM Lottoschein l "
                + "JOIN Lottoscheinziehung lz ON lz.lottoscheinId = l "
                + "JOIN Ziehung z ON lz.ziehungId = z "
                + "WHERE z.ziehungId = " + ziehung.getZiehungId() + " "
                + "AND l.isSuper6 = true";
        Query q = em.createQuery(sql);
        Long result = (Long) q.getSingleResult();
        return result;

    }

    public long getAnzahlSuper6FromZiehung() {
        return getAnzahlSuper6FromZiehung(this.ziehung);
    }

    public long getAnzahlTippsFromZiehung() {
        return getAnzahlTippsFromZiehung(this.ziehung);
    }

    public long getAnzahlTipps() {
        return anzahlTipps;
    }

    public void setAnzahlTipps(long anzahlTipps) {
        this.anzahlTipps = anzahlTipps;
    }

    public long getAnzahlSpiel77() {
        return anzahlSpiel77;
    }

    public void setAnzahlSpiel77(long anzahlSpiel77) {
        this.anzahlSpiel77 = anzahlSpiel77;
    }

    public long getAnzahlSuper6() {
        return anzahlSuper6;
    }

    public void setAnzahlSuper6(long anzahlSuper6) {
        this.anzahlSuper6 = anzahlSuper6;
    }

    public int[] getGklLotto() {
        return gklLotto;
    }

    public void setGklLotto(int[] gklLotto) {
        this.gklLotto = gklLotto;
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

    public int[] getGklSpiel77() {
        return gklSpiel77;
    }

    public void setGklSpiel77(int[] gklSpiel77) {
        this.gklSpiel77 = gklSpiel77;

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

    public int[] getGklSuper6() {
        return gklSuper6;
    }

    public void setGklSuper6(int[] gklSuper6) {
        this.gklSuper6 = gklSuper6;
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

    public double getAuswertungslaufzeit() {
        return auswertungslaufzeit;
    }

    public void setAuswertungslaufzeit(double auswertungslaufzeit) {
        this.auswertungslaufzeit = auswertungslaufzeit;
    }

    public int getAuswertungsstatus() {
        return auswertungsstatus;
    }

    public void setAuswertungsstatus(int auswertungsstatus) {
        this.auswertungsstatus = auswertungsstatus;
    }

    public void setLottoZahlenAsString(String zahlenAsString) {
        this.lottoZahlenAsString = getLottoZahlenAsString();

    }

    public String getLottoZahlenAsString() {
        return LottoUtil.tippsAsString2(ziehung.getZahlenAlsBits().longValue());
    }

    public double getAuswertungsstartzeit() {
        return auswertungsstartzeit;
    }

    public void setAuswertungsstartzeit(double auswertungsstartzeit) {
        this.auswertungsstartzeit = auswertungsstartzeit;
    }

    public Future<String> getAuswertungsFuture() {
        return auswertungsFuture;
    }

    public void setAuswertungsFuture(Future<String> auswertungsFuture) {
        this.auswertungsFuture = auswertungsFuture;
    }

    public long getZiehungId() {
        return ziehungId;
    }

    public void setZiehungId(long ziehungId) {
        //if (this.ziehungId != ziehungId) {
        Ziehung ziehung = ziehungFacadeLocal.find(ziehungId);
        this.setZiehung(ziehung);
        this.ziehungId = ziehung.getZiehungId();
        System.out.println("Länge JackpotList:" + ziehung.getJackpotList().size()
    

    );
        //}
    }

    public String getAuswertungsMeldung() {
        return auswertungsMeldung;
    }

    public void setAuswertungsMeldung(String auswertungsMeldung) {
        this.auswertungsMeldung = auswertungsMeldung;
    }

    @PostConstruct
    public void init() {
        System.out.println("Init...");
        setZiehungId(14L);

    }

}
