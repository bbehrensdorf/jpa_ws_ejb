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
@Table(name = "adresse")
@NamedQueries({
    @NamedQuery(name = "Adresse.findAll", query = "SELECT a FROM Adresse a"),
    @NamedQuery(name = "Adresse.findByAdresseId", query = "SELECT a FROM Adresse a WHERE a.adresseId = :adresseId"),
    @NamedQuery(name = "Adresse.findByAdresseNr", query = "SELECT a FROM Adresse a WHERE a.adresseNr = :adresseNr"),
    @NamedQuery(name = "Adresse.findByStrasse", query = "SELECT a FROM Adresse a WHERE a.strasse = :strasse"),
    @NamedQuery(name = "Adresse.findByHausnummer", query = "SELECT a FROM Adresse a WHERE a.hausnummer = :hausnummer"),
    @NamedQuery(name = "Adresse.findByAdresszusatz", query = "SELECT a FROM Adresse a WHERE a.adresszusatz = :adresszusatz"),
    @NamedQuery(name = "Adresse.findByPlz", query = "SELECT a FROM Adresse a WHERE a.plz = :plz"),
    @NamedQuery(name = "Adresse.findByOrt", query = "SELECT a FROM Adresse a WHERE a.ort = :ort"),
    @NamedQuery(name = "Adresse.findByLand", query = "SELECT a FROM Adresse a WHERE a.land = :land"),
    @NamedQuery(name = "Adresse.findByCreated", query = "SELECT a FROM Adresse a WHERE a.created = :created"),
    @NamedQuery(name = "Adresse.findByLastModified", query = "SELECT a FROM Adresse a WHERE a.lastModified = :lastModified"),
    @NamedQuery(name = "Adresse.findByVersion", query = "SELECT a FROM Adresse a WHERE a.version = :version")})
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AdresseId")
    private Long adresseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AdresseNr")
    private int adresseNr;
    @Size(max = 255)
    @Column(name = "Strasse")
    private String strasse;
    @Size(max = 100)
    @Column(name = "Hausnummer")
    private String hausnummer;
    @Size(max = 500)
    @Column(name = "Adresszusatz")
    private String adresszusatz;
    @Size(max = 10)
    @Column(name = "PLZ")
    private String plz;
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

    public Adresse() {
    }

    public Adresse(Long adresseId) {
        this.adresseId = adresseId;
    }

    public Adresse(Long adresseId, int adresseNr, Date created, Date lastModified) {
        this.adresseId = adresseId;
        this.adresseNr = adresseNr;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    public int getAdresseNr() {
        return adresseNr;
    }

    public void setAdresseNr(int adresseNr) {
        this.adresseNr = adresseNr;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getAdresszusatz() {
        return adresszusatz;
    }

    public void setAdresszusatz(String adresszusatz) {
        this.adresszusatz = adresszusatz;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
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
        hash += (adresseId != null ? adresseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresse)) {
            return false;
        }
        Adresse other = (Adresse) object;
        if ((this.adresseId == null && other.adresseId != null) || (this.adresseId != null && !this.adresseId.equals(other.adresseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Adresse[ adresseId=" + adresseId + " ]";
    }
    
}
