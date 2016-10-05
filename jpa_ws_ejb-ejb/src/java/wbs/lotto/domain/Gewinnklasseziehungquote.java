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
@Table(name = "gewinnklasseziehungquote")
@NamedQueries({
    @NamedQuery(name = "Gewinnklasseziehungquote.findAll", query = "SELECT g FROM Gewinnklasseziehungquote g"),
    @NamedQuery(name = "Gewinnklasseziehungquote.findByGewinnklasseZiehungQuoteId", query = "SELECT g FROM Gewinnklasseziehungquote g WHERE g.gewinnklasseZiehungQuoteId = :gewinnklasseZiehungQuoteId"),
    @NamedQuery(name = "Gewinnklasseziehungquote.findByAnzahlGewinner", query = "SELECT g FROM Gewinnklasseziehungquote g WHERE g.anzahlGewinner = :anzahlGewinner"),
    @NamedQuery(name = "Gewinnklasseziehungquote.findByQuote", query = "SELECT g FROM Gewinnklasseziehungquote g WHERE g.quote = :quote"),
    @NamedQuery(name = "Gewinnklasseziehungquote.findByCreated", query = "SELECT g FROM Gewinnklasseziehungquote g WHERE g.created = :created"),
    @NamedQuery(name = "Gewinnklasseziehungquote.findByLastModified", query = "SELECT g FROM Gewinnklasseziehungquote g WHERE g.lastModified = :lastModified"),
    @NamedQuery(name = "Gewinnklasseziehungquote.findByVersion", query = "SELECT g FROM Gewinnklasseziehungquote g WHERE g.version = :version")})
public class Gewinnklasseziehungquote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GewinnklasseZiehungQuoteId")
    private Long gewinnklasseZiehungQuoteId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AnzahlGewinner")
    private int anzahlGewinner;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quote")
    private long quote;
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
    @JoinColumn(name = "ZiehungId", referencedColumnName = "ZiehungId")
    @ManyToOne(optional = false)
    private Ziehung ziehungId;

    public Gewinnklasseziehungquote() {
    }

    public Gewinnklasseziehungquote(Long gewinnklasseZiehungQuoteId) {
        this.gewinnklasseZiehungQuoteId = gewinnklasseZiehungQuoteId;
    }

    public Gewinnklasseziehungquote(Long gewinnklasseZiehungQuoteId, int anzahlGewinner, long quote, Date created, Date lastModified) {
        this.gewinnklasseZiehungQuoteId = gewinnklasseZiehungQuoteId;
        this.anzahlGewinner = anzahlGewinner;
        this.quote = quote;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getGewinnklasseZiehungQuoteId() {
        return gewinnklasseZiehungQuoteId;
    }

    public void setGewinnklasseZiehungQuoteId(Long gewinnklasseZiehungQuoteId) {
        this.gewinnklasseZiehungQuoteId = gewinnklasseZiehungQuoteId;
    }

    public int getAnzahlGewinner() {
        return anzahlGewinner;
    }

    public void setAnzahlGewinner(int anzahlGewinner) {
        this.anzahlGewinner = anzahlGewinner;
    }

    public long getQuote() {
        return quote;
    }

    public void setQuote(long quote) {
        this.quote = quote;
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
        hash += (gewinnklasseZiehungQuoteId != null ? gewinnklasseZiehungQuoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gewinnklasseziehungquote)) {
            return false;
        }
        Gewinnklasseziehungquote other = (Gewinnklasseziehungquote) object;
        if ((this.gewinnklasseZiehungQuoteId == null && other.gewinnklasseZiehungQuoteId != null) || (this.gewinnklasseZiehungQuoteId != null && !this.gewinnklasseZiehungQuoteId.equals(other.gewinnklasseZiehungQuoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Gewinnklasseziehungquote[ gewinnklasseZiehungQuoteId=" + gewinnklasseZiehungQuoteId + " ]";
    }
    
}
