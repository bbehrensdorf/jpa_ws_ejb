/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wbs.lotto.domain.Lottoschein;
import wbs.lotto.initdb.PopulateDatabaseLocal;
import wbs.lotto.initdb.PopulateLottoscheinLocal;
import wbs.lotto.util.ByteLongConverterBB;
import wbs.lotto.util.ByteLongConverter;
import wbs.lotto.util.TestdatenUtil;

/**
 *
 * @author Master
 */
@WebServlet(name = "PopulateLottoscheinServlet", urlPatterns = {"/PopulateLottoscheinServlet"})
public class PopulateLottoscheinServlet extends HttpServlet {

    @EJB
    private PopulateLottoscheinLocal populateLottoscheinLocal;

    long timeStart;
    String msg;
    PrintWriter outWriter;

    long ziehung;
    int superzahl;
    List<Long> tippsMitSuperzahl = new ArrayList<>();
    List<Long> tippsOhneSuperzahl = new ArrayList<>();
    List<Long> nietenMitSuperzahl = new ArrayList<>();
    List<Long> nietenOhneSuperzahl = new ArrayList<>();

    List<Lottoschein> lottoscheine = new ArrayList<>();

    int anzahlNietenOhneSuperzahl;
    int anzahlNietenMitSuperzahl;
    Random rnd = new Random();

    private void startMsg(String msg) {
        this.msg = msg;
        outWriter.println("<p>Start: " + msg + "</p>");
        timeStart = System.nanoTime();

    }

    private void endMsg() {
        long timeEnd = System.nanoTime();
        double seconds = (double) (timeEnd - timeStart) / 1000000000.0;
        outWriter.println("<p>Ende: " + msg + " - Dauer: " + (seconds) + " Sekunden</p>");
        timeStart = System.nanoTime();
    }

    private long getGaussian(double aMean, double aVariance) {
        return (long) (aMean + rnd.nextGaussian() * aVariance);
    }

    private Long getTipp(Integer gkl, long ziehung) {
        int anzahlRichtige = (int) Math.floor((14 - gkl) / 2);
        return TestdatenUtil.getWinTipp(ziehung, anzahlRichtige);
    }

    private Long getNieteOhneSZ(long ziehung) {
        return TestdatenUtil.getWinTipp(ziehung, rnd.nextInt(3));
    }

    private Long getNieteMitSZ(long ziehung) {
        return TestdatenUtil.getWinTipp(ziehung, rnd.nextInt(2));
    }

    private int getLosnummer(int superzahl, boolean mitSz) {

        List<Integer> ziffern = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));

        int letzteZiffer;
        if (mitSz) {
            letzteZiffer = superzahl;
        } else {
            ziffern.remove(superzahl);
            letzteZiffer = ziffern.get(rnd.nextInt(9));
        }

        int result = rnd.nextInt(1000000) * 10 + letzteZiffer;
        return result;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            outWriter = out;

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PopulateLottoscheinZiehungServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lege ein paar Testlottoscheine an</h1>");

            int[] ziehungArray = {4, 15, 18, 21, 32, 45, 4};
            ziehung = TestdatenUtil.testdatenArrayToLong(ziehungArray);
            superzahl = 4;

            Map<Integer, Integer> anzahlInGewinnklasse = new HashMap<>();
            /*            anzahlInGewinnklasse.put(1, 1);
            anzahlInGewinnklasse.put(2, 7);
            anzahlInGewinnklasse.put(3, 201);
            anzahlInGewinnklasse.put(4, 1948);
            anzahlInGewinnklasse.put(5, 4897);
            anzahlInGewinnklasse.put(6, 42663);
            anzahlInGewinnklasse.put(7, 80676);
            anzahlInGewinnklasse.put(8, 714150);
            anzahlInGewinnklasse.put(9, 575597);
             */
//            anzahlInGewinnklasse.put(1, 1);
//            anzahlInGewinnklasse.put(2, 7);
//            anzahlInGewinnklasse.put(3, 201);
//            anzahlInGewinnklasse.put(4, 948);
//            anzahlInGewinnklasse.put(5, 897);
//            anzahlInGewinnklasse.put(6, 4266);
//            anzahlInGewinnklasse.put(7, 8067);
//            anzahlInGewinnklasse.put(8, 71415);
//            anzahlInGewinnklasse.put(9, 57559);
//
//            anzahlNietenOhneSuperzahl = 4_647_873;
//            anzahlNietenMitSuperzahl = 423_659;

            anzahlInGewinnklasse.put(1, 1);
            anzahlInGewinnklasse.put(2, 7);
            anzahlInGewinnklasse.put(3, 50);
            anzahlInGewinnklasse.put(4, 48);
            anzahlInGewinnklasse.put(5, 90);
            anzahlInGewinnklasse.put(6, 426);
            anzahlInGewinnklasse.put(7, 806);
            anzahlInGewinnklasse.put(8, 7141);
            anzahlInGewinnklasse.put(9, 5755);

            anzahlNietenOhneSuperzahl = 647_873;
            anzahlNietenMitSuperzahl = 23_659;



            startMsg("Erzeuge Tipps für Scheine mit Superzahl");
            tippsMitSuperzahl.clear();
            for (int gkl = 1; gkl <= 9; gkl += 2) {
                int anzahl = anzahlInGewinnklasse.get(gkl);
                for (int j = 0; j < anzahl; j++) {
                    long tipp = getTipp(gkl, ziehung);
                    tippsMitSuperzahl.add(tipp);
                }
            }
            out.println("<p>Anzahl erzeugt: " + tippsMitSuperzahl.size() + "</p>");

            endMsg();

            startMsg("Erzeuge Nieten für Scheine mit Superzahl");
            nietenMitSuperzahl.clear();
            for (int i = 0; i < anzahlNietenMitSuperzahl; i++) {
                long tipp = getNieteMitSZ(ziehung);
                nietenMitSuperzahl.add(tipp);
            }

            out.println("<p>Anzahl erzeugt: " + nietenMitSuperzahl.size() + "</p>");
            endMsg();

            startMsg("Erzeuge Tipps für Scheine ohne Superzahl");
            tippsOhneSuperzahl.clear();
            for (int gkl = 2; gkl <= 8; gkl += 2) {
                int anzahl = anzahlInGewinnklasse.get(gkl);
                for (int j = 0; j < anzahl; j++) {
                    long tipp = getTipp(gkl, ziehung);
                    tippsOhneSuperzahl.add(tipp);
                }
            }
            out.println("<p>Anzahl erzeugt: " + tippsOhneSuperzahl.size() + "</p>");

            endMsg();

            startMsg("Erzeuge Nieten für Scheine ohne Superzahl");
            nietenOhneSuperzahl.clear();
            for (int i = 0; i < anzahlNietenOhneSuperzahl; i++) {
                long tipp = getNieteMitSZ(ziehung);
                nietenOhneSuperzahl.add(tipp);
            }

            out.println("<p>Anzahl erzeugt: " + nietenOhneSuperzahl.size() + "</p>");
            endMsg();

            startMsg("Nieten und Tipps zusammenschmeißen und mischen.");

            tippsMitSuperzahl.addAll(nietenMitSuperzahl);
            tippsOhneSuperzahl.addAll(nietenOhneSuperzahl);

            Collections.shuffle(tippsMitSuperzahl);
            Collections.shuffle(tippsOhneSuperzahl);

            endMsg();

            startMsg("Daten für Lottoscheine ohne Superzahl erzeugen.");

            lottoscheine.clear();

            int listenGroesse = tippsOhneSuperzahl.size();
            int orgListenGroesse = listenGroesse;
            while (listenGroesse > 0) {
                int anzahlTipps = (int) getGaussian(6, 3);
                if (anzahlTipps < 1) {
                    anzahlTipps = 1;
                }
                if (anzahlTipps > 12) {
                    anzahlTipps = 12;
                }
                if (anzahlTipps > listenGroesse) {
                    anzahlTipps = listenGroesse;
                }
                long[] tippsAsLong = new long[anzahlTipps];
                for (int i = 0; i < anzahlTipps; i++) {
                    tippsAsLong[i] = tippsOhneSuperzahl.get(orgListenGroesse - listenGroesse + i);
                }
                byte[] tippsAsByteArray = ByteLongConverter.longToByte(tippsAsLong);
                Date date = new Date();
                Lottoschein lottoschein = new Lottoschein();
                lottoschein.setAbgabeDatum(date);
                lottoschein.setCreated(date);
                lottoschein.setLastModified(date);
                lottoschein.setIsMittwoch(true);
                lottoschein.setIsSamstag(true);
                lottoschein.setLosnummer(getLosnummer(superzahl, false));
                lottoschein.setTipps(tippsAsByteArray);
                lottoscheine.add(lottoschein);

                listenGroesse -= anzahlTipps;
                //System.out.println(listenGroesse);
            }
            out.println("Anzahl Lottoscheine:" + lottoscheine.size());
            endMsg();
            tippsOhneSuperzahl = null;
            nietenOhneSuperzahl = null;
            startMsg("Daten für Lottoscheine mit Superzahl erzeugen.");

            listenGroesse = tippsMitSuperzahl.size();
            orgListenGroesse = listenGroesse;
            while (listenGroesse > 0) {
                int anzahlTipps = (int) getGaussian(6, 3);
                if (anzahlTipps < 1) {
                    anzahlTipps = 1;
                }
                if (anzahlTipps > 12) {
                    anzahlTipps = 12;
                }
                if (anzahlTipps > listenGroesse) {
                    anzahlTipps = listenGroesse;
                }
                long[] tippsAsLong = new long[anzahlTipps];
                for (int i = 0; i < anzahlTipps; i++) {
                    tippsAsLong[i] = tippsMitSuperzahl.get(orgListenGroesse - listenGroesse + i);
                }
                byte[] tippsAsByteArray = ByteLongConverter.longToByte(tippsAsLong);
                Date date = new Date();
                Lottoschein lottoschein = new Lottoschein();
                lottoschein.setAbgabeDatum(date);
                lottoschein.setCreated(date);
                lottoschein.setLastModified(date);
                lottoschein.setIsMittwoch(true);
                lottoschein.setIsSamstag(true);
                lottoschein.setLosnummer(getLosnummer(superzahl, true));
                lottoschein.setTipps(tippsAsByteArray);
                lottoscheine.add(lottoschein);

                listenGroesse -= anzahlTipps;
                //System.out.println(listenGroesse);
            }
            tippsMitSuperzahl = null;
            nietenMitSuperzahl = null;
            out.println("Anzahl Lottoscheine:" + lottoscheine.size());
            endMsg();
            startMsg("Mischen...");
            Collections.shuffle(lottoscheine);
            endMsg();
            System.out.println("Erzeugen...");
            startMsg("Lottoscheine erzeugen.");
            populateLottoscheinLocal.populateLottoschein(lottoscheine);
            endMsg();

            out.println("</body>");
            out.println("</html>");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
