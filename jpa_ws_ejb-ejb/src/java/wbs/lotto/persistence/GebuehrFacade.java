/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.persistence;

import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import wbs.lotto.domain.Gebuehr;

/**
 *
 * @author Master
 */
@Stateless
public class GebuehrFacade extends AbstractFacade<Gebuehr> implements GebuehrFacadeLocal {

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GebuehrFacade() {
        super(Gebuehr.class);
    }

    @Override
    public Gebuehr getCurrentGebuehr() {
        Date date = new Date();
        TypedQuery<Gebuehr> query = em.createQuery("SELECT g FROM Gebuehr g WHERE g.gueltigAb <= :datum AND g.gueltigBis > :datum", Gebuehr.class);
        query.setParameter("datum", date);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
