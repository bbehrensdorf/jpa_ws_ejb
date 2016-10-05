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
import javax.validation.constraints.Size;

/**
 *
 * @author Master
 */
@Entity
@Table(name = "bankverbindung")
@NamedQueries({
    @NamedQuery(name = "Bankverbindung.findAll", query = "SELECT b FROM Bankverbindung b"),
    @NamedQuery(name = "Bankverbindung.findByBankverbindungId", query = "SELECT b FROM Bankverbindung b WHERE b.bankverbindungId = :bankverbindungId"),
    @NamedQuery(name = "Bankverbindung.findByBankverbindungNr", query = "SELECT b FROM Bankverbindung b WHERE b.bankverbindungNr = :bankverbindungNr"),
    @NamedQuery(name = "Bankverbindung.findByIban", query = "SELECT b FROM Bankverbindung b WHERE b.iban = :iban"),
    @NamedQuery(name = "Bankverbindung.findByBic", query = "SELECT b FROM Bankverbindung b WHERE b.bic = :bic"),
    @NamedQuery(name = "Bankverbindung.findByName", query = "SELECT b FROM Bankverbindung b WHERE b.name = :name"),
    @NamedQuery(name = "Bankverbindung.findByOrt", query = "SELECT b FROM Bankverbindung b WHERE b.ort = :ort"),
    @NamedQuery(name = "Bankverbindung.findByLand", query = "SELECT b FROM Bankverbindung b WHERE b.land = :land"),
    @NamedQuery(name = "Bankverbindung.findByCreated", query = "SELECT b FROM Bankverbindung b WHERE b.created = :created"),
    @NamedQuery(name = "Bankverbindung.findByLastModified", query = "SELECT b FROM Bankverbindung b WHERE b.lastModified = :lastModified"),
    @NamedQuery(name = "Bankverbindung.findByVersion", query = "SELECT b FROM Bankverbindung b WHERE b.version = :version")})
public class Bankverbindung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BankverbindungId")
    private Long bankverbindungId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BankverbindungNr")
    private int bankverbindungNr;
    @Size(max = 30)
    @Column(name = "IBAN")
    private String iban;
    @Size(max = 15)
    @Column(name = "BIC")
    private String bic;
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 255)
    @Column(name = "Ort")
    private String ort;
    @Size(max = 255)
    @Column(name = "Land")
    private String land;
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
    @JoinColumn(name = "KundeId", referencedColumnName = "KundeId")
    @ManyToOne(optional = false)
    private Kunde kundeId;

    public Bankverbindung() {
    }

    public Bankverbindung(Long bankverbindungId) {
        this.bankverbindungId = bankverbindungId;
    }

    public Bankverbindung(Long bankverbindungId, int bankverbindungNr, Date created, Date lastModified) {
        this.bankverbindungId = bankverbindungId;
        this.bankverbindungNr = bankverbindungNr;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getBankverbindungId() {
        return bankverbindungId;
    }

    public void setBankverbindungId(Long bankverbindungId) {
        this.bankverbindungId = bankverbindungId;
    }

    public int getBankverbindungNr() {
        return bankverbindungNr;
    }

    public void setBankverbindungNr(int bankverbindungNr) {
        this.bankverbindungNr = bankverbindungNr;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
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

    public Kunde getKundeId() {
        return kundeId;
    }

    public void setKundeId(Kunde kundeId) {
        this.kundeId = kundeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankverbindungId != null ? bankverbindungId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bankverbindung)) {
            return false;
        }
        Bankverbindung other = (Bankverbindung) object;
        if ((this.bankverbindungId == null && other.bankverbindungId != null) || (this.bankverbindungId != null && !this.bankverbindungId.equals(other.bankverbindungId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Bankverbindung[ bankverbindungId=" + bankverbindungId + " ]";
    }
    
}
