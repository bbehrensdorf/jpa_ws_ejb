/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Lottoscheinziehung;

/**
 *
 * @author Master
 */
@Local
public interface LottoscheinziehungFacadeLocal {

    void create(Lottoscheinziehung lottoscheinziehung);

    void edit(Lottoscheinziehung lottoscheinziehung);

    void remove(Lottoscheinziehung lottoscheinziehung);

    Lottoscheinziehung find(Object id);

    List<Lottoscheinziehung> findAll();

    List<Lottoscheinziehung> findRange(int[] range);

    int count();
    
}
