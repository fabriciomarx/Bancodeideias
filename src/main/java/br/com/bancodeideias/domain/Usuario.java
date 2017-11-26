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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "senha")
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "situacao")
    private String situacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipoUsuario")
    private String tipoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cpf_cnpj")
    private String cpf_cnpj;
    @OneToMany(mappedBy = "analista", fetch = FetchType.LAZY)
    private List<Ideia> ideiaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Ideia> ideiaList1;
    @OneToMany(mappedBy = "participante", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academico", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList1;
    @OneToMany(mappedBy = "analista", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList2;
    @OneToMany(mappedBy = "orientador", fetch = FetchType.LAZY)
    private List<PropostaTcc> propostaTccList3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidade", fetch = FetchType.LAZY)
    private List<Curso> cursoList;
    @JoinColumn(name = "curso", referencedColumnName = "idCurso")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @OneToMany(mappedBy = "universidade", fetch = FetchType.LAZY)
    private List<Usuario> usuarioList;
    @JoinColumn(name = "universidade", referencedColumnName = "idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario universidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orientador", fetch = FetchType.LAZY)
    private List<Encontro> encontroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno", fetch = FetchType.LAZY)
    private List<Encontro> encontroList1;

    @Transient
    private String confirmarSenha;
    @Transient
    private String emailString;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String email, String matricula, String nome, String senha, String situacao, String tipoUsuario, String cpf_cnpj) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.matricula = matricula;
        this.nome = nome;
        this.senha = senha;
        this.situacao = situacao;
        this.tipoUsuario = tipoUsuario;
        this.cpf_cnpj = cpf_cnpj;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<Ideia> getIdeiaList() {
        return ideiaList;
    }

    public void setIdeiaList(List<Ideia> ideiaList) {
        this.ideiaList = ideiaList;
    }

    public List<Ideia> getIdeiaList1() {
        return ideiaList1;
    }

    public void setIdeiaList1(List<Ideia> ideiaList1) {
        this.ideiaList1 = ideiaList1;
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

    public List<PropostaTcc> getPropostaTccList3() {
        return propostaTccList3;
    }

    public void setPropostaTccList3(List<PropostaTcc> propostaTccList3) {
        this.propostaTccList3 = propostaTccList3;
    }

    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
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

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
    
    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
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
