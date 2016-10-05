/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Bankverbindung;

/**
 *
 * @author Master
 */
@Local
public interface BankverbindungFacadeLocal {

    void create(Bankverbindung bankverbindung);

    void edit(Bankverbindung bankverbindung);

    void remove(Bankverbindung bankverbindung);

    Bankverbindung find(Object id);

    List<Bankverbindung> findAll();

    List<Bankverbindung> findRange(int[] range);

    int count();
    
}
