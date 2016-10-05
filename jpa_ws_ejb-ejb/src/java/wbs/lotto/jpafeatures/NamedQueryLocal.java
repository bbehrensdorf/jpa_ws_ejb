/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.jpafeatures;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Ziehung;

/**
 *
 * @author Master
 */
@Local
public interface NamedQueryLocal {

    public List nq1(String land);

    public List nq2(Date datum);

    public List lottoscheinNachZiehung(long ziehungId);

    public Ziehung getZiehungById(long ziehungId);

    public List lottoscheinZiehungNachZiehung(long ziehungId);

}
