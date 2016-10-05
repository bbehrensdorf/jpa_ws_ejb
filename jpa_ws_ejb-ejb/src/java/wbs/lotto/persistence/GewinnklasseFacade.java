/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wbs.lotto.domain.Gewinnklasse;

/**
 *
 * @author Master
 */
@Stateless
public class GewinnklasseFacade extends AbstractFacade<Gewinnklasse> implements GewinnklasseFacadeLocal {

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GewinnklasseFacade() {
        super(Gewinnklasse.class);
    }
    
}
