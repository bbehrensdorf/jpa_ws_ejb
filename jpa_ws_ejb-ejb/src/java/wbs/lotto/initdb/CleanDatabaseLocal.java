/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import javax.ejb.Local;

/**
 *
 * @author Master
 */
@Local
public interface CleanDatabaseLocal {
    public void cleanDatabase(String schema);

    public void resetZiehungsAuswertung(long ziehungId, boolean resetLottoscheinZiehung) throws Exception;
}
