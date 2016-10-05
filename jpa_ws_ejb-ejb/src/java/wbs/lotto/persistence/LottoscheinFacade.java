/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wbs.lotto.domain.Lottoschein;

/**
 *
 * @author Master
 */
@Stateless
public class LottoscheinFacade extends AbstractFacade<Lottoschein> implements LottoscheinFacadeLocal {

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LottoscheinFacade() {
        super(Lottoschein.class);
    }
    
}
