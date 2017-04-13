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
@Table(name = "analise_propostatcc")
@NamedQueries({
    @NamedQuery(name = "AnalisePropostatcc.findAll", query = "SELECT a FROM AnalisePropostatcc a")})
public class AnalisePropostatcc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAnalise")
    private Integer idAnalise;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataAnalise")
    @Temporal(TemporalType.DATE)
    private Date dataAnalise;
    @Size(max = 250)
    @Column(name = "comentarios")
    private String comentarios;
    @JoinColumn(name = "propostaTcc", referencedColumnName = "idProposta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PropostaTcc propostaTcc;
    @JoinColumn(name = "academicoAnalista", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario academicoAnalista;

    public AnalisePropostatcc() {
    }

    public AnalisePropostatcc(Integer idAnalise) {
        this.idAnalise = idAnalise;
    }

    public AnalisePropostatcc(Integer idAnalise, Date dataAnalise) {
        this.idAnalise = idAnalise;
        this.dataAnalise = dataAnalise;
    }

    public Integer getIdAnalise() {
        return idAnalise;
    }

    public void setIdAnalise(Integer idAnalise) {
        this.idAnalise = idAnalise;
    }

    public Date getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(Date dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public PropostaTcc getPropostaTcc() {
        return propostaTcc;
    }

    public void setPropostaTcc(PropostaTcc propostaTcc) {
        this.propostaTcc = propostaTcc;
    }

    public Usuario getAcademicoAnalista() {
        return academicoAnalista;
    }

    public void setAcademicoAnalista(Usuario academicoAnalista) {
        this.academicoAnalista = academicoAnalista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnalise != null ? idAnalise.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisePropostatcc)) {
            return false;
        }
        AnalisePropostatcc other = (AnalisePropostatcc) object;
        if ((this.idAnalise == null && other.idAnalise != null) || (this.idAnalise != null && !this.idAnalise.equals(other.idAnalise))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.AnalisePropostatcc[ idAnalise=" + idAnalise + " ]";
    }
    
}
