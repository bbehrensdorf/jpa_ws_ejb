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
@Table(name = "gebuehr")
@NamedQueries({
    @NamedQuery(name = "Gebuehr.findAll", query = "SELECT g FROM Gebuehr g"),
    @NamedQuery(name = "Gebuehr.findByGebuehrId", query = "SELECT g FROM Gebuehr g WHERE g.gebuehrId = :gebuehrId"),
    @NamedQuery(name = "Gebuehr.findByGrundgebuehr", query = "SELECT g FROM Gebuehr g WHERE g.grundgebuehr = :grundgebuehr"),
    @NamedQuery(name = "Gebuehr.findByEinsatzProTipp", query = "SELECT g FROM Gebuehr g WHERE g.einsatzProTipp = :einsatzProTipp"),
    @NamedQuery(name = "Gebuehr.findByEinsatzSpiel77", query = "SELECT g FROM Gebuehr g WHERE g.einsatzSpiel77 = :einsatzSpiel77"),
    @NamedQuery(name = "Gebuehr.findByEinsatzSuper6", query = "SELECT g FROM Gebuehr g WHERE g.einsatzSuper6 = :einsatzSuper6"),
    @NamedQuery(name = "Gebuehr.findByGueltigAb", query = "SELECT g FROM Gebuehr g WHERE g.gueltigAb = :gueltigAb"),
    @NamedQuery(name = "Gebuehr.findByGueltigBis", query = "SELECT g FROM Gebuehr g WHERE g.gueltigBis = :gueltigBis"),
    @NamedQuery(name = "Gebuehr.findByCreated", query = "SELECT g FROM Gebuehr g WHERE g.created = :created"),
    @NamedQuery(name = "Gebuehr.findByLastModified", query = "SELECT g FROM Gebuehr g WHERE g.lastModified = :lastModified"),
    @NamedQuery(name = "Gebuehr.findByVersion", query = "SELECT g FROM Gebuehr g WHERE g.version = :version")})
public class Gebuehr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GebuehrId")
    private Long gebuehrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Grundgebuehr")
    private int grundgebuehr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EinsatzProTipp")
    private int einsatzProTipp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EinsatzSpiel77")
    private int einsatzSpiel77;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EinsatzSuper6")
    private int einsatzSuper6;
    @Column(name = "GueltigAb")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltigAb;
    @Column(name = "GueltigBis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltigBis;
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

    public Gebuehr() {
    }

    public Gebuehr(Long gebuehrId) {
        this.gebuehrId = gebuehrId;
    }

    public Gebuehr(Long gebuehrId, int grundgebuehr, int einsatzProTipp, int einsatzSpiel77, int einsatzSuper6, Date created, Date lastModified) {
        this.gebuehrId = gebuehrId;
        this.grundgebuehr = grundgebuehr;
        this.einsatzProTipp = einsatzProTipp;
        this.einsatzSpiel77 = einsatzSpiel77;
        this.einsatzSuper6 = einsatzSuper6;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getGebuehrId() {
        return gebuehrId;
    }

    public void setGebuehrId(Long gebuehrId) {
        this.gebuehrId = gebuehrId;
    }

    public int getGrundgebuehr() {
        return grundgebuehr;
    }

    public void setGrundgebuehr(int grundgebuehr) {
        this.grundgebuehr = grundgebuehr;
    }

    public int getEinsatzProTipp() {
        return einsatzProTipp;
    }

    public void setEinsatzProTipp(int einsatzProTipp) {
        this.einsatzProTipp = einsatzProTipp;
    }

    public int getEinsatzSpiel77() {
        return einsatzSpiel77;
    }

    public void setEinsatzSpiel77(int einsatzSpiel77) {
        this.einsatzSpiel77 = einsatzSpiel77;
    }

    public int getEinsatzSuper6() {
        return einsatzSuper6;
    }

    public void setEinsatzSuper6(int einsatzSuper6) {
        this.einsatzSuper6 = einsatzSuper6;
    }

    public Date getGueltigAb() {
        return gueltigAb;
    }

    public void setGueltigAb(Date gueltigAb) {
        this.gueltigAb = gueltigAb;
    }

    public Date getGueltigBis() {
        return gueltigBis;
    }

    public void setGueltigBis(Date gueltigBis) {
        this.gueltigBis = gueltigBis;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gebuehrId != null ? gebuehrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gebuehr)) {
            return false;
        }
        Gebuehr other = (Gebuehr) object;
        if ((this.gebuehrId == null && other.gebuehrId != null) || (this.gebuehrId != null && !this.gebuehrId.equals(other.gebuehrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Gebuehr[ gebuehrId=" + gebuehrId + " ]";
    }
    
}
