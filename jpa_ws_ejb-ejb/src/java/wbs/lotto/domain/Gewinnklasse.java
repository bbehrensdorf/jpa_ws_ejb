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
import javax.persistence.ManyToOne;
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
@Table(name = "gewinnklasse")
@NamedQueries({
    @NamedQuery(name = "Gewinnklasse.findAll", query = "SELECT g FROM Gewinnklasse g"),
    @NamedQuery(name = "Gewinnklasse.findByGewinnklasseId", query = "SELECT g FROM Gewinnklasse g WHERE g.gewinnklasseId = :gewinnklasseId"),
    @NamedQuery(name = "Gewinnklasse.findByGewinnklasseNr", query = "SELECT g FROM Gewinnklasse g WHERE g.gewinnklasseNr = :gewinnklasseNr"),
    @NamedQuery(name = "Gewinnklasse.findByBezeichnungLatein", query = "SELECT g FROM Gewinnklasse g WHERE g.bezeichnungLatein = :bezeichnungLatein"),
    @NamedQuery(name = "Gewinnklasse.findByBeschreibung", query = "SELECT g FROM Gewinnklasse g WHERE g.beschreibung = :beschreibung"),
    @NamedQuery(name = "Gewinnklasse.findByIsAbsolut", query = "SELECT g FROM Gewinnklasse g WHERE g.isAbsolut = :isAbsolut"),
    @NamedQuery(name = "Gewinnklasse.findByBetrag", query = "SELECT g FROM Gewinnklasse g WHERE g.betrag = :betrag"),
    @NamedQuery(name = "Gewinnklasse.findByGueltigAb", query = "SELECT g FROM Gewinnklasse g WHERE g.gueltigAb = :gueltigAb"),
    @NamedQuery(name = "Gewinnklasse.findByGueltigBis", query = "SELECT g FROM Gewinnklasse g WHERE g.gueltigBis = :gueltigBis"),
    @NamedQuery(name = "Gewinnklasse.findByCreated", query = "SELECT g FROM Gewinnklasse g WHERE g.created = :created"),
    @NamedQuery(name = "Gewinnklasse.findByLastModified", query = "SELECT g FROM Gewinnklasse g WHERE g.lastModified = :lastModified"),
    @NamedQuery(name = "Gewinnklasse.findByVersion", query = "SELECT g FROM Gewinnklasse g WHERE g.version = :version")})
public class Gewinnklasse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GewinnklasseId")
    private Long gewinnklasseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GewinnklasseNr")
    private int gewinnklasseNr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "BezeichnungLatein")
    private String bezeichnungLatein;
    @Size(max = 255)
    @Column(name = "Beschreibung")
    private String beschreibung;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsAbsolut")
    private boolean isAbsolut;
    @Column(name = "Betrag")
    private BigInteger betrag;
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
    @OneToMany(mappedBy = "gewinnklasseIdSpiel77")
    private List<Lottoscheinziehung> lottoscheinziehungList;
    @OneToMany(mappedBy = "gewinnklasseIdSuper6")
    private List<Lottoscheinziehung> lottoscheinziehungList1;
    @OneToMany(mappedBy = "gewinnklasseId")
    private List<Jackpot> jackpotList;
    @JoinColumn(name = "SpielId", referencedColumnName = "SpielId")
    @ManyToOne(optional = false)
    private Spiel spielId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gewinnklasseId")
    private List<Lottoscheinziehung6aus49> lottoscheinziehung6aus49List;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gewinnklasseId")
    private List<Gewinnklasseziehungquote> gewinnklasseziehungquoteList;

    public Gewinnklasse() {
    }

    public Gewinnklasse(Long gewinnklasseId) {
        this.gewinnklasseId = gewinnklasseId;
    }

    public Gewinnklasse(Long gewinnklasseId, int gewinnklasseNr, String bezeichnungLatein, boolean isAbsolut, Date created, Date lastModified) {
        this.gewinnklasseId = gewinnklasseId;
        this.gewinnklasseNr = gewinnklasseNr;
        this.bezeichnungLatein = bezeichnungLatein;
        this.isAbsolut = isAbsolut;
        this.created = created;
        this.lastModified = lastModified;
    }

    public Long getGewinnklasseId() {
        return gewinnklasseId;
    }

    public void setGewinnklasseId(Long gewinnklasseId) {
        this.gewinnklasseId = gewinnklasseId;
    }

    public int getGewinnklasseNr() {
        return gewinnklasseNr;
    }

    public void setGewinnklasseNr(int gewinnklasseNr) {
        this.gewinnklasseNr = gewinnklasseNr;
    }

    public String getBezeichnungLatein() {
        return bezeichnungLatein;
    }

    public void setBezeichnungLatein(String bezeichnungLatein) {
        this.bezeichnungLatein = bezeichnungLatein;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean getIsAbsolut() {
        return isAbsolut;
    }

    public void setIsAbsolut(boolean isAbsolut) {
        this.isAbsolut = isAbsolut;
    }

    public BigInteger getBetrag() {
        return betrag;
    }

    public void setBetrag(BigInteger betrag) {
        this.betrag = betrag;
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

    public List<Lottoscheinziehung> getLottoscheinziehungList() {
        return lottoscheinziehungList;
    }

    public void setLottoscheinziehungList(List<Lottoscheinziehung> lottoscheinziehungList) {
        this.lottoscheinziehungList = lottoscheinziehungList;
    }

    public List<Lottoscheinziehung> getLottoscheinziehungList1() {
        return lottoscheinziehungList1;
    }

    public void setLottoscheinziehungList1(List<Lottoscheinziehung> lottoscheinziehungList1) {
        this.lottoscheinziehungList1 = lottoscheinziehungList1;
    }

    public List<Jackpot> getJackpotList() {
        return jackpotList;
    }

    public void setJackpotList(List<Jackpot> jackpotList) {
        this.jackpotList = jackpotList;
    }

    public Spiel getSpielId() {
        return spielId;
    }

    public void setSpielId(Spiel spielId) {
        this.spielId = spielId;
    }

    public List<Lottoscheinziehung6aus49> getLottoscheinziehung6aus49List() {
        return lottoscheinziehung6aus49List;
    }

    public void setLottoscheinziehung6aus49List(List<Lottoscheinziehung6aus49> lottoscheinziehung6aus49List) {
        this.lottoscheinziehung6aus49List = lottoscheinziehung6aus49List;
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
        hash += (gewinnklasseId != null ? gewinnklasseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gewinnklasse)) {
            return false;
        }
        Gewinnklasse other = (Gewinnklasse) object;
        if ((this.gewinnklasseId == null && other.gewinnklasseId != null) || (this.gewinnklasseId != null && !this.gewinnklasseId.equals(other.gewinnklasseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "wbs.lotto.domain.Gewinnklasse[ gewinnklasseId=" + gewinnklasseId + " ]";
    }
    
}
