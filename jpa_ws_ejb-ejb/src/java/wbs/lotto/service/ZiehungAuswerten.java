package wbs.lotto.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import wbs.lotto.domain.Gebuehr;
import wbs.lotto.domain.Gewinnklasse;
import wbs.lotto.domain.Gewinnklasseziehungquote;
import wbs.lotto.domain.Jackpot;
import wbs.lotto.domain.Lottoschein;
import wbs.lotto.domain.Lottoscheinziehung;
import wbs.lotto.domain.Lottoscheinziehung6aus49;
import wbs.lotto.domain.Ziehung;
import wbs.lotto.jpafeatures.JPQLQueryLocal;
import wbs.lotto.jpafeatures.NamedQueryLocal;
import wbs.lotto.persistence.GebuehrFacadeLocal;
import wbs.lotto.persistence.GewinnklasseziehungquoteFacadeLocal;
import wbs.lotto.persistence.JackpotFacadeLocal;
import wbs.lotto.persistence.LottoscheinFacadeLocal;
import wbs.lotto.persistence.Lottoscheinziehung6aus49FacadeLocal;
import wbs.lotto.persistence.LottoscheinziehungFacadeLocal;
import wbs.lotto.persistence.ZiehungFacadeLocal;
import wbs.lotto.util.ByteLongConverter;
import wbs.lotto.util.LottoDatumUtil;
import wbs.lotto.util.LottoUtil;

@Stateless
@PermitAll
//@TransactionManagement(TransactionManagementType.BEAN)
public class ZiehungAuswerten implements ZiehungAuswertenLocal {

    private final int configMaxZiehungJackZwangsausschuettung = 12;
    private final long configSpielIdLotto = 1;
    private final long configSpielIdSpiel77 = 2;
    private final long configSpielIdSuper6 = 3;
    private final double configLottoFaktor = 0.5;

    private Map<Long, Map<Integer, Gewinnklasse>> gewinnKlassen;
    private Ziehung ziehung;
    private Long ziehungsZahlen;
    private Gebuehr gebuehr = new Gebuehr();

    @Resource(lookup = "lotto_jndi")
    private DataSource ds;

    @EJB
    private LottoscheinFacadeLocal lottoscheinFacadeLocal;
    @EJB
    private ZiehungFacadeLocal ziehungFacadeLocal;
    @EJB
    private NamedQueryLocal namedQueryLocal;

    @EJB
    private JPQLQueryLocal jPQLQueryLocal;

    @EJB
    private LottoscheinziehungFacadeLocal lottoscheinziehungFacadeLocal;
    @EJB
    private Lottoscheinziehung6aus49FacadeLocal lottoscheinziehungLottoFacadeLocal;
    @EJB
    private GebuehrFacadeLocal gebuehrFacadeLocal;
    @EJB
    private GewinnklasseziehungquoteFacadeLocal gewinnklasseziehungquoteFacadeLocal;
    @EJB
    private JackpotFacadeLocal jackpotFacadeLocal;

    @EJB
    private ZiehungAuswertenDataLocal_neu ziehungAuswertenData_neu;

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    ;
    
    
    // @Resource
    //private UserTransaction userTransaction;
    @Override
    //@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    //@Asynchronous
    public void mainLoop(long ziehungId) {

        //ziehung = ziehungFacadeLocal.find(ziehungId);
        ziehung = ziehungAuswertenData_neu.getZiehung();
        if (ziehung.getEinsatzLotto() == null) {
            ziehung.setEinsatzLotto(BigInteger.ZERO);
        }
        if (ziehung.getEinsatzSpiel77() == null) {
            ziehung.setEinsatzSpiel77(BigInteger.ZERO);
        }
        if (ziehung.getEinsatzSuper6() == null) {
            ziehung.setEinsatzSuper6(BigInteger.ZERO);
        }
        ziehungsZahlen = ziehung.getZahlenAlsBits().longValue();
        Date ziehungsdatum = ziehung.getZiehungsdatum();

        // TODO: mit geeignetem Query holen!!
        gebuehr = gebuehrFacadeLocal.find(1L);

        gewinnKlassen = getGewinnKlassen(ziehungsdatum);

        // Auswertung beginnt
        //try {
        //      userTransaction.begin();
        if (ziehung.getStatus() == null) {
            ziehung.setStatus(5);
        }
        System.out.println("Status: " + ziehung.getStatus());
        switch (ziehung.getStatus()) {
            case 5:
                System.out.println("Hole lz Liste");
                //List<Lottoscheinziehung> lzList = jPQLQueryLocal.lzList(ziehung, 10000);
                List<Lottoscheinziehung> lzList = ziehung.getLottoscheinziehungList();
                System.out.println("Fertig. " + lzList.size());
                if (bearbeiteLottoscheinZiehungen(lzList)) {
                    if (ziehungAuswertenData_neu.getAnzahlScheineBearbeitet() == ziehungAuswertenData_neu.getAnzahlScheine()) {
                        ziehung.setStatus(6);
                    }
                    writeLog("Erfolg in 'mainloop': Lottoscheine bearbeiten - Ok");
                    break;
                } else {
                    writeLog("Fehler in 'mainloop': Lottoscheine bearbeiten - Nok");
                    break;
                }
            case 6:
                if (berechneQuotenLotto()) {
                    ziehung.setStatus(7);
                    writeLog("Erfolg in 'mainloop': Quotenberechnung 6 aus 49 - Ok");
                    //break;
                } else {
                    writeLog("Fehler in 'mainloop': Quotenberechnung 6 aus 49 - Nok");
                    break;
                }
            case 7:
                if (berechneQuotenSpiel77()) {
                    ziehung.setStatus(8);
                    writeLog("Erfolg in 'mainloop': Quotenberechnung Spiel77 - Ok");
                    //break;
                } else {
                    writeLog("Fehler in 'mainloop': Quotenberechnung Spiel77 - Nok");
                    break;
                }
            case 8:
                if (berechneQuotenSuper6()) {
                    ziehung.setStatus(9);
                    writeLog("Erfolg in 'mainloop': Quotenberechnung Super 6 - Ok");
                    //break;
                } else {
                    writeLog("Fehler in 'mainloop': Quotenberechnung Super 6 - Nok");
                    break;
                }
        }
        System.out.println("Persistiere Ziehung");
        ziehungFacadeLocal.edit(ziehung);
        System.out.println("Persistiere Ziehung - Fertig");
        System.out.println("commit...");
        //    userTransaction.commit();
//            System.out.println("commit fertig");
        //} catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
        //  Logger.getLogger(ZiehungAuswerten.class.getName()).log(Level.SEVERE, null, ex);
        //}

    }

    //@TransactionAttribute(TransactionAttributeType.REQUIRED)
    private boolean bearbeiteLottoscheinZiehungen(List<Lottoscheinziehung> lottoscheinziehungen) {

        int anzahlScheine = 0;
        int anzahlTipps = 0;
        int anzahlSpiel77 = 0;
        int anzahlSuper6 = 0;
        int[] gklLotto = new int[10];
        int[] gklSpiel77 = new int[8];
        int[] gklSuper6 = new int[7];
        try {
            for (Lottoscheinziehung lottoscheinziehung : lottoscheinziehungen) {
                //System.out.println("Länge der LottoscheinziehungList:" + lottoschein.getLottoscheinziehungList().size());
                // Wichtige Arbeiten in der Schleife:
                if (lottoscheinziehung.getIsAbgeschlossen() != null) {
                    continue;
                }

                Lottoschein lottoschein = lottoscheinziehung.getLottoscheinId();
                //System.out.println(lottoschein);
                anzahlSpiel77 += lottoschein.getIsSpiel77() ? 1 : 0;
                anzahlSuper6 += lottoschein.getIsSuper6() ? 1 : 0;
                byte[] tippByte = lottoschein.getTipps();
                anzahlTipps += tippByte.length / 8;
                anzahlScheine++;
                long[] tippsLong = ByteLongConverter.byteToLong(tippByte);
                int superZahlSchein = lottoschein.getLosnummer() % 10;
                int tippCounter = 0;
                for (long tipp : tippsLong) {
                    tippCounter++;
                    int gkl = LottoUtil.gkl6Aus49(ziehungsZahlen, tipp, superZahlSchein == ziehung.getSuperzahl());
                    //System.out.println(gkl);
                    if (gkl > 0) {
                        Lottoscheinziehung6aus49 lottoscheinziehungLotto = new Lottoscheinziehung6aus49();
                        lottoscheinziehungLotto.setGewinnklasseId(gewinnKlassen.get(configSpielIdLotto).get(gkl));
                        lottoscheinziehungLotto.setTippNr(tippCounter);
                        lottoscheinziehungLotto.setLottoscheinZiehungId(lottoscheinziehung);
                        lottoscheinziehungLotto.setCreated(new Date());
                        lottoscheinziehungLotto.setLastModified(new Date());
                        lottoscheinziehungLottoFacadeLocal.create(lottoscheinziehungLotto);
                        //System.out.println("Gewinner gefunden");
                    }
                    gklLotto[gkl]++;
                }

                int gkl = LottoUtil.gklSpiel77(ziehung.getSpiel77(), lottoschein.getLosnummer());
                gklSpiel77[gkl]++;
                if (gkl > 0) {
                    lottoscheinziehung.setGewinnklasseIdSpiel77(gewinnKlassen.get(configSpielIdSpiel77).get(gkl));
                }
                gkl = LottoUtil.gklSuper6(ziehung.getSuper6(), lottoschein.getLosnummer());
                gklSuper6[gkl]++;
                if (gkl > 0) {
                    lottoscheinziehung.setGewinnklasseIdSuper6(gewinnKlassen.get(configSpielIdSuper6).get(gkl));
                }
                lottoscheinziehung.setIsAbgeschlossen(false);

                lottoscheinziehungFacadeLocal.edit(lottoscheinziehung);

                /*
            if (anzahlScheine >= 10000) {
                break;
            }
                 */
            }
            /// Test zwinge Klasse 1 auf 0 Gewinner
            ///gklLotto[1]=0;
/*
            ziehungAuswertenData.addAnzahlScheine(anzahlScheine);
            ziehungAuswertenData.addAnzahlTipps(anzahlTipps);

            ziehungAuswertenData.addgklLotto(gklLotto);
            ziehungAuswertenData.addgklSpiel77(gklSpiel77);
            ziehungAuswertenData.addgklSuper6(gklSuper6);
             */
            BigInteger einsatzLotto = BigInteger.valueOf(anzahlTipps).multiply(BigInteger.valueOf(gebuehr.getEinsatzProTipp()));
            ziehung.setEinsatzLotto(ziehung.getEinsatzLotto().add(einsatzLotto));

            BigInteger einsatzSpiel77 = BigInteger.valueOf(anzahlSpiel77).multiply(BigInteger.valueOf(gebuehr.getEinsatzSpiel77()));
            ziehung.setEinsatzSpiel77(ziehung.getEinsatzSpiel77().add(einsatzSpiel77));

            BigInteger einsatzSuper6 = BigInteger.valueOf(anzahlSuper6).multiply(BigInteger.valueOf(gebuehr.getEinsatzSuper6()));
            ziehung.setEinsatzSuper6(ziehung.getEinsatzSuper6().add(einsatzSuper6));
            ziehungFacadeLocal.edit(ziehung);
            ziehungAuswertenData_neu.setAnzahlScheineBearbeitet(ziehungAuswertenData_neu.getAnzahlScheineBearbeitet() + anzahlScheine);
            ziehungAuswertenData_neu.addgklLotto(gklLotto);
            ziehungAuswertenData_neu.addgklSpiel77(gklSpiel77);
            ziehungAuswertenData_neu.addgklSuper6(gklSuper6);
            System.out.println("'bearbeiteLottoscheinZiehungen' wird gleich verlassen. Es erfolgt wahrscheinlich ein commit...");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private boolean berechneQuotenLotto() {
        int[] gklLotto = getGklLotto();
        long[] gewinnInGklLotto = new long[10];
        long[] quoteInGklLotto = new long[10];


        try {
            /**
             * *************************************************
             * Quotenberechnung 6 aus 49
             * *************************************************
             */
            long ausschuettungLotto = (long) (ziehung.getEinsatzLotto().longValue() * configLottoFaktor);

            // Gewinnklasse 1
            gewinnInGklLotto[1] = ausschuettungLotto * getGklFromNr(configSpielIdLotto, 1).getBetrag().longValue() / 10000;
            ausschuettungLotto -= gewinnInGklLotto[1];

            // Gewinnklasse 9
            gewinnInGklLotto[9] = gklLotto[9] * getGklFromNr(configSpielIdLotto, 9).getBetrag().longValue();
            ausschuettungLotto -= gewinnInGklLotto[9];

            // Gewinnklassen 2 bis 8
            long ausschuettungsBetrag = 0L;
            long ausschuettungsBetragKum = 0L;
            for (int gkl = 2; gkl < 8; gkl++) {
                ausschuettungsBetrag = ausschuettungLotto * getGklFromNr(configSpielIdLotto, gkl).getBetrag().longValue() / 10000;
                gewinnInGklLotto[gkl] = ausschuettungsBetrag;
                ausschuettungsBetragKum += ausschuettungsBetrag;
//                ausschuettungLotto -= gewinnInGklLotto[gkl];
            }

            gewinnInGklLotto[8] = ausschuettungLotto - ausschuettungsBetragKum;
            long jackpotNeuBetrag = 0;

            Jackpot jackLotto = null;
            
            writeLog("Länge Jacklist: " + ziehung.getJackpotList().size());
            List<Jackpot> jackList = ziehung.getJackpotList();
            for (Jackpot jack : jackList) {
                writeLog("Jack GewinnklasseId: " + jack.getGewinnklasseId());
                if (jack.getGewinnklasseId().equals(getGklFromNr(configSpielIdLotto, 1))) {
                    jackLotto = jack;
                }
            }
            
            if (jackLotto == null) {
                writeLog("Wie kann das sein: jackLotto ist null");
            }

            long jackpotAltBetrag = jackLotto.getBetragKumuliert().longValue();
            int anzahlZiehungenNeu = (jackLotto.getAnzahlZiehungen() + 1);
            boolean isZwangsausschuettung = anzahlZiehungenNeu > configMaxZiehungJackZwangsausschuettung;

            // Jackpot
            for (int gkl = 1; gkl <= 8; gkl++) {
                if (gklLotto[gkl] > 0) {
                    if (gkl == 1) {
                        gewinnInGklLotto[gkl] += jackpotAltBetrag;
                        jackpotAltBetrag = 0;
                        anzahlZiehungenNeu = 0;
                    } else if (isZwangsausschuettung && jackpotAltBetrag > 0) {
                        gewinnInGklLotto[gkl] += jackpotAltBetrag;
                        jackpotAltBetrag = 0;
                        anzahlZiehungenNeu = 0;
                    }
                } else if (gkl == 2 && gklLotto[1] > 0) {
                    // GKL 2 ist unbesetzt und GKL 1 ist besetzt
                    // Betrag von GKL 2 der GKL 1 zuschlagen
                    gewinnInGklLotto[1] += gewinnInGklLotto[2];
                    //gewinnInGklLotto[2] = 0;
                } else {
                    // GKL (außer 2) ist unbesetzt 
                    // Betrag wird dem Jackpot zugeschlagen
                    jackpotNeuBetrag += gewinnInGklLotto[gkl];
                    if (isZwangsausschuettung || gkl == 1) {
                        gewinnInGklLotto[gkl] += jackpotAltBetrag;

                    }

                    //gewinnInGklLotto[gkl] = 0;
                }
            }

            for (int i = 1; i <= 9; i++) {
                quoteInGklLotto[i] = gklLotto[i] > 0 ? gewinnInGklLotto[i] / gklLotto[i] : gewinnInGklLotto[i];
            }

            for (int i = 1; i <= 7; i++) {
                if (quoteInGklLotto[i + 1] > quoteInGklLotto[i] && gklLotto[i] > 0 && gklLotto[i + 1] > 0) {
                    long summe = quoteInGklLotto[i] * gklLotto[i] + quoteInGklLotto[i + 1] * gklLotto[i + 1];
                    quoteInGklLotto[i + 1] = quoteInGklLotto[i] = summe / (gklLotto[i] + gklLotto[i + 1]);
                }
            }

            // Gewinnquoten 6 aus 49
            Date date = new Date();
            for (int i = 1; i <= 9; i++) {
                Gewinnklasseziehungquote gewinnklasseziehungquote = new Gewinnklasseziehungquote();
                Gewinnklasse gewinnklasse = getGklFromNr(configSpielIdLotto, i);
                gewinnklasseziehungquote.setGewinnklasseId(gewinnklasse);
                gewinnklasseziehungquote.setZiehungId(ziehung);
                gewinnklasseziehungquote.setAnzahlGewinner(gklLotto[i]);
                gewinnklasseziehungquote.setQuote(quoteInGklLotto[i]);
                gewinnklasseziehungquote.setCreated(date);
                gewinnklasseziehungquote.setLastModified(date);
//                try {
                gewinnklasseziehungquoteFacadeLocal.create(gewinnklasseziehungquote);
//                } catch (Exception e) {
                //                  writeLog("Fehler in 'berechneQuotenLotto': " + e.getMessage());
                //                return false;
//                }
            }

            /* Jackpot schreiben */
            Date ziehungsdatum = ziehung.getZiehungsdatum();
            Ziehung naechsteZiehung = (Ziehung) namedQueryLocal.nq2(LottoDatumUtil.naechsterZiehungstag(ziehungsdatum)).get(0);

            Jackpot jackpotNeu = new Jackpot();
            jackpotNeu.setAnzahlZiehungen(anzahlZiehungenNeu);
            jackpotNeu.setBetrag(BigInteger.valueOf(jackpotNeuBetrag));
            jackpotNeu.setBetragKumuliert(BigInteger.valueOf(jackpotNeuBetrag));
            jackpotNeu.setGewinnklasseId(getGklFromNr(configSpielIdLotto, 1));
            jackpotNeu.setZiehungId(naechsteZiehung);
            jackpotNeu.setCreated(date);
            jackpotNeu.setLastModified(date);
            jackpotFacadeLocal.create(jackpotNeu);

            ziehungAuswertenData_neu.setGklLotto(gklLotto);
            ziehungAuswertenData_neu.setQuoteGklLotto(quoteInGklLotto);

            return true;
        } catch (Exception e) {
            // writeLog("Fehler in 'berechneQuotenLotto': " + e.getMessage());
            //return false;
            throw new RuntimeException(e);
        }
    }

    private int[] getGklLotto() throws RuntimeException {
        int[] gklLotto = new int[10];
        ResultSet rs;
        Statement statement;
        try (Connection connection = ds.getConnection()) {
            statement = connection.createStatement();

            String sql = "SELECT GewinnklasseId, count(*) AS anzahl "
                    + "FROM lottoscheinziehung6aus49 lz6_49 "
                    + "INNER JOIN lottoscheinziehung lz "
                    + "ON lz6_49.LottoscheinZiehungId = lz.LottoscheinZiehungId "
                    + "WHERE lz.ZiehungId = " + ziehung.getZiehungId() + " GROUP BY `GewinnklasseId`";

            rs = statement.executeQuery(sql);
            while (rs.next()) {
                long gklId = rs.getInt("GewinnklasseId");
                int gkl = getGkFromId(gklId);
                gklLotto[gkl] = rs.getInt("anzahl");
            }
            return gklLotto;
        } catch (SQLException sqle) {
            // Was passiert mit der Transaktion?
            // Wird die hier abgebrochen?
            throw new RuntimeException(sqle);
            //writeLog("Fehler in 'berechneQuotenLotto': " + sqle.getMessage());
            //return false;
        }
    }

    private boolean berechneQuotenSpiel77() {
        int[] gklSpiel77 = new int[8];
        long[] quoteInGklSpiel77 = new long[8];

        ResultSet rs;
        Statement statement;
        try (Connection connection = ds.getConnection()) {
            statement = connection.createStatement();

            String sql = "SELECT GewinnklasseIdSpiel77, count(*) AS anzahl "
                    + "FROM lottoscheinziehung lz "
                    + "WHERE lz.ZiehungId = " + ziehung.getZiehungId() + " GROUP BY `GewinnklasseIdSpiel77`";

            rs = statement.executeQuery(sql);
            while (rs.next()) {
                long gklId = rs.getInt("GewinnklasseIdSpiel77");
                int gkl = getGkFromId(gklId);
                gklSpiel77[gkl] = rs.getInt("anzahl");
            }
        } catch (SQLException sqle) {
            // Was passiert mit der Transaktion?
            // Wird die hier abgebrochen?
            //writeLog("Fehler in 'berechneQuotenSpiel77': " + sqle.getMessage());
            //return false;
            throw new RuntimeException(sqle);
        }

        try {
            /**
             * *************************************************
             * Quotenberechnung Spiel 77
             * *************************************************
             */

            long ausschuettungSpiel77 = (long) (ziehung.getEinsatzSpiel77().longValue());

            // Gewinnquoten Spiel 77 für die Gewinnklassen 2 bis 7
            for (int i = 2; i <= 7; i++) {
//                if (gklSpiel77[i] > 0) {
                quoteInGklSpiel77[i] = getGklFromNr(configSpielIdSpiel77, i).getBetrag().longValue();
//                } else {
//                    quoteInGklSpiel77[i] = 0;
//                }
            }
            // Gewinnquote Gewinnklasse 1

            Jackpot jack77 = null;

            List<Jackpot> jackList = ziehung.getJackpotList();
            for (Jackpot jack : jackList) {
                if (jack.getGewinnklasseId().equals(getGklFromNr(configSpielIdSpiel77, 1))) {
                    jack77 = jack;
                }
            }

            long jackpotAltBetrag = jack77.getBetragKumuliert().longValue();
            long ausschuettungKlasse1 = (long) (ausschuettungSpiel77 * 0.0711) + jackpotAltBetrag;
            jackpotAltBetrag = 0;
            long jackpotNeuBetrag = 0;

            if (gklSpiel77[1] > 50) {
                quoteInGklSpiel77[1] = Math.max(50 * 177_777_00, ausschuettungKlasse1) / gklSpiel77[1];
                // oder Math.min() ????? wg: 

                // Werden mehr als 50 Gewinne ermittelt, 
                // wird die Gewinnausschüttung der Gewinnklasse 1
                // auf 50 x 177.777,00 € oder – wenn diese höher ist – 
                // auf die gemäß den Spiegelstrichen 2 und 5 
                // festgestellte Gewinnausschüttung begrenzt 
                // und auf die Gesamtzahl der Gewinne aufgeteilt.
            } else if (gklSpiel77[1] > 0) {
                quoteInGklSpiel77[1] = ausschuettungKlasse1 / gklSpiel77[1];
                if (quoteInGklSpiel77[1] < 177_777_00) {
                    quoteInGklSpiel77[1] = 177_777_00;
                } else {
                    quoteInGklSpiel77[1] = (long) ((Math.floor((quoteInGklSpiel77[1] - 177_777_00) / 100_000_00) * 100_000_00) + 177_777_00);
                }
                jackpotNeuBetrag = ausschuettungKlasse1 - (quoteInGklSpiel77[1] * gklSpiel77[1]);
            } else {
                int anzahlZiehungenNeu = (jack77.getAnzahlZiehungen() + 1);
                boolean isZwangsausschuettung = anzahlZiehungenNeu > configMaxZiehungJackZwangsausschuettung;
                if (isZwangsausschuettung) {
                    for (int i = 2; i <= 7; i++) {
                        if (gklSpiel77[i] > 0) {
                            quoteInGklSpiel77[i] += ausschuettungKlasse1 / gklSpiel77[i];
                            break;
                        }
                    }
                } else {
                    quoteInGklSpiel77[1] = jackpotNeuBetrag = ausschuettungKlasse1;

                }
            }

            if (quoteInGklSpiel77[2] > quoteInGklSpiel77[1] && gklSpiel77[1] > 0 && gklSpiel77[2] > 0) {
                long summe = quoteInGklSpiel77[1] * gklSpiel77[1] + quoteInGklSpiel77[2] * gklSpiel77[2];
                quoteInGklSpiel77[2] = quoteInGklSpiel77[1] = summe / (gklSpiel77[1] + gklSpiel77[2]);
            }
            Date date = new Date();
            for (int i = 1; i <= 7; i++) {
                Gewinnklasseziehungquote gewinnklasseziehungquote = new Gewinnklasseziehungquote();
                Gewinnklasse gewinnklasse = getGklFromNr(configSpielIdSpiel77, i);
                gewinnklasseziehungquote.setGewinnklasseId(gewinnklasse);
                gewinnklasseziehungquote.setZiehungId(ziehung);
                gewinnklasseziehungquote.setAnzahlGewinner(gklSpiel77[i]);
                gewinnklasseziehungquote.setQuote(quoteInGklSpiel77[i]);
                gewinnklasseziehungquote.setCreated(date);
                gewinnklasseziehungquote.setLastModified(date);
//                try {
                gewinnklasseziehungquoteFacadeLocal.create(gewinnklasseziehungquote);
//                } catch (Exception e) {
                //                  writeLog("Fehler in 'berechneQuotenLotto': " + e.getMessage());
                //                return false;
//                }
            }

            /* Jackpot schreiben */
            Date ziehungsdatum = ziehung.getZiehungsdatum();
            Ziehung naechsteZiehung = (Ziehung) namedQueryLocal.nq2(LottoDatumUtil.naechsterZiehungstag(ziehungsdatum)).get(0);
            int anzahlZiehungen = jack77.getAnzahlZiehungen() + 1;
            if (anzahlZiehungen > configMaxZiehungJackZwangsausschuettung) {
                anzahlZiehungen = 1;
            }

            Jackpot jackpotNeu = new Jackpot();

            jackpotNeu.setAnzahlZiehungen(anzahlZiehungen);
            jackpotNeu.setBetrag(BigInteger.valueOf(jackpotNeuBetrag));
            jackpotNeu.setBetragKumuliert(BigInteger.valueOf(jackpotNeuBetrag));
            jackpotNeu.setGewinnklasseId(getGklFromNr(configSpielIdSpiel77, 1));
            jackpotNeu.setZiehungId(naechsteZiehung);
            jackpotNeu.setCreated(date);
            jackpotNeu.setLastModified(date);
            jackpotFacadeLocal.create(jackpotNeu);
            ziehungAuswertenData_neu.setGklSpiel77(gklSpiel77);
            ziehungAuswertenData_neu.setQuoteGklSpiel77(quoteInGklSpiel77);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
            //throw new RuntimeException(e);
//            return false;
        }
    }

    private boolean berechneQuotenSuper6() {
        int[] gklSuper6 = new int[7];
        long[] quoteInGklSuper6 = new long[7];

        ResultSet rs;
        Statement statement;
        try (Connection connection = ds.getConnection()) {
            statement = connection.createStatement();

            String sql = "SELECT GewinnklasseIdSuper6, count(*) AS anzahl "
                    + "FROM lottoscheinziehung lz "
                    + "WHERE lz.ZiehungId = " + ziehung.getZiehungId() + " GROUP BY `GewinnklasseIdSuper6`";

            rs = statement.executeQuery(sql);
            while (rs.next()) {
                long gklId = rs.getInt("GewinnklasseIdSuper6");
                int gkl = getGkFromId(gklId);
                gklSuper6[gkl] = rs.getInt("anzahl");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }

        for (int i = 1; i <= 6; i++) {
            quoteInGklSuper6[i] = getGklFromNr(configSpielIdSuper6, i).getBetrag().longValue();
        }

        if (gklSuper6[1] > 100) {
            quoteInGklSuper6[1] = 100 * quoteInGklSuper6[1] / gklSuper6[1];
        }

        if (quoteInGklSuper6[2] > quoteInGklSuper6[1] && gklSuper6[2] > 0 && gklSuper6[1] > 0) {
            long summe = quoteInGklSuper6[1] * gklSuper6[1] + quoteInGklSuper6[2] * gklSuper6[2];
            quoteInGklSuper6[2] = quoteInGklSuper6[1] = summe / (gklSuper6[1] + gklSuper6[2]);
        }
        Date date = new Date();
        for (int i = 1; i <= 6; i++) {
            Gewinnklasseziehungquote gewinnklasseziehungquote = new Gewinnklasseziehungquote();
            Gewinnklasse gewinnklasse = getGklFromNr(configSpielIdSuper6, i);
            gewinnklasseziehungquote.setGewinnklasseId(gewinnklasse);
            gewinnklasseziehungquote.setZiehungId(ziehung);
            gewinnklasseziehungquote.setAnzahlGewinner(gklSuper6[i]);
            gewinnklasseziehungquote.setQuote(quoteInGklSuper6[i]);
            gewinnklasseziehungquote.setCreated(date);
            gewinnklasseziehungquote.setLastModified(date);
//                try {
            gewinnklasseziehungquoteFacadeLocal.create(gewinnklasseziehungquote);
            ziehungAuswertenData_neu.setGklSuper6(gklSuper6);
            ziehungAuswertenData_neu.setQuoteGklSuper6(quoteInGklSuper6);

//                } catch (Exception e) {
            //                  writeLog("Fehler in 'berechneQuotenLotto': " + e.getMessage());
            //                return false;
//                }
        }

        return true;
    }

    private int getGkFromId(long gklId) {
        for (Map.Entry<Long, Map<Integer, Gewinnklasse>> entry : gewinnKlassen.entrySet()) {
            for (Map.Entry<Integer, Gewinnklasse> entry2 : entry.getValue().entrySet()) {
                if (entry2.getValue().getGewinnklasseId() == gklId) {
                    return entry2.getKey();
                }
            }

        }
        return 0;
    }

    private Gewinnklasse getGklFromNr(long spielId, int gewinnKlasseNr) {
        return gewinnKlassen.get(spielId).get(gewinnKlasseNr);
    }

    private Lottoscheinziehung getLottoscheinZiehung(List<Lottoscheinziehung> lottoscheinziehungList, long ziehungId) {

        for (Lottoscheinziehung lottoscheinziehung : lottoscheinziehungList) {
            if (lottoscheinziehung.getZiehungId().getZiehungId() == ziehungId) {
                return lottoscheinziehung;
            }
        }
        return null;
    }

    private Map<Long, Map<Integer, Gewinnklasse>> getGewinnKlassen(Date ziehungsdatum) {

        gewinnKlassen = new HashMap<>();

        String sql = "SELECT gk  FROM Gewinnklasse gk "
                + " WHERE gk.gueltigAb <= :ziehungsdatum ";
        //+ " AND gk.gueltigBis >= :ziehungsdatum";

        Query query = em.createQuery(sql);
        query.setParameter("ziehungsdatum", ziehungsdatum);

        List<Gewinnklasse> gkList = query.getResultList();
        for (Gewinnklasse gewinnklasse : gkList) {
            Map<Integer, Gewinnklasse> value;
            if (!gewinnKlassen.containsKey(gewinnklasse.getSpielId().getSpielId())) {
                value = new HashMap<>();

                gewinnKlassen.put(gewinnklasse.getSpielId().getSpielId(), value);

            } else {
                value = gewinnKlassen.get(gewinnklasse.getSpielId().getSpielId());
            }
            value.put(gewinnklasse.getGewinnklasseNr(), gewinnklasse);
        }

        return gewinnKlassen;
    }

    private void writeLog(String message) {
        System.out.println(message);
    }

// Ab hier Spielwiese für eine eventuelle  erweiterte Version
    @Asynchronous
    @Override
    public Future<String> auswertung_async(long ziehungId) {
        auswertung(ziehungId);
        return new AsyncResult<String>("Bearbeitung beendet");
    }

    @Override
    public void auswertung(long ziehungId) {
        System.out.println("Auswertung starten...");
        if (ziehung == null || !ziehung.equals(ziehungAuswertenData_neu.getZiehung())) {
            if (ziehungAuswertenData_neu.getZiehung() != null) {
                ziehung = ziehungAuswertenData_neu.getZiehung();
            } else {
                initZiehung(ziehungId);
            }
        }

        if (ziehung.getEinsatzLotto() == null) {
            ziehung.setEinsatzLotto(BigInteger.ZERO);
        }
        if (ziehung.getEinsatzSpiel77() == null) {
            ziehung.setEinsatzSpiel77(BigInteger.ZERO);
        }
        if (ziehung.getEinsatzSuper6() == null) {
            ziehung.setEinsatzSuper6(BigInteger.ZERO);
        }
        ziehungsZahlen = ziehung.getZahlenAlsBits().longValue();
        Date ziehungsdatum = ziehung.getZiehungsdatum();

        // TODO: mit geeignetem Query holen!!
        gebuehr = gebuehrFacadeLocal.find(1L);

        gewinnKlassen = getGewinnKlassen(ziehungsdatum);

        // Auswertung beginnt
        if (ziehung.getStatus() == null) {
            ziehung.setStatus(0);
        }
        List<Lottoscheinziehung> lz = jPQLQueryLocal.lzList(ziehung, 10000);
        System.out.println("Fertig. " + lz.size());
        if (bearbeiteLottoscheinZiehungen(lz)) {
            //ziehung.setStatus(1);
            writeLog("Erfolg in 'mainloop': Lottoscheine bearbeiten - Ok");
        } else {
            writeLog("Fehler in 'mainloop': Lottoscheine bearbeiten - Nok");
        }

    }

    ;

    public void initZiehung(long ziehungId) {
        ziehung = ziehungFacadeLocal.find(ziehungId);
        ziehungAuswertenData_neu.setZiehung(null);
        ziehungAuswertenData_neu.setZiehung(ziehung);
    }

}
