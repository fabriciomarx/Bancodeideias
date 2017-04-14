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
    @NamedQuery(name = "Relatorio.findAll", query = "SELECT r FROM Relatorio r")})
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
    @Column(name = "dataRelatorio")
    @Temporal(TemporalType.DATE)
    private Date dataRelatorio;
    @Size(max = 250)
    @Column(name = "justificativa")
    private String justificativa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodoFim")
    @Temporal(TemporalType.DATE)
    private Date periodoFim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodoInicio")
    @Temporal(TemporalType.DATE)
    private Date periodoInicio;
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

    public Relatorio(Integer idRelatorio, String atividadesRealizadas, String autoAvaliacao, Date dataRelatorio, Date periodoFim, Date periodoInicio) {
        this.idRelatorio = idRelatorio;
        this.atividadesRealizadas = atividadesRealizadas;
        this.autoAvaliacao = autoAvaliacao;
        this.dataRelatorio = dataRelatorio;
        this.periodoFim = periodoFim;
        this.periodoInicio = periodoInicio;
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

    public Date getDataRelatorio() {
        return dataRelatorio;
    }

    public void setDataRelatorio(Date dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Date getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(Date periodoFim) {
        this.periodoFim = periodoFim;
    }

    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
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
