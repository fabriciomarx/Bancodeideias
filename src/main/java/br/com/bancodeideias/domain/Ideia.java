/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
@Table(name = "ideia")
@NamedQueries({
    @NamedQuery(name = "Ideia.findAll", query = "SELECT i FROM Ideia i")})
public class Ideia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIdeia")
    private Integer idIdeia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoIdeia")
    private String tipoIdeia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataIdeia")
    @Temporal(TemporalType.DATE)
    private Date dataIdeia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "situacao")
    private String situacao;
    @JoinColumn(name = "usuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "problema", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ideia", fetch = FetchType.LAZY)
    private List<AnaliseIdeia> analiseIdeiaList;

    public Ideia() {
    }

    public Ideia(Integer idIdeia) {
        this.idIdeia = idIdeia;
    }

    public Ideia(Integer idIdeia, String titulo, String tipoIdeia, Date dataIdeia, String situacao) {
        this.idIdeia = idIdeia;
        this.titulo = titulo;
        this.tipoIdeia = tipoIdeia;
        this.dataIdeia = dataIdeia;
        this.situacao = situacao;
    }

    public Integer getIdIdeia() {
        return idIdeia;
    }

    public void setIdIdeia(Integer idIdeia) {
        this.idIdeia = idIdeia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoIdeia() {
        return tipoIdeia;
    }

    public void setTipoIdeia(String tipoIdeia) {
        this.tipoIdeia = tipoIdeia;
    }

    public Date getDataIdeia() {
        return dataIdeia;
    }

    public void setDataIdeia(Date dataIdeia) {
        this.dataIdeia = dataIdeia;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PropostaTcc> getPropostaTccList() {
        return propostaTccList;
    }

    public void setPropostaTccList(List<PropostaTcc> propostaTccList) {
        this.propostaTccList = propostaTccList;
    }

    public List<AnaliseIdeia> getAnaliseIdeiaList() {
        return analiseIdeiaList;
    }

    public void setAnaliseIdeiaList(List<AnaliseIdeia> analiseIdeiaList) {
        this.analiseIdeiaList = analiseIdeiaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIdeia != null ? idIdeia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ideia)) {
            return false;
        }
        Ideia other = (Ideia) object;
        if ((this.idIdeia == null && other.idIdeia != null) || (this.idIdeia != null && !this.idIdeia.equals(other.idIdeia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.Ideia[ idIdeia=" + idIdeia + " ]";
    }
    
}
