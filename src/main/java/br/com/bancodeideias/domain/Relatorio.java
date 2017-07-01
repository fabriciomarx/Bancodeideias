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
@Table(name = "relatorio")
@NamedQueries({
    @NamedQuery(name = "Relatorio.findAll", query = "SELECT r FROM Relatorio r")
    , @NamedQuery(name = "Relatorio.findByIdRelatorio", query = "SELECT r FROM Relatorio r WHERE r.idRelatorio = :idRelatorio")
    , @NamedQuery(name = "Relatorio.findByAtividadesRealizadas", query = "SELECT r FROM Relatorio r WHERE r.atividadesRealizadas = :atividadesRealizadas")
    , @NamedQuery(name = "Relatorio.findByAutoAvaliacao", query = "SELECT r FROM Relatorio r WHERE r.autoAvaliacao = :autoAvaliacao")
    , @NamedQuery(name = "Relatorio.findByDataInscricao", query = "SELECT r FROM Relatorio r WHERE r.dataInscricao = :dataInscricao")
    , @NamedQuery(name = "Relatorio.findByJustificativa", query = "SELECT r FROM Relatorio r WHERE r.justificativa = :justificativa")
    , @NamedQuery(name = "Relatorio.findByDataFim", query = "SELECT r FROM Relatorio r WHERE r.dataFim = :dataFim")
    , @NamedQuery(name = "Relatorio.findByDataInicio", query = "SELECT r FROM Relatorio r WHERE r.dataInicio = :dataInicio")
    , @NamedQuery(name = "Relatorio.findByStatus", query = "SELECT r FROM Relatorio r WHERE r.status = :status")})
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
    @Size(max = 100)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "orientador", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario orientador;
    @JoinColumn(name = "academico", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario academico;

    public Relatorio() {
    }

    public Relatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public Relatorio(Integer idRelatorio, String atividadesRealizadas, String autoAvaliacao, Date dataInscricao, Date dataFim, Date dataInicio) {
        this.idRelatorio = idRelatorio;
        this.atividadesRealizadas = atividadesRealizadas;
        this.autoAvaliacao = autoAvaliacao;
        this.dataInscricao = dataInscricao;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
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

    public Usuario getOrientador() {
        return orientador;
    }

    public void setOrientador(Usuario orientador) {
        this.orientador = orientador;
    }

    public Usuario getAcademico() {
        return academico;
    }

    public void setAcademico(Usuario academico) {
        this.academico = academico;
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
