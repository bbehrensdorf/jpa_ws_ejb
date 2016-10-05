/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Master
 */
@Local
public interface PopulateZiehungLocal {
    void populateZiehung(Date ziehungsDatum);
    void populateZiehungen(int anzahl);
}
