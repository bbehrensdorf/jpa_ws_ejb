/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.domain;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "jackpot")
@NamedQueries({
    @NamedQuery(name = "Jackpot.findAll", query = "SELECT j FROM Jackpot j"),
    @NamedQuery(name = "Jackpot.findByJackpotId", query = "SELECT j FROM Jackpot j WHERE j.jackpotId = :jackpotId"),
    @NamedQuery(name = "Jackpot.findByAnzahlZiehungen", query = "SELECT j FROM Jackpot j WHERE j.anzahlZiehungen = :anzahlZiehungen"),
    @NamedQuery(name = "Jackpot.findByBetrag", query = "SELECT j FROM Jackpot j WHERE j.betrag = :betrag"),
    @NamedQuery(name = "Jackpot.findByBetragKumuliert", query = "SELECT j FROM Jackpot j WHERE j.betragKumuliert = :betragKumuliert"),
    @NamedQuery(name = "Jackpot.findByCreated", query = "SELECT j FROM Jackpot j WHERE j.created = :created"),
    @NamedQuery(name = "Jackpot.findByLastModified", query = "SELECT j FROM Jackpot j WHERE j.lastModified = :lastModified"),
    @NamedQuery(name = "Jackpot.findByVersion", query = "SELECT j FROM Jackpot j WHERE j.version = :version")})
public class Jackpot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "JackpotId")
    private Long jackpotId;
    @Column(name = "AnzahlZiehungen")
    private Integer anzahlZiehungen;
    @Column(name = "Betrag")
    private BigInteger betrag;
    @Column(name = "BetragKumuliert")
    private BigInteger betragKumuliert;
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
    @ManyToOne
    private Gewinnklasse gewinnklasseId;
    @JoinColumn(name = "ZiehungId", referencedColumnName = "ZiehungId")
    @ManyToOne(optional = false)
    private Ziehung ziehungId;

    public Jackpot() {
    }

    public Jackpot(Long jackpotId) {
        this.jackpotId = jackpotId;
    }

    public Jackpot(Long jackpotId, Date created, Date lastModified) {
        this.jackpotId = jackpotId;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(Long jackpotId) {
        this.jackpotId = jackpotId;
    }

    public Integer getAnzahlZiehungen() {
        return anzahlZiehungen;
    }

    public void setAnzahlZiehungen(Integer anzahlZiehungen) {
        this.anzahlZiehungen = anzahlZiehungen;
    }

    public BigInteger getBetrag() {
        return betrag;
    }

    public void setBetrag(BigInteger betrag) {
        this.betrag = betrag;
    }

    public BigInteger getBetragKumuliert() {
        return betragKumuliert;
    }

    public void setBetragKumuliert(BigInteger betragKumuliert) {
        this.betragKumuliert = betragKumuliert;
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

    public Ziehung getZiehungId() {
        return ziehungId;
    }

    public void setZiehungId(Ziehung ziehungId) {
        this.ziehungId = ziehungId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jackpotId != null ? jackpotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jackpot)) {
            return false;
        }
        Jackpot other = (Jackpot) object;
        if ((this.jackpotId == null && other.jackpotId != null) || (this.jackpotId != null && !this.jackpotId.equals(other.jackpotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Jackpot[ jackpotId=" + jackpotId + " ]";
    }
    
}
