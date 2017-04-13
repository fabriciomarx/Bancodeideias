/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author fabri
 */
@Entity
@Table(name = "situacao_projeto")
@NamedQueries({
    @NamedQuery(name = "SituacaoProjeto.findAll", query = "SELECT s FROM SituacaoProjeto s")})
public class SituacaoProjeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSituacao")
    private Integer idSituacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataSituacao")
    @Temporal(TemporalType.DATE)
    private Date dataSituacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "observacoes")
    private String observacoes;
    @JoinColumn(name = "projetoTcc", referencedColumnName = "idProposta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PropostaTcc projetoTcc;

    public SituacaoProjeto() {
    }

    public SituacaoProjeto(Integer idSituacao) {
        this.idSituacao = idSituacao;
    }

    public SituacaoProjeto(Integer idSituacao, Date dataSituacao, String observacoes) {
        this.idSituacao = idSituacao;
        this.dataSituacao = dataSituacao;
        this.observacoes = observacoes;
    }

    public Integer getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(Integer idSituacao) {
        this.idSituacao = idSituacao;
    }

    public Date getDataSituacao() {
        return dataSituacao;
    }

    public void setDataSituacao(Date dataSituacao) {
        this.dataSituacao = dataSituacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public PropostaTcc getProjetoTcc() {
        return projetoTcc;
    }

    public void setProjetoTcc(PropostaTcc projetoTcc) {
        this.projetoTcc = projetoTcc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSituacao != null ? idSituacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SituacaoProjeto)) {
            return false;
        }
        SituacaoProjeto other = (SituacaoProjeto) object;
        if ((this.idSituacao == null && other.idSituacao != null) || (this.idSituacao != null && !this.idSituacao.equals(other.idSituacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.SituacaoProjeto[ idSituacao=" + idSituacao + " ]";
    }
    
}
