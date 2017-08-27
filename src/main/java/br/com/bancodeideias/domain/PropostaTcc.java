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
@Table(name = "proposta_tcc")
@NamedQueries({
    @NamedQuery(name = "PropostaTcc.findAll", query = "SELECT p FROM PropostaTcc p")
    , @NamedQuery(name = "PropostaTcc.findByIdProposta", query = "SELECT p FROM PropostaTcc p WHERE p.idProposta = :idProposta")
    , @NamedQuery(name = "PropostaTcc.findByAprovacaoOrientador", query = "SELECT p FROM PropostaTcc p WHERE p.aprovacaoOrientador = :aprovacaoOrientador")
    , @NamedQuery(name = "PropostaTcc.findByBibliografia", query = "SELECT p FROM PropostaTcc p WHERE p.bibliografia = :bibliografia")
    , @NamedQuery(name = "PropostaTcc.findByComentario", query = "SELECT p FROM PropostaTcc p WHERE p.comentario = :comentario")
    , @NamedQuery(name = "PropostaTcc.findByCronograma", query = "SELECT p FROM PropostaTcc p WHERE p.cronograma = :cronograma")
    , @NamedQuery(name = "PropostaTcc.findByDataAnalise", query = "SELECT p FROM PropostaTcc p WHERE p.dataAnalise = :dataAnalise")
    , @NamedQuery(name = "PropostaTcc.findByDataInscricao", query = "SELECT p FROM PropostaTcc p WHERE p.dataInscricao = :dataInscricao")
    , @NamedQuery(name = "PropostaTcc.findByJustificativa", query = "SELECT p FROM PropostaTcc p WHERE p.justificativa = :justificativa")
    , @NamedQuery(name = "PropostaTcc.findByMaterias", query = "SELECT p FROM PropostaTcc p WHERE p.materias = :materias")
    , @NamedQuery(name = "PropostaTcc.findByMetodologia", query = "SELECT p FROM PropostaTcc p WHERE p.metodologia = :metodologia")
    , @NamedQuery(name = "PropostaTcc.findByObjetivo", query = "SELECT p FROM PropostaTcc p WHERE p.objetivo = :objetivo")
    , @NamedQuery(name = "PropostaTcc.findBySituacao", query = "SELECT p FROM PropostaTcc p WHERE p.situacao = :situacao")
    , @NamedQuery(name = "PropostaTcc.findByTipoTcc", query = "SELECT p FROM PropostaTcc p WHERE p.tipoTcc = :tipoTcc")
    , @NamedQuery(name = "PropostaTcc.findByTitulo", query = "SELECT p FROM PropostaTcc p WHERE p.titulo = :titulo")})
public class PropostaTcc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProposta")
    private Integer idProposta;
    @Size(max = 15)
    @Column(name = "aprovacao_orientador")
    private String aprovacaoOrientador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "bibliografia")
    private String bibliografia;
    @Size(max = 250)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "cronograma")
    private String cronograma;
    @Column(name = "dataAnalise")
    @Temporal(TemporalType.DATE)
    private Date dataAnalise;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataInscricao")
    @Temporal(TemporalType.DATE)
    private Date dataInscricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "justificativa")
    private String justificativa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "materias")
    private String materias;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "metodologia")
    private String metodologia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "objetivo")
    private String objetivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "situacao")
    private String situacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoTcc")
    private String tipoTcc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "titulo")
    private String titulo;
    @JoinColumn(name = "problema", referencedColumnName = "idIdeia")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ideia problema;
    @JoinColumn(name = "participante", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario participante;
    @JoinColumn(name = "academico", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario academico;
    @JoinColumn(name = "analista", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario analista;
    @JoinColumn(name = "orientador", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario orientador;

    public PropostaTcc() {
    }

    public PropostaTcc(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public PropostaTcc(Integer idProposta, String bibliografia, String cronograma, Date dataInscricao, String justificativa, String materias, String metodologia, String objetivo, String situacao, String tipoTcc, String titulo) {
        this.idProposta = idProposta;
        this.bibliografia = bibliografia;
        this.cronograma = cronograma;
        this.dataInscricao = dataInscricao;
        this.justificativa = justificativa;
        this.materias = materias;
        this.metodologia = metodologia;
        this.objetivo = objetivo;
        this.situacao = situacao;
        this.tipoTcc = tipoTcc;
        this.titulo = titulo;
    }

    public Integer getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public String getAprovacaoOrientador() {
        return aprovacaoOrientador;
    }

    public void setAprovacaoOrientador(String aprovacaoOrientador) {
        this.aprovacaoOrientador = aprovacaoOrientador;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCronograma() {
        return cronograma;
    }

    public void setCronograma(String cronograma) {
        this.cronograma = cronograma;
    }

    public Date getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(Date dataAnalise) {
        this.dataAnalise = dataAnalise;
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

    public String getMaterias() {
        return materias;
    }

    public void setMaterias(String materias) {
        this.materias = materias;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTipoTcc() {
        return tipoTcc;
    }

    public void setTipoTcc(String tipoTcc) {
        this.tipoTcc = tipoTcc;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Ideia getProblema() {
        return problema;
    }

    public void setProblema(Ideia problema) {
        this.problema = problema;
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

    public Usuario getAnalista() {
        return analista;
    }

    public void setAnalista(Usuario analista) {
        this.analista = analista;
    }

    public Usuario getOrientador() {
        return orientador;
    }

    public void setOrientador(Usuario orientador) {
        this.orientador = orientador;
    }

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
