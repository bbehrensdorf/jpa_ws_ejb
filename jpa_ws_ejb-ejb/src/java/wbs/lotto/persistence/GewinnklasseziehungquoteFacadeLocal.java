/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.List;
import javax.ejb.Local;
import wbs.lotto.domain.Gewinnklasseziehungquote;

/**
 *
 * @author Master
 */
@Local
public interface GewinnklasseziehungquoteFacadeLocal {

    void create(Gewinnklasseziehungquote gewinnklasseziehungquote);

    void edit(Gewinnklasseziehungquote gewinnklasseziehungquote);

    void remove(Gewinnklasseziehungquote gewinnklasseziehungquote);

    Gewinnklasseziehungquote find(Object id);

    List<Gewinnklasseziehungquote> findAll();

    List<Gewinnklasseziehungquote> findRange(int[] range);

    int count();
    
}
