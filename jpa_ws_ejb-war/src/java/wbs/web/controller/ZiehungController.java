package wbs.web.controller;

import java.math.BigInteger;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import wbs.lotto.domain.Ziehung;
import wbs.lotto.persistence.ZiehungFacadeLocal;

@RequestScoped
@Named
public class ZiehungController {
  
    @EJB
    private ZiehungFacadeLocal ziehungFacadeLocal;
    
    private Date ziehungsdatum;
    private Long zahlenAlsBits;
    private Integer superzahl;
    private Integer spiel77;
    private Integer super6;

    public Long getZahlen6aus49() {
        return zahlenAlsBits;
    }

    public void setZahlen6aus49(Long zahlen6aus49) {
        this.zahlenAlsBits = zahlen6aus49;
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
    
    

    public Date getZiehungsdatum() {
        return ziehungsdatum;
    }

    public void setZiehungsdatum(Date ziehungsDatum) {
        this.ziehungsdatum = ziehungsDatum;
    }
    

    public void checkSuper6(FacesContext context, UIComponent component, Object value) {
    String formId = "ziehungerfassen";
    String inputId = "super6";
    //String clientId = formId + ":" + inputId;
    String clientId = inputId;
    
    String message = "Fehler: Es müssen 6 Ziffern sein.";
 
    FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(message));
  }
    public String senden() {
        Date date = new Date(); 
        Ziehung ziehung=new Ziehung();
        ziehung.setZiehungsdatum(this.ziehungsdatum);
        ziehung.setZahlenAlsBits(BigInteger.valueOf(this.zahlenAlsBits));
        ziehung.setSuperzahl(this.superzahl);
        ziehung.setSpiel77(this.spiel77);
        ziehung.setSuper6(this.super6);
        ziehung.setCreated(date);
        ziehung.setLastModified(date);
        ziehungFacadeLocal.create(ziehung);
        return "ziehungeingetragen";
    }
    
}
