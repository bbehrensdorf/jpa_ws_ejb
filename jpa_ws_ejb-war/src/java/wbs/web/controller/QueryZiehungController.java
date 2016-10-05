package wbs.web.controller;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import wbs.lotto.domain.Ziehung;
import wbs.lotto.jpafeatures.NamedQueryLocal;
import wbs.lotto.persistence.ZiehungFacadeLocal;

@RequestScoped
@Named
public class QueryZiehungController {

    @EJB
    private NamedQueryLocal namedQueryLocal;

    private Date ziehungsdatum;
    private Date suchdatum;

    public NamedQueryLocal getNamedQueryLocal() {
        return namedQueryLocal;
    }

    public void setNamedQueryLocal(NamedQueryLocal namedQueryLocal) {
        this.namedQueryLocal = namedQueryLocal;
    }

    public Date getSuchdatum() {
        return suchdatum;
    }

    public void setSuchdatum(Date suchdatum) {
        this.suchdatum = suchdatum;
    }
    private Long zahlenAlsBits;
    private Integer superzahl;
    private Integer spiel77;
    private Integer super6;

    public void setZiehung(Ziehung ziehung) {
        this.ziehungsdatum = ziehung.getZiehungsdatum();
        this.zahlenAlsBits = ziehung.getZahlenAlsBits().longValue();
        this.superzahl = ziehung.getSuperzahl();
        this.spiel77 = ziehung.getSpiel77();
        this.super6 = ziehung.getSuper6();
    }

    public Date getZiehungsdatum() {
        return ziehungsdatum;
    }

    public void setZiehungsdatum(Date ziehungsdatum) {
        this.ziehungsdatum = ziehungsdatum;
    }

    public Long getZahlenAlsBits() {
        return zahlenAlsBits;
    }

    public void setZahlenAlsBits(Long zahlenAlsBits) {
        this.zahlenAlsBits = zahlenAlsBits;
    }

    public Integer getSuperzahl() {
        return superzahl;
    }

    public void setSuperzahl(Integer superzahl) {
        this.superzahl = superzahl;
    }

    public Integer getSpiel77() {
        return spiel77;
    }

    public void setSpiel77(Integer spiel77) {
        this.spiel77 = spiel77;
    }

    public Integer getSuper6() {
        return super6;
    }

    public void setSuper6(Integer super6) {
        this.super6 = super6;
    }

    public String senden() {
        List<Ziehung> l = namedQueryLocal.nq2(suchdatum);
        if (l != null && l.size() > 0) {
            setZiehung(l.get(0));
        }
        
        return "";
    }

    public void zeigZiehung(Date ziehungsDatum) {

        //TypedQuery<Ziehung> query = getEntityManager().createNamedQuery("Ziehung.findByZiehungsdatum", Ziehung.class);
        // query.setParameter("ziehungsdatum", ziehungsDatum);
        // query.setMaxResults(1);
        // setZiehung(query.getSingleResult());
    }

}
