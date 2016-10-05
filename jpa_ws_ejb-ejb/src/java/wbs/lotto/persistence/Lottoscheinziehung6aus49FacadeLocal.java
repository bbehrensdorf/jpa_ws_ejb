/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Lottoscheinziehung6aus49;

/**
 *
 * @author Master
 */
@Local
public interface Lottoscheinziehung6aus49FacadeLocal {

    void create(Lottoscheinziehung6aus49 lottoscheinziehung6aus49);

    void edit(Lottoscheinziehung6aus49 lottoscheinziehung6aus49);

    void remove(Lottoscheinziehung6aus49 lottoscheinziehung6aus49);

    Lottoscheinziehung6aus49 find(Object id);

    List<Lottoscheinziehung6aus49> findAll();

    List<Lottoscheinziehung6aus49> findRange(int[] range);

    int count();
    
}
