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
@Table(name = "encontro")
@NamedQueries({
    @NamedQuery(name = "Encontro.findAll", query = "SELECT e FROM Encontro e")
    , @NamedQuery(name = "Encontro.findByIdEncontro", query = "SELECT e FROM Encontro e WHERE e.idEncontro = :idEncontro")
    , @NamedQuery(name = "Encontro.findByDataEncontro", query = "SELECT e FROM Encontro e WHERE e.dataEncontro = :dataEncontro")
    , @NamedQuery(name = "Encontro.findByHora", query = "SELECT e FROM Encontro e WHERE e.hora = :hora")
    , @NamedQuery(name = "Encontro.findByDescricao", query = "SELECT e FROM Encontro e WHERE e.descricao = :descricao")
    , @NamedQuery(name = "Encontro.findByLocalEncontro", query = "SELECT e FROM Encontro e WHERE e.localEncontro = :localEncontro")
    , @NamedQuery(name = "Encontro.findByStatus", query = "SELECT e FROM Encontro e WHERE e.status = :status")})
public class Encontro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEncontro")
    private Integer idEncontro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataEncontro")
    @Temporal(TemporalType.DATE)
    private Date dataEncontro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "localEncontro")
    private String localEncontro;
    @Size(max = 100)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "academico", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario academico;
    @JoinColumn(name = "participante", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario participante;

    public Encontro() {
    }

    public Encontro(Integer idEncontro) {
        this.idEncontro = idEncontro;
    }

    public Encontro(Integer idEncontro, Date dataEncontro, Date hora, String descricao, String localEncontro) {
        this.idEncontro = idEncontro;
        this.dataEncontro = dataEncontro;
        this.hora = hora;
        this.descricao = descricao;
        this.localEncontro = localEncontro;
    }

    public Integer getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(Integer idEncontro) {
        this.idEncontro = idEncontro;
    }

    public Date getDataEncontro() {
        return dataEncontro;
    }

    public void setDataEncontro(Date dataEncontro) {
        this.dataEncontro = dataEncontro;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalEncontro() {
        return localEncontro;
    }

    public void setLocalEncontro(String localEncontro) {
        this.localEncontro = localEncontro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getAcademico() {
        return academico;
    }

    public void setAcademico(Usuario academico) {
        this.academico = academico;
    }

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEncontro != null ? idEncontro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encontro)) {
            return false;
        }
        Encontro other = (Encontro) object;
        if ((this.idEncontro == null && other.idEncontro != null) || (this.idEncontro != null && !this.idEncontro.equals(other.idEncontro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.Encontro[ idEncontro=" + idEncontro + " ]";
    }
    
}
