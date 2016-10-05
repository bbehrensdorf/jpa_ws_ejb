/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.service;

import java.util.concurrent.Future;
import javax.ejb.Local;
import wbs.lotto.domain.Ziehung;

/**
 *
 * @author Bernd
 */
@Local
public interface ZiehungAuswertenDataLocal_neu {

    public void reset();

    public Ziehung getZiehung();

    public void setZiehung(Ziehung ziehung);

    public long getAnzahlScheine();

    public void setAnzahlScheine(long anzahlScheine);

    public long getAnzahlTipps();

    public void setAnzahlTipps(long anzahlTipps);

    public int[] getGklLotto();

    public void setGklLotto(int[] gklLotto);

    public void addgklLotto(int[] gklLottoAddition);

    public int[] getGklSpiel77();

    public void setGklSpiel77(int[] gklSpiel77);

    public void addgklSpiel77(int[] gklSpiel77Addition);

    public int[] getGklSuper6();

    public void setGklSuper6(int[] gklSuper6);

    public void addgklSuper6(int[] gklSuper6Addition);

    public long[] getQuoteGklLotto();

    public void setQuoteGklLotto(long[] quoteGklLotto);

    public long[] getQuoteGklSpiel77();

    public void setQuoteGklSpiel77(long[] quoteGklSpiel77);

    public long[] getQuoteGklSuper6();

    public void setQuoteGklSuper6(long[] quoteGklSuper6);

    public double getAuswertungslaufzeit();

    public void setAuswertungslaufzeit(double auswertungslaufzeit);

    public int getAuswertungsstatus();

    public void setAuswertungsstatus(int auswertungsstatus);

    public String getLottoZahlenAsString();

    public void setLottoZahlenAsString(String zahlenAsString);

    public long getAnzahlSpiel77();

    public void setAnzahlSpiel77(long anzahlSpiel77);

    public long getAnzahlSuper6();

    public void setAnzahlSuper6(long anzahlSuper6);

    public long getAnzahlScheineBearbeitet();

    public void setAnzahlScheineBearbeitet(long anzahlScheineBearbeitet);

    public long getZiehungId();

    public void setZiehungId(long ziehungId);

    public double getAuswertungsstartzeit();

    public void setAuswertungsstartzeit(double auswertungsstartzeit);

    public Future<String> getAuswertungsFuture();

    public void initZiehung();

    public void setAuswertungsMeldung(String auswertungsMeldung);

    public String getAuswertungsMeldung();

    public void setAuswertungsFuture(Future<String> auswertungsFuture);


}
