/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.jpafeatures;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Gebuehr;
import wbs.lotto.domain.Lottoscheinziehung;
import wbs.lotto.domain.Ziehung;

/**
 *
 * @author Bernd
 */
@Local
public interface JPQLQueryLocal {
        public Gebuehr gebuehr(Date datum);
        public List<Lottoscheinziehung> lzList(Ziehung ziehung, int maxResults);
    
}
