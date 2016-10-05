/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ziehung")
@NamedQueries({
    @NamedQuery(name = "Ziehung.findAll", query = "SELECT z FROM Ziehung z"),
    @NamedQuery(name = "Ziehung.findByZiehungId", query = "SELECT z FROM Ziehung z WHERE z.ziehungId = :ziehungId"),
    @NamedQuery(name = "Ziehung.findByZiehungsdatum", query = "SELECT z FROM Ziehung z WHERE z.ziehungsdatum = :ziehungsdatum"),
    @NamedQuery(name = "Ziehung.findByZahlenAlsBits", query = "SELECT z FROM Ziehung z WHERE z.zahlenAlsBits = :zahlenAlsBits"),
    @NamedQuery(name = "Ziehung.findBySuperzahl", query = "SELECT z FROM Ziehung z WHERE z.superzahl = :superzahl"),
    @NamedQuery(name = "Ziehung.findBySpiel77", query = "SELECT z FROM Ziehung z WHERE z.spiel77 = :spiel77"),
    @NamedQuery(name = "Ziehung.findBySuper6", query = "SELECT z FROM Ziehung z WHERE z.super6 = :super6"),
    @NamedQuery(name = "Ziehung.findByEinsatzLotto", query = "SELECT z FROM Ziehung z WHERE z.einsatzLotto = :einsatzLotto"),
    @NamedQuery(name = "Ziehung.findByEinsatzSpiel77", query = "SELECT z FROM Ziehung z WHERE z.einsatzSpiel77 = :einsatzSpiel77"),
    @NamedQuery(name = "Ziehung.findByEinsatzSuper6", query = "SELECT z FROM Ziehung z WHERE z.einsatzSuper6 = :einsatzSuper6"),
    @NamedQuery(name = "Ziehung.findByStatus", query = "SELECT z FROM Ziehung z WHERE z.status = :status"),
    @NamedQuery(name = "Ziehung.findByCreated", query = "SELECT z FROM Ziehung z WHERE z.created = :created"),
    @NamedQuery(name = "Ziehung.findByLastModified", query = "SELECT z FROM Ziehung z WHERE z.lastModified = :lastModified"),
    @NamedQuery(name = "Ziehung.findByVersion", query = "SELECT z FROM Ziehung z WHERE z.version = :version")})
public class Ziehung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ZiehungId")
    private Long ziehungId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ziehungsdatum")
    @Temporal(TemporalType.DATE)
    private Date ziehungsdatum;
    @Column(name = "ZahlenAlsBits")
    private BigInteger zahlenAlsBits;
    @Column(name = "Superzahl")
    private Integer superzahl;
    @Column(name = "Spiel77")
    private Integer spiel77;
    @Column(name = "Super6")
    private Integer super6;
    @Column(name = "EinsatzLotto")
    private BigInteger einsatzLotto;
    @Column(name = "EinsatzSpiel77")
    private BigInteger einsatzSpiel77;
    @Column(name = "EinsatzSuper6")
    private BigInteger einsatzSuper6;
    @Column(name = "Status")
    private Integer status;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ziehungId")
    private List<Lottoscheinziehung> lottoscheinziehungList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ziehungId")
    private List<Jackpot> jackpotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ziehungId")
    private List<Gewinnklasseziehungquote> gewinnklasseziehungquoteList;

    public Ziehung() {
    }

    public Ziehung(Long ziehungId) {
        this.ziehungId = ziehungId;
    }

    public Ziehung(Long ziehungId, Date ziehungsdatum, Date created, Date lastModified) {
        this.ziehungId = ziehungId;
        this.ziehungsdatum = ziehungsdatum;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getZiehungId() {
        return ziehungId;
    }

    public void setZiehungId(Long ziehungId) {
        this.ziehungId = ziehungId;
    }

    public Date getZiehungsdatum() {
        return ziehungsdatum;
    }

    public void setZiehungsdatum(Date ziehungsdatum) {
        this.ziehungsdatum = ziehungsdatum;
    }

    public BigInteger getZahlenAlsBits() {
        return zahlenAlsBits;
    }

    public void setZahlenAlsBits(BigInteger zahlenAlsBits) {
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

    public BigInteger getEinsatzLotto() {
        return einsatzLotto;
    }

    public void setEinsatzLotto(BigInteger einsatzLotto) {
        this.einsatzLotto = einsatzLotto;
    }

    public BigInteger getEinsatzSpiel77() {
        return einsatzSpiel77;
    }

    public void setEinsatzSpiel77(BigInteger einsatzSpiel77) {
        this.einsatzSpiel77 = einsatzSpiel77;
    }

    public BigInteger getEinsatzSuper6() {
        return einsatzSuper6;
    }

    public void setEinsatzSuper6(BigInteger einsatzSuper6) {
        this.einsatzSuper6 = einsatzSuper6;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<Lottoscheinziehung> getLottoscheinziehungList() {
        return lottoscheinziehungList;
    }

    public void setLottoscheinziehungList(List<Lottoscheinziehung> lottoscheinziehungList) {
        this.lottoscheinziehungList = lottoscheinziehungList;
    }

    public List<Jackpot> getJackpotList() {
        return jackpotList;
    }

    public void setJackpotList(List<Jackpot> jackpotList) {
        this.jackpotList = jackpotList;
    }

    public List<Gewinnklasseziehungquote> getGewinnklasseziehungquoteList() {
        return gewinnklasseziehungquoteList;
    }

    public void setGewinnklasseziehungquoteList(List<Gewinnklasseziehungquote> gewinnklasseziehungquoteList) {
        this.gewinnklasseziehungquoteList = gewinnklasseziehungquoteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ziehungId != null ? ziehungId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ziehung)) {
            return false;
        }
        Ziehung other = (Ziehung) object;
        if ((this.ziehungId == null && other.ziehungId != null) || (this.ziehungId != null && !this.ziehungId.equals(other.ziehungId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Ziehung[ ziehungId=" + ziehungId + " ]";
    }
    
}
