/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Kunde;

/**
 *
 * @author Master
 */
@Local
public interface KundeFacadeLocal {

    void create(Kunde kunde);

    void edit(Kunde kunde);

    void remove(Kunde kunde);

    Kunde find(Object id);

    List<Kunde> findAll();

    List<Kunde> findRange(int[] range);

    int count();
    
}
