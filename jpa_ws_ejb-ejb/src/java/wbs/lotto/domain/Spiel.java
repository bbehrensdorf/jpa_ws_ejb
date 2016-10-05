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
@Table(name = "spiel")
@NamedQueries({
    @NamedQuery(name = "Spiel.findAll", query = "SELECT s FROM Spiel s"),
    @NamedQuery(name = "Spiel.findBySpielId", query = "SELECT s FROM Spiel s WHERE s.spielId = :spielId"),
    @NamedQuery(name = "Spiel.findByName", query = "SELECT s FROM Spiel s WHERE s.name = :name"),
    @NamedQuery(name = "Spiel.findByBeschreibung", query = "SELECT s FROM Spiel s WHERE s.beschreibung = :beschreibung"),
    @NamedQuery(name = "Spiel.findByPfadAnleitung", query = "SELECT s FROM Spiel s WHERE s.pfadAnleitung = :pfadAnleitung"),
    @NamedQuery(name = "Spiel.findByCreated", query = "SELECT s FROM Spiel s WHERE s.created = :created"),
    @NamedQuery(name = "Spiel.findByLastModified", query = "SELECT s FROM Spiel s WHERE s.lastModified = :lastModified"),
    @NamedQuery(name = "Spiel.findByVersion", query = "SELECT s FROM Spiel s WHERE s.version = :version")})
public class Spiel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SpielId")
    private Long spielId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1023)
    @Column(name = "Beschreibung")
    private String beschreibung;
    @Size(max = 255)
    @Column(name = "PfadAnleitung")
    private String pfadAnleitung;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spielId")
    private List<Gewinnklasse> gewinnklasseList;

    public Spiel() {
    }

    public Spiel(Long spielId) {
        this.spielId = spielId;
    }

    public Spiel(Long spielId, String name, String beschreibung, Date created, Date lastModified) {
        this.spielId = spielId;
        this.name = name;
        this.beschreibung = beschreibung;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getSpielId() {
        return spielId;
    }

    public void setSpielId(Long spielId) {
        this.spielId = spielId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getPfadAnleitung() {
        return pfadAnleitung;
    }

    public void setPfadAnleitung(String pfadAnleitung) {
        this.pfadAnleitung = pfadAnleitung;
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

    public List<Gewinnklasse> getGewinnklasseList() {
        return gewinnklasseList;
    }

    public void setGewinnklasseList(List<Gewinnklasse> gewinnklasseList) {
        this.gewinnklasseList = gewinnklasseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spielId != null ? spielId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spiel)) {
            return false;
        }
        Spiel other = (Spiel) object;
        if ((this.spielId == null && other.spielId != null) || (this.spielId != null && !this.spielId.equals(other.spielId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Spiel[ spielId=" + spielId + " ]";
    }
    
}
