/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Spiel;

/**
 *
 * @author Master
 */
@Local
public interface SpielFacadeLocal {

    void create(Spiel spiel);

    void edit(Spiel spiel);

    void remove(Spiel spiel);

    Spiel find(Object id);

    List<Spiel> findAll();

    List<Spiel> findRange(int[] range);

    int count();
    
}
