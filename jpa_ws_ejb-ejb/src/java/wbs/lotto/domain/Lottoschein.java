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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "lottoschein")
@NamedQueries({
    @NamedQuery(name = "Lottoschein.findAll", query = "SELECT l FROM Lottoschein l"),
    @NamedQuery(name = "Lottoschein.findByLottoscheinId", query = "SELECT l FROM Lottoschein l WHERE l.lottoscheinId = :lottoscheinId"),
    @NamedQuery(name = "Lottoschein.findByBelegnummer", query = "SELECT l FROM Lottoschein l WHERE l.belegnummer = :belegnummer"),
    @NamedQuery(name = "Lottoschein.findByLosnummer", query = "SELECT l FROM Lottoschein l WHERE l.losnummer = :losnummer"),
    @NamedQuery(name = "Lottoschein.findByIsSpiel77", query = "SELECT l FROM Lottoschein l WHERE l.isSpiel77 = :isSpiel77"),
    @NamedQuery(name = "Lottoschein.findByIsSuper6", query = "SELECT l FROM Lottoschein l WHERE l.isSuper6 = :isSuper6"),
    @NamedQuery(name = "Lottoschein.findByIsMittwoch", query = "SELECT l FROM Lottoschein l WHERE l.isMittwoch = :isMittwoch"),
    @NamedQuery(name = "Lottoschein.findByIsSamstag", query = "SELECT l FROM Lottoschein l WHERE l.isSamstag = :isSamstag"),
    @NamedQuery(name = "Lottoschein.findByLaufzeit", query = "SELECT l FROM Lottoschein l WHERE l.laufzeit = :laufzeit"),
    @NamedQuery(name = "Lottoschein.findByIsAbgeschlossen", query = "SELECT l FROM Lottoschein l WHERE l.isAbgeschlossen = :isAbgeschlossen"),
    @NamedQuery(name = "Lottoschein.findByAbgabeDatum", query = "SELECT l FROM Lottoschein l WHERE l.abgabeDatum = :abgabeDatum"),
    @NamedQuery(name = "Lottoschein.findByCreated", query = "SELECT l FROM Lottoschein l WHERE l.created = :created"),
    @NamedQuery(name = "Lottoschein.findByKosten", query = "SELECT l FROM Lottoschein l WHERE l.kosten = :kosten"),
    @NamedQuery(name = "Lottoschein.findByStatus", query = "SELECT l FROM Lottoschein l WHERE l.status = :status"),
    @NamedQuery(name = "Lottoschein.findByLastModified", query = "SELECT l FROM Lottoschein l WHERE l.lastModified = :lastModified"),
    @NamedQuery(name = "Lottoschein.findByVersion", query = "SELECT l FROM Lottoschein l WHERE l.version = :version"),
    @NamedQuery(name = "Lottoschein.findByZiehungId", query = "SELECT l FROM Lottoschein l JOIN Lottoscheinziehung lz ON l.lottoscheinId = lz.lottoscheinId WHERE lz.ziehungId = :ziehungId" )
})

public class Lottoschein implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LottoscheinId")
    private Long lottoscheinId;
    @Column(name = "Belegnummer")
    private BigInteger belegnummer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Losnummer")
    private int losnummer;
    @Column(name = "IsSpiel77")
    private Boolean isSpiel77;
    @Column(name = "IsSuper6")
    private Boolean isSuper6;
    @Column(name = "IsMittwoch")
    private Boolean isMittwoch;
    @Column(name = "IsSamstag")
    private Boolean isSamstag;
    @Column(name = "Laufzeit")
    private Integer laufzeit;
    @Lob
    @Column(name = "Tipps")
    private byte[] tipps;
    @Column(name = "IsAbgeschlossen")
    private Boolean isAbgeschlossen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AbgabeDatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date abgabeDatum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "Kosten")
    private Integer kosten;
    @Column(name = "Status")
    private Integer status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LastModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Column(name = "Version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lottoscheinId")
    private List<Lottoscheinziehung> lottoscheinziehungList;
    @JoinColumn(name = "KundeId", referencedColumnName = "KundeId")
    @ManyToOne(optional = false)
    private Kunde kundeId;

    public Lottoschein() {
    }

    public Lottoschein(Long lottoscheinId) {
        this.lottoscheinId = lottoscheinId;
    }

    public Lottoschein(Long lottoscheinId, int losnummer, Date abgabeDatum, Date created, Date lastModified) {
        this.lottoscheinId = lottoscheinId;
        this.losnummer = losnummer;
        this.abgabeDatum = abgabeDatum;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getLottoscheinId() {
        return lottoscheinId;
    }

    public void setLottoscheinId(Long lottoscheinId) {
        this.lottoscheinId = lottoscheinId;
    }

    public BigInteger getBelegnummer() {
        return belegnummer;
    }

    public void setBelegnummer(BigInteger belegnummer) {
        this.belegnummer = belegnummer;
    }

    public int getLosnummer() {
        return losnummer;
    }

    public void setLosnummer(int losnummer) {
        this.losnummer = losnummer;
    }

    public Boolean getIsSpiel77() {
        return isSpiel77;
    }

    public void setIsSpiel77(Boolean isSpiel77) {
        this.isSpiel77 = isSpiel77;
    }

    public Boolean getIsSuper6() {
        return isSuper6;
    }

    public void setIsSuper6(Boolean isSuper6) {
        this.isSuper6 = isSuper6;
    }

    public Boolean getIsMittwoch() {
        return isMittwoch;
    }

    public void setIsMittwoch(Boolean isMittwoch) {
        this.isMittwoch = isMittwoch;
    }

    public Boolean getIsSamstag() {
        return isSamstag;
    }

    public void setIsSamstag(Boolean isSamstag) {
        this.isSamstag = isSamstag;
    }

    public Integer getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(Integer laufzeit) {
        this.laufzeit = laufzeit;
    }

    public byte[] getTipps() {
        return tipps;
    }

    public void setTipps(byte[] tipps) {
        this.tipps = tipps;
    }

    public Boolean getIsAbgeschlossen() {
        return isAbgeschlossen;
    }

    public void setIsAbgeschlossen(Boolean isAbgeschlossen) {
        this.isAbgeschlossen = isAbgeschlossen;
    }

    public Date getAbgabeDatum() {
        return abgabeDatum;
    }

    public void setAbgabeDatum(Date abgabeDatum) {
        this.abgabeDatum = abgabeDatum;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getKosten() {
        return kosten;
    }

    public void setKosten(Integer kosten) {
        this.kosten = kosten;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Kunde getKundeId() {
        return kundeId;
    }

    public void setKundeId(Kunde kundeId) {
        this.kundeId = kundeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lottoscheinId != null ? lottoscheinId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lottoschein)) {
            return false;
        }
        Lottoschein other = (Lottoschein) object;
        if ((this.lottoscheinId == null && other.lottoscheinId != null) || (this.lottoscheinId != null && !this.lottoscheinId.equals(other.lottoscheinId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Lottoschein[ lottoscheinId=" + lottoscheinId + " ]";
    }
    
}
