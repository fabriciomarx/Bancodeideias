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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ideia")
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
    @Column(name = "dataAnalise")
    @Temporal(TemporalType.DATE)
    private Date dataAnalise;
    @Size(max = 250)
    @Column(name = "comentario")
    private String comentario;
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

    public Ideia(Integer idIdeia, Date dataInscricao, String situacao, String tipoIdeia, String descricao, String titulo) {
        this.idIdeia = idIdeia;
        this.dataInscricao = dataInscricao;
        this.situacao = situacao;
        this.tipoIdeia = tipoIdeia;
        this.descricao = descricao;
        this.titulo = titulo;
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
