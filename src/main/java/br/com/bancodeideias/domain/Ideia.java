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
    @NamedQuery(name = "Ideia.findAll", query = "SELECT i FROM Ideia i")
    , @NamedQuery(name = "Ideia.findByIdIdeia", query = "SELECT i FROM Ideia i WHERE i.idIdeia = :idIdeia")
    , @NamedQuery(name = "Ideia.findByDataInscricao", query = "SELECT i FROM Ideia i WHERE i.dataInscricao = :dataInscricao")
    , @NamedQuery(name = "Ideia.findBySituacao", query = "SELECT i FROM Ideia i WHERE i.situacao = :situacao")
    , @NamedQuery(name = "Ideia.findByTipoIdeia", query = "SELECT i FROM Ideia i WHERE i.tipoIdeia = :tipoIdeia")
    , @NamedQuery(name = "Ideia.findByDescricao", query = "SELECT i FROM Ideia i WHERE i.descricao = :descricao")
    , @NamedQuery(name = "Ideia.findByTitulo", query = "SELECT i FROM Ideia i WHERE i.titulo = :titulo")
    , @NamedQuery(name = "Ideia.findByDisponibilidade", query = "SELECT i FROM Ideia i WHERE i.disponibilidade = :disponibilidade")
    , @NamedQuery(name = "Ideia.findByDataAnalise", query = "SELECT i FROM Ideia i WHERE i.dataAnalise = :dataAnalise")
    , @NamedQuery(name = "Ideia.findByComentario", query = "SELECT i FROM Ideia i WHERE i.comentario = :comentario")
    , @NamedQuery(name = "Ideia.findByFavorito", query = "SELECT i FROM Ideia i WHERE i.favorito = :favorito")})
public class Ideia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIdeia")
    private Integer idIdeia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "situacao")
    private String situacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoIdeia")
    private String tipoIdeia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "disponibilidade")
    private String disponibilidade;
    @Column(name = "dataAnalise")
    @Temporal(TemporalType.DATE)
    private Date dataAnalise;
    @Size(max = 250)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "favorito")
    private String favorito;
    @JoinColumn(name = "analista", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario analista;
    @JoinColumn(name = "usuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "problema", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList;

    public Ideia() {
    }

    public Ideia(Integer idIdeia) {
        this.idIdeia = idIdeia;
    }

    public Ideia(Integer idIdeia, Date dataInscricao, String situacao, String tipoIdeia, String descricao, String titulo, String disponibilidade, String favorito) {
        this.idIdeia = idIdeia;
        this.dataInscricao = dataInscricao;
        this.situacao = situacao;
        this.tipoIdeia = tipoIdeia;
        this.descricao = descricao;
        this.titulo = titulo;
        this.disponibilidade = disponibilidade;
        this.favorito = favorito;
    }

    public Integer getIdIdeia() {
        return idIdeia;
    }

    public void setIdIdeia(Integer idIdeia) {
        this.idIdeia = idIdeia;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTipoIdeia() {
        return tipoIdeia;
    }

    public void setTipoIdeia(String tipoIdeia) {
        this.tipoIdeia = tipoIdeia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Date getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(Date dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFavorito() {
        return favorito;
    }

    public void setFavorito(String favorito) {
        this.favorito = favorito;
    }

    public Usuario getAnalista() {
        return analista;
    }

    public void setAnalista(Usuario analista) {
        this.analista = analista;
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
