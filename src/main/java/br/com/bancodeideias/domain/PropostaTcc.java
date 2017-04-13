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
@Table(name = "proposta_tcc")
@NamedQueries({
    @NamedQuery(name = "PropostaTcc.findAll", query = "SELECT p FROM PropostaTcc p")})
public class PropostaTcc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProposta")
    private Integer idProposta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoTcc")
    private String tipoTcc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "objetivos")
    private String objetivos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "justificativas")
    private String justificativas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "metodologias")
    private String metodologias;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "materias")
    private String materias;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "cronograma")
    private String cronograma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "bibliografia")
    private String bibliografia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Size(max = 2)
    @Column(name = "situacao")
    private String situacao;
    @Size(max = 2)
    @Column(name = "aprovacao_orientador")
    private String aprovacaoOrientador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propostaTcc", fetch = FetchType.LAZY)
    private List<AnalisePropostatcc> analisePropostatccList;
    @JoinColumn(name = "participante", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario participante;
    @JoinColumn(name = "academico", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario academico;
    @JoinColumn(name = "problema", referencedColumnName = "idIdeia")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ideia problema;
    @JoinColumn(name = "orientador", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario orientador;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "projetoTcc", fetch = FetchType.LAZY)
    private List<SituacaoProjeto> situacaoProjetoList;*/

    public PropostaTcc() {
    }

    public PropostaTcc(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public PropostaTcc(Integer idProposta, String titulo, String tipoTcc, String objetivos, String justificativas, String metodologias, String materias, String cronograma, String bibliografia, Date dataInscricao) {
        this.idProposta = idProposta;
        this.titulo = titulo;
        this.tipoTcc = tipoTcc;
        this.objetivos = objetivos;
        this.justificativas = justificativas;
        this.metodologias = metodologias;
        this.materias = materias;
        this.cronograma = cronograma;
        this.bibliografia = bibliografia;
        this.dataInscricao = dataInscricao;
    }

    public Integer getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoTcc() {
        return tipoTcc;
    }

    public void setTipoTcc(String tipoTcc) {
        this.tipoTcc = tipoTcc;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getJustificativas() {
        return justificativas;
    }

    public void setJustificativas(String justificativas) {
        this.justificativas = justificativas;
    }

    public String getMetodologias() {
        return metodologias;
    }

    public void setMetodologias(String metodologias) {
        this.metodologias = metodologias;
    }

    public String getMaterias() {
        return materias;
    }

    public void setMaterias(String materias) {
        this.materias = materias;
    }

    public String getCronograma() {
        return cronograma;
    }

    public void setCronograma(String cronograma) {
        this.cronograma = cronograma;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
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

    public String getAprovacaoOrientador() {
        return aprovacaoOrientador;
    }

    public void setAprovacaoOrientador(String aprovacaoOrientador) {
        this.aprovacaoOrientador = aprovacaoOrientador;
    }

    public List<AnalisePropostatcc> getAnalisePropostatccList() {
        return analisePropostatccList;
    }

    public void setAnalisePropostatccList(List<AnalisePropostatcc> analisePropostatccList) {
        this.analisePropostatccList = analisePropostatccList;
    }

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }

    public Usuario getAcademico() {
        return academico;
    }

    public void setAcademico(Usuario academico) {
        this.academico = academico;
    }

    public Ideia getProblema() {
        return problema;
    }

    public void setProblema(Ideia problema) {
        this.problema = problema;
    }

    public Usuario getOrientador() {
        return orientador;
    }

    public void setOrientador(Usuario orientador) {
        this.orientador = orientador;
    }
/*
    public List<SituacaoProjeto> getSituacaoProjetoList() {
        return situacaoProjetoList;
    }

    public void setSituacaoProjetoList(List<SituacaoProjeto> situacaoProjetoList) {
        this.situacaoProjetoList = situacaoProjetoList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProposta != null ? idProposta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropostaTcc)) {
            return false;
        }
        PropostaTcc other = (PropostaTcc) object;
        if ((this.idProposta == null && other.idProposta != null) || (this.idProposta != null && !this.idProposta.equals(other.idProposta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.PropostaTcc[ idProposta=" + idProposta + " ]";
    }
    
}
