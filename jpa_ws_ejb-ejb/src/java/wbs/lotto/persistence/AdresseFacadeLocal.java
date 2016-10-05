/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Adresse;

/**
 *
 * @author Master
 */
@Local
public interface AdresseFacadeLocal {

    void create(Adresse adresse);

    void edit(Adresse adresse);

    void remove(Adresse adresse);

    Adresse find(Object id);

    List<Adresse> findAll();

    List<Adresse> findRange(int[] range);

    int count();
    
}
