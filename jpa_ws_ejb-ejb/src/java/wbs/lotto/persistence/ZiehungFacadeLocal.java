/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Ziehung;

/**
 *
 * @author Master
 */
@Local
public interface ZiehungFacadeLocal {

    void create(Ziehung ziehung);

    void edit(Ziehung ziehung);

    void remove(Ziehung ziehung);

    Ziehung find(Object id);

    List<Ziehung> findAll();

    List<Ziehung> findRange(int[] range);

    int count();
    
}
