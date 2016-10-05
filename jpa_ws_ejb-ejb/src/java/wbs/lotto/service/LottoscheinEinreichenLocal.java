/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.service;

import javax.ejb.Local;
import wbs.lotto.domain.Lottoschein;

/**
 *
 * @author Master
 */
@Local
public interface LottoscheinEinreichenLocal {

    void lottoscheinEinreichen(Lottoschein schein, int laufzeit);
    
}
