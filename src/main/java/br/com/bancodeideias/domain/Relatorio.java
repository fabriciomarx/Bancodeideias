package br.com.bancodeideias.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
@Table(name = "relatorio")
public class Relatorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRelatorio")
    private Integer idRelatorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "atividadesRealizadas")
    private String atividadesRealizadas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "autoAvaliacao")
    private String autoAvaliacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Size(max = 250)
    @Column(name = "justificativa")
    private String justificativa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataFim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "encontro", referencedColumnName = "idEncontro")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Encontro encontro;

    public Relatorio() {
    }

    public Relatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public Relatorio(Integer idRelatorio, String atividadesRealizadas, String autoAvaliacao, Date dataInscricao, Date dataFim, Date dataInicio, String status) {
        this.idRelatorio = idRelatorio;
        this.atividadesRealizadas = atividadesRealizadas;
        this.autoAvaliacao = autoAvaliacao;
        this.dataInscricao = dataInscricao;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.status = status;
    }

    public Integer getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getAtividadesRealizadas() {
        return atividadesRealizadas;
    }

    public void setAtividadesRealizadas(String atividadesRealizadas) {
        this.atividadesRealizadas = atividadesRealizadas;
    }

    public String getAutoAvaliacao() {
        return autoAvaliacao;
    }

    public void setAutoAvaliacao(String autoAvaliacao) {
        this.autoAvaliacao = autoAvaliacao;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRelatorio != null ? idRelatorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relatorio)) {
            return false;
        }
        Relatorio other = (Relatorio) object;
        if ((this.idRelatorio == null && other.idRelatorio != null) || (this.idRelatorio != null && !this.idRelatorio.equals(other.idRelatorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.Relatorio[ idRelatorio=" + idRelatorio + " ]";
    }

}
