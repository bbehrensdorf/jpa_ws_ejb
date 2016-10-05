/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Gebuehr;

/**
 *
 * @author Master
 */
@Local
public interface GebuehrFacadeLocal {

    void create(Gebuehr gebuehr);

    void edit(Gebuehr gebuehr);

    void remove(Gebuehr gebuehr);

    Gebuehr find(Object id);

    List<Gebuehr> findAll();

    List<Gebuehr> findRange(int[] range);

    int count();
    
    Gebuehr getCurrentGebuehr();
    
}
