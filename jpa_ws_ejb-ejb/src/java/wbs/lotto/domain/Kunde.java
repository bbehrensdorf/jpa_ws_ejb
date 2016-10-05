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
import javax.validation.constraints.Size;

/**
 *
 * @author Master
 */
@Entity
@Table(name = "kunde")
@NamedQueries({
    @NamedQuery(name = "Kunde.findAll", query = "SELECT k FROM Kunde k"),
    @NamedQuery(name = "Kunde.findByKundeId", query = "SELECT k FROM Kunde k WHERE k.kundeId = :kundeId"),
    @NamedQuery(name = "Kunde.findByName", query = "SELECT k FROM Kunde k WHERE k.name = :name"),
    @NamedQuery(name = "Kunde.findByVorname", query = "SELECT k FROM Kunde k WHERE k.vorname = :vorname"),
    @NamedQuery(name = "Kunde.findByEmail", query = "SELECT k FROM Kunde k WHERE k.email = :email"),
    @NamedQuery(name = "Kunde.findByGuthaben", query = "SELECT k FROM Kunde k WHERE k.guthaben = :guthaben"),
    @NamedQuery(name = "Kunde.findByDispo", query = "SELECT k FROM Kunde k WHERE k.dispo = :dispo"),
    @NamedQuery(name = "Kunde.findByGesperrt", query = "SELECT k FROM Kunde k WHERE k.gesperrt = :gesperrt"),
    @NamedQuery(name = "Kunde.findByIsAnnahmestelle", query = "SELECT k FROM Kunde k WHERE k.isAnnahmestelle = :isAnnahmestelle"),
    @NamedQuery(name = "Kunde.findByCreated", query = "SELECT k FROM Kunde k WHERE k.created = :created"),
    @NamedQuery(name = "Kunde.findByLastModified", query = "SELECT k FROM Kunde k WHERE k.lastModified = :lastModified"),
    @NamedQuery(name = "Kunde.findByVersion", query = "SELECT k FROM Kunde k WHERE k.version = :version")})
public class Kunde implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "KundeId")
    private Long kundeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 255)
    @Column(name = "Vorname")
    private String vorname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Email")
    private String email;
    @Column(name = "Guthaben")
    private BigInteger guthaben;
    @Column(name = "Dispo")
    private BigInteger dispo;
    @Column(name = "Gesperrt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gesperrt;
    @Column(name = "IsAnnahmestelle")
    private Boolean isAnnahmestelle;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kundeId")
    private List<Adresse> adresseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kundeId")
    private List<Lottoschein> lottoscheinList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kundeId")
    private List<Bankverbindung> bankverbindungList;

    public Kunde() {
    }

    public Kunde(Long kundeId) {
        this.kundeId = kundeId;
    }

    public Kunde(Long kundeId, String name, String email, Date created, Date lastModified) {
        this.kundeId = kundeId;
        this.name = name;
        this.email = email;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getKundeId() {
        return kundeId;
    }

    public void setKundeId(Long kundeId) {
        this.kundeId = kundeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getGuthaben() {
        return guthaben;
    }

    public void setGuthaben(BigInteger guthaben) {
        this.guthaben = guthaben;
    }

    public BigInteger getDispo() {
        return dispo;
    }

    public void setDispo(BigInteger dispo) {
        this.dispo = dispo;
    }

    public Date getGesperrt() {
        return gesperrt;
    }

    public void setGesperrt(Date gesperrt) {
        this.gesperrt = gesperrt;
    }

    public Boolean getIsAnnahmestelle() {
        return isAnnahmestelle;
    }

    public void setIsAnnahmestelle(Boolean isAnnahmestelle) {
        this.isAnnahmestelle = isAnnahmestelle;
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

    public List<Adresse> getAdresseList() {
        return adresseList;
    }

    public void setAdresseList(List<Adresse> adresseList) {
        this.adresseList = adresseList;
    }

    public List<Lottoschein> getLottoscheinList() {
        return lottoscheinList;
    }

    public void setLottoscheinList(List<Lottoschein> lottoscheinList) {
        this.lottoscheinList = lottoscheinList;
    }

    public List<Bankverbindung> getBankverbindungList() {
        return bankverbindungList;
    }

    public void setBankverbindungList(List<Bankverbindung> bankverbindungList) {
        this.bankverbindungList = bankverbindungList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kundeId != null ? kundeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kunde)) {
            return false;
        }
        Kunde other = (Kunde) object;
        if ((this.kundeId == null && other.kundeId != null) || (this.kundeId != null && !this.kundeId.equals(other.kundeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Kunde[ kundeId=" + kundeId + " ]";
    }
    
}
