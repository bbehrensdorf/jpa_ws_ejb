/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.jpafeatures;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import wbs.lotto.domain.Ziehung;

@Stateless
public class NamedQuery implements NamedQueryLocal {

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    public List nq1(String land) {
        return em.createNamedQuery("Adresse.findByLand").setParameter("land", land).getResultList();
    }

    public List nq2(Date datum) {
        return em.createNamedQuery("Ziehung.findByZiehungsdatum").setParameter("ziehungsdatum", datum).getResultList();
    }

    public List lottoscheinNachZiehung(long ziehungId) {
        return em.createNamedQuery("Lottoschein.findByZiehungId").setParameter("ziehungId", ziehungId).getResultList();
    }

    public List lottoscheinZiehungNachZiehung(long ziehungId) {
        List resultList = em.createNamedQuery("Lottoscheinziehung.findByLottoscheinZiehungId").setParameter("lottoscheinZiehungId", ziehungId).getResultList();
        System.out.println("Zihung Id:" + ziehungId + " - Länge Resultlist: " + resultList.size());
        return resultList;
    }

    public Ziehung getZiehungById(long ziehungId) {
        
        TypedQuery<Ziehung> query = em.createNamedQuery("Ziehung.findByZiehungId",Ziehung.class);
        query.setParameter("ziehungId", ziehungId);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
