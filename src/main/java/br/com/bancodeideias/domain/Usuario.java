/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.domain;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fabri
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nome")
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "senha")
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "tipoUsuario")
    private String tipoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "situacao")
    private String situacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicoAnalista", fetch = FetchType.LAZY)
    private List<AnalisePropostatcc> analisePropostatccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Ideia> ideiaList;
    @OneToMany(mappedBy = "participante", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academico", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orientador", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidade", fetch = FetchType.LAZY)
    private List<Curso> cursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicoAnalista", fetch = FetchType.LAZY)
    private List<AnaliseIdeia> analiseIdeiaList;
    @JoinColumn(name = "curso", referencedColumnName = "idCurso")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @OneToMany(mappedBy = "universidade", fetch = FetchType.LAZY)
    private List<Usuario> usuarioList;
    @JoinColumn(name = "universidade", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario universidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participante", fetch = FetchType.LAZY)
    private List<Encontro> encontroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academico", fetch = FetchType.LAZY)
    private List<Encontro> encontroList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orientador", fetch = FetchType.LAZY)
    private List<Relatorio> relatorioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academico", fetch = FetchType.LAZY)
    private List<Relatorio> relatorioList1;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String nome, String email, String senha, String matricula, String tipoUsuario, String situacao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.matricula = matricula;
        this.tipoUsuario = tipoUsuario;
        this.situacao = situacao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<AnalisePropostatcc> getAnalisePropostatccList() {
        return analisePropostatccList;
    }

    public void setAnalisePropostatccList(List<AnalisePropostatcc> analisePropostatccList) {
        this.analisePropostatccList = analisePropostatccList;
    }

    public List<Ideia> getIdeiaList() {
        return ideiaList;
    }

    public void setIdeiaList(List<Ideia> ideiaList) {
        this.ideiaList = ideiaList;
    }

    public List<PropostaTcc> getPropostaTccList() {
        return propostaTccList;
    }

    public void setPropostaTccList(List<PropostaTcc> propostaTccList) {
        this.propostaTccList = propostaTccList;
    }

    public List<PropostaTcc> getPropostaTccList1() {
        return propostaTccList1;
    }

    public void setPropostaTccList1(List<PropostaTcc> propostaTccList1) {
        this.propostaTccList1 = propostaTccList1;
    }

    public List<PropostaTcc> getPropostaTccList2() {
        return propostaTccList2;
    }

    public void setPropostaTccList2(List<PropostaTcc> propostaTccList2) {
        this.propostaTccList2 = propostaTccList2;
    }

    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
    }

    public List<AnaliseIdeia> getAnaliseIdeiaList() {
        return analiseIdeiaList;
    }

    public void setAnaliseIdeiaList(List<AnaliseIdeia> analiseIdeiaList) {
        this.analiseIdeiaList = analiseIdeiaList;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Usuario getUniversidade() {
        return universidade;
    }

    public void setUniversidade(Usuario universidade) {
        this.universidade = universidade;
    }

    public List<Encontro> getEncontroList() {
        return encontroList;
    }

    public void setEncontroList(List<Encontro> encontroList) {
        this.encontroList = encontroList;
    }

    public List<Encontro> getEncontroList1() {
        return encontroList1;
    }

    public void setEncontroList1(List<Encontro> encontroList1) {
        this.encontroList1 = encontroList1;
    }

    public List<Relatorio> getRelatorioList() {
        return relatorioList;
    }

    public void setRelatorioList(List<Relatorio> relatorioList) {
        this.relatorioList = relatorioList;
    }

    public List<Relatorio> getRelatorioList1() {
        return relatorioList1;
    }

    public void setRelatorioList1(List<Relatorio> relatorioList1) {
        this.relatorioList1 = relatorioList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bancodeideias.domain.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
