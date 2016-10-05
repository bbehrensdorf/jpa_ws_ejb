/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Lottoschein;

/**
 *
 * @author Master
 */
@Local
public interface PopulateLottoscheinLocal {
      public void populateLottoschein(List<Lottoschein> lottoscheine);
}
