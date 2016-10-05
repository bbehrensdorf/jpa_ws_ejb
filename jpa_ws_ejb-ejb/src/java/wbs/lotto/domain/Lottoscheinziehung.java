/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Master
 */
@Entity
@Table(name = "lottoscheinziehung")
@NamedQueries({
    @NamedQuery(name = "Lottoscheinziehung.findAll", query = "SELECT l FROM Lottoscheinziehung l"),
    @NamedQuery(name = "Lottoscheinziehung.findByLottoscheinZiehungId", query = "SELECT l FROM Lottoscheinziehung l WHERE l.lottoscheinZiehungId = :lottoscheinZiehungId"),
    @NamedQuery(name = "Lottoscheinziehung.findByZiehungNr", query = "SELECT l FROM Lottoscheinziehung l WHERE l.ziehungNr = :ziehungNr"),
    @NamedQuery(name = "Lottoscheinziehung.findByIsAbgeschlossen", query = "SELECT l FROM Lottoscheinziehung l WHERE l.isAbgeschlossen = :isAbgeschlossen"),
    @NamedQuery(name = "Lottoscheinziehung.findByIsLetzteZiehung", query = "SELECT l FROM Lottoscheinziehung l WHERE l.isLetzteZiehung = :isLetzteZiehung"),
    @NamedQuery(name = "Lottoscheinziehung.findByCreated", query = "SELECT l FROM Lottoscheinziehung l WHERE l.created = :created"),
    @NamedQuery(name = "Lottoscheinziehung.findByLastModified", query = "SELECT l FROM Lottoscheinziehung l WHERE l.lastModified = :lastModified"),
    @NamedQuery(name = "Lottoscheinziehung.findByVersion", query = "SELECT l FROM Lottoscheinziehung l WHERE l.version = :version")})
public class Lottoscheinziehung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LottoscheinZiehungId")
    private Long lottoscheinZiehungId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZiehungNr")
    private int ziehungNr;
    @Column(name = "IsAbgeschlossen")
    private Boolean isAbgeschlossen;
    @Column(name = "IsLetzteZiehung")
    private Boolean isLetzteZiehung;
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
    @JoinColumn(name = "GewinnklasseIdSpiel77", referencedColumnName = "GewinnklasseId")
    @ManyToOne
    private Gewinnklasse gewinnklasseIdSpiel77;
    @JoinColumn(name = "GewinnklasseIdSuper6", referencedColumnName = "GewinnklasseId")
    @ManyToOne
    private Gewinnklasse gewinnklasseIdSuper6;
    @JoinColumn(name = "LottoscheinId", referencedColumnName = "LottoscheinId")
    @ManyToOne(optional = false)
    private Lottoschein lottoscheinId;
    @JoinColumn(name = "ZiehungId", referencedColumnName = "ZiehungId")
    @ManyToOne(optional = false)
    private Ziehung ziehungId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lottoscheinZiehungId")
    private List<Lottoscheinziehung6aus49> lottoscheinziehung6aus49List;

    public Lottoscheinziehung() {
    }

    public Lottoscheinziehung(Long lottoscheinZiehungId) {
        this.lottoscheinZiehungId = lottoscheinZiehungId;
    }

    public Lottoscheinziehung(Long lottoscheinZiehungId, int ziehungNr, Date created, Date lastModified) {
        this.lottoscheinZiehungId = lottoscheinZiehungId;
        this.ziehungNr = ziehungNr;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getLottoscheinZiehungId() {
        return lottoscheinZiehungId;
    }

    public void setLottoscheinZiehungId(Long lottoscheinZiehungId) {
        this.lottoscheinZiehungId = lottoscheinZiehungId;
    }

    public int getZiehungNr() {
        return ziehungNr;
    }

    public void setZiehungNr(int ziehungNr) {
        this.ziehungNr = ziehungNr;
    }

    public Boolean getIsAbgeschlossen() {
        return isAbgeschlossen;
    }

    public void setIsAbgeschlossen(Boolean isAbgeschlossen) {
        this.isAbgeschlossen = isAbgeschlossen;
    }

    public Boolean getIsLetzteZiehung() {
        return isLetzteZiehung;
    }

    public void setIsLetzteZiehung(Boolean isLetzteZiehung) {
        this.isLetzteZiehung = isLetzteZiehung;
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

    public Gewinnklasse getGewinnklasseIdSpiel77() {
        return gewinnklasseIdSpiel77;
    }

    public void setGewinnklasseIdSpiel77(Gewinnklasse gewinnklasseIdSpiel77) {
        this.gewinnklasseIdSpiel77 = gewinnklasseIdSpiel77;
    }

    public Gewinnklasse getGewinnklasseIdSuper6() {
        return gewinnklasseIdSuper6;
    }

    public void setGewinnklasseIdSuper6(Gewinnklasse gewinnklasseIdSuper6) {
        this.gewinnklasseIdSuper6 = gewinnklasseIdSuper6;
    }

    public Lottoschein getLottoscheinId() {
        return lottoscheinId;
    }

    public void setLottoscheinId(Lottoschein lottoscheinId) {
        this.lottoscheinId = lottoscheinId;
    }

    public Ziehung getZiehungId() {
        return ziehungId;
    }

    public void setZiehungId(Ziehung ziehungId) {
        this.ziehungId = ziehungId;
    }

    public List<Lottoscheinziehung6aus49> getLottoscheinziehung6aus49List() {
        return lottoscheinziehung6aus49List;
    }

    public void setLottoscheinziehung6aus49List(List<Lottoscheinziehung6aus49> lottoscheinziehung6aus49List) {
        this.lottoscheinziehung6aus49List = lottoscheinziehung6aus49List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lottoscheinZiehungId != null ? lottoscheinZiehungId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lottoscheinziehung)) {
            return false;
        }
        Lottoscheinziehung other = (Lottoscheinziehung) object;
        if ((this.lottoscheinZiehungId == null && other.lottoscheinZiehungId != null) || (this.lottoscheinZiehungId != null && !this.lottoscheinZiehungId.equals(other.lottoscheinZiehungId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Lottoscheinziehung[ lottoscheinZiehungId=" + lottoscheinZiehungId + " ]";
    }
    
}
