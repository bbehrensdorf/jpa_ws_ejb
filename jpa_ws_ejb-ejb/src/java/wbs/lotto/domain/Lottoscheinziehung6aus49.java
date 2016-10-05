/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Master
 */
@Entity
@Table(name = "lottoscheinziehung6aus49")
@NamedQueries({
    @NamedQuery(name = "Lottoscheinziehung6aus49.findAll", query = "SELECT l FROM Lottoscheinziehung6aus49 l"),
    @NamedQuery(name = "Lottoscheinziehung6aus49.findByLottoscheinZiehung6Aus49Id", query = "SELECT l FROM Lottoscheinziehung6aus49 l WHERE l.lottoscheinZiehung6Aus49Id = :lottoscheinZiehung6Aus49Id"),
    @NamedQuery(name = "Lottoscheinziehung6aus49.findByTippNr", query = "SELECT l FROM Lottoscheinziehung6aus49 l WHERE l.tippNr = :tippNr"),
    @NamedQuery(name = "Lottoscheinziehung6aus49.findByCreated", query = "SELECT l FROM Lottoscheinziehung6aus49 l WHERE l.created = :created"),
    @NamedQuery(name = "Lottoscheinziehung6aus49.findByLastModified", query = "SELECT l FROM Lottoscheinziehung6aus49 l WHERE l.lastModified = :lastModified"),
    @NamedQuery(name = "Lottoscheinziehung6aus49.findByVersion", query = "SELECT l FROM Lottoscheinziehung6aus49 l WHERE l.version = :version")})
public class Lottoscheinziehung6aus49 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LottoscheinZiehung6Aus49Id")
    private Long lottoscheinZiehung6Aus49Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TippNr")
    private int tippNr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LastModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Column(name = "Version")
    private Integer version;
    @JoinColumn(name = "GewinnklasseId", referencedColumnName = "GewinnklasseId")
    @ManyToOne(optional = false)
    private Gewinnklasse gewinnklasseId;
    @JoinColumn(name = "LottoscheinZiehungId", referencedColumnName = "LottoscheinZiehungId")
    @ManyToOne(optional = false)
    private Lottoscheinziehung lottoscheinZiehungId;

    public Lottoscheinziehung6aus49() {
    }

    public Lottoscheinziehung6aus49(Long lottoscheinZiehung6Aus49Id) {
        this.lottoscheinZiehung6Aus49Id = lottoscheinZiehung6Aus49Id;
    }

    public Lottoscheinziehung6aus49(Long lottoscheinZiehung6Aus49Id, int tippNr, Date created, Date lastModified) {
        this.lottoscheinZiehung6Aus49Id = lottoscheinZiehung6Aus49Id;
        this.tippNr = tippNr;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getLottoscheinZiehung6Aus49Id() {
        return lottoscheinZiehung6Aus49Id;
    }

    public void setLottoscheinZiehung6Aus49Id(Long lottoscheinZiehung6Aus49Id) {
        this.lottoscheinZiehung6Aus49Id = lottoscheinZiehung6Aus49Id;
    }

    public int getTippNr() {
        return tippNr;
    }

    public void setTippNr(int tippNr) {
        this.tippNr = tippNr;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Gewinnklasse getGewinnklasseId() {
        return gewinnklasseId;
    }

    public void setGewinnklasseId(Gewinnklasse gewinnklasseId) {
        this.gewinnklasseId = gewinnklasseId;
    }

    public Lottoscheinziehung getLottoscheinZiehungId() {
        return lottoscheinZiehungId;
    }

    public void setLottoscheinZiehungId(Lottoscheinziehung lottoscheinZiehungId) {
        this.lottoscheinZiehungId = lottoscheinZiehungId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lottoscheinZiehung6Aus49Id != null ? lottoscheinZiehung6Aus49Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lottoscheinziehung6aus49)) {
            return false;
        }
        Lottoscheinziehung6aus49 other = (Lottoscheinziehung6aus49) object;
        if ((this.lottoscheinZiehung6Aus49Id == null && other.lottoscheinZiehung6Aus49Id != null) || (this.lottoscheinZiehung6Aus49Id != null && !this.lottoscheinZiehung6Aus49Id.equals(other.lottoscheinZiehung6Aus49Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Lottoscheinziehung6aus49[ lottoscheinZiehung6Aus49Id=" + lottoscheinZiehung6Aus49Id + " ]";
    }
    
}
