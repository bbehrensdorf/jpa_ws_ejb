/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import wbs.lotto.domain.Gebuehr;
import wbs.lotto.persistence.GebuehrFacadeLocal;

/**
 *
 * @author Master
 */
@Stateless
public class PopulateGebuehr implements PopulateGebuehrLocal {

    @EJB
    private GebuehrFacadeLocal gebuehrFacadeLocal;
    
    @Override
    public void populateGebuehr() {
        
            Date date = new Date();
            GregorianCalendar cal = new GregorianCalendar(2018, Calendar.DECEMBER, 31);
            Gebuehr gebuehr = new Gebuehr();
            gebuehr.setGrundgebuehr(50);
            gebuehr.setEinsatzProTipp(100);
            gebuehr.setEinsatzSpiel77(250);
            gebuehr.setEinsatzSuper6(125);
            gebuehr.setGueltigBis(cal.getTime());
            gebuehr.setCreated(date);
            gebuehr.setGueltigAb(date);
            gebuehr.setLastModified(date);
            gebuehrFacadeLocal.create(gebuehr);
        
    }
}
