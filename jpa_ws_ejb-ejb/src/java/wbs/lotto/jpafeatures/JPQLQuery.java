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
import javax.persistence.TypedQuery;
import wbs.lotto.domain.Gebuehr;
import wbs.lotto.domain.Lottoscheinziehung;
import wbs.lotto.domain.Ziehung;

/**
 *
 * @author Bernd
 */
@Stateless
public class JPQLQuery implements JPQLQueryLocal {

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    public Gebuehr gebuehr(Date datum) {
        String jpql = "SELECT g FROM Gebuehr g WHERE g.gueltigAb <= :datum AND g.gueltigBis >= :datum";
        TypedQuery<Gebuehr> query = em.createQuery(jpql, Gebuehr.class);
        query.setParameter("datum", datum);
        return query.getSingleResult();
    }

    @Override
    public List<Lottoscheinziehung> lzList(Ziehung ziehung, int maxResults) {
        String jpql = "SELECT l FROM Lottoscheinziehung l WHERE l.ziehungId = :ziehungId AND l.isAbgeschlossen IS NULL";
        TypedQuery query = em.createQuery(jpql, Lottoscheinziehung.class);
        query.setParameter("ziehungId", ziehung);
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }

        return query.getResultList();

    }

}
