/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import wbs.lotto.domain.Ziehung;
import wbs.lotto.persistence.ZiehungFacadeLocal;
import wbs.lotto.util.LottoDatumUtil;
import wbs.lotto.util.LottoZiehungUtil;

/**
 *
 * @author Master
 */
@Stateless
public class PopulateZiehung implements PopulateZiehungLocal {

    @EJB
    private ZiehungFacadeLocal ziehungFacadeLocal;

    @Override
    public void populateZiehung(Date ziehungsDatum) {
        Integer[] zahlen6Aus49 = LottoZiehungUtil.zufall6aus49();
        int superzahl = LottoZiehungUtil.zufallSuperzahl();
        Integer spiel77 = LottoZiehungUtil.zufallSpiel77();
        Integer supersechs = LottoZiehungUtil.zufallSuper6();
        //GregorianCalendar ziehungsDatum=new GregorianCalendar();

        Ziehung ziehung = new Ziehung();
        Date date = new Date();
        BigInteger zahlen = BigInteger.ZERO;
        

        for (int i : zahlen6Aus49) {
            zahlen = zahlen.setBit(i);
        }
        ziehung.setZiehungsdatum(ziehungsDatum); 
        ziehung.setZahlenAlsBits(zahlen);
        ziehung.setSpiel77(spiel77);
        ziehung.setSuper6(supersechs);
        ziehung.setSuperzahl(superzahl);

        ziehung.setCreated(date);
        ziehung.setLastModified(date);

        ziehungFacadeLocal.create(ziehung);

    }

    @Override
    public void populateZiehungen(int anzahl) {
        Date date=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.setTime(LottoDatumUtil.ersterZiehungstag(date,true,true,18,18));
        for (int i=0; i<anzahl;i++) {
          populateZiehung(cal.getTime());
          cal.add(Calendar.DATE, 1);
          cal.setTime(LottoDatumUtil.ersterZiehungstag(cal.getTime(),true,true,18,18));
        }
        

    }

}
