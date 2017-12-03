package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.CursoService;
import br.com.bancodeideias.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController extends GenericController implements Serializable {

    private Usuario             usuarioSelecionado;
    private UsuarioService      usuarioService;
    
    private List<Usuario>       listaUsuario;
    private List<Usuario>       listaAluno;
    private List<Usuario>       listaUniversidades;
    private List<Usuario>       listaUniversidadesPendentes;
    private List<Usuario>       listaAcademicosUniLogada;
    
    private Usuario             usuarioLogado;

    private List<Curso>         listaCurso; 
    private List<Curso>         listarCursosUniversidadeEscolhida;
    private CursoService        cursoService;
    
    private Curso               curso;
     
    private String              senhaGerada;
    
    private String              tipoUsuario; //filtro de usuarios .. Universidade /Academicos

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    /* provisorio, pois na hora de inserir um novo usuario ele estava trazendo os dados (email)
        do usuario logado */
    public void ressetUsuario() {
        usuarioSelecionado = new Usuario();
    }
    
    private void resset() {
        usuarioSelecionado          = new Usuario();
        usuarioService              = new UsuarioService();
        
        listaUsuario                = new ArrayList<>();
        listaAluno                  = new ArrayList<>();
        listaUniversidades          = new ArrayList<>();
        listaUniversidadesPendentes = new ArrayList<>();
        listaAcademicosUniLogada    = new ArrayList<>();

        listaCurso                  = new ArrayList<>();
        cursoService                = new CursoService();

        curso                       = new Curso();

        tipoUsuario                 = "";
    }
    
    /* Admin que utiliza na hora de inserir um usuario 
     ao selecionar a universidade, é carregado somente os cursos dela */
    public void listaCursoUniversidadeEscolhida() {
        listarCursosUniversidadeEscolhida = this.getCursoService().listarCursosUniversidadeEscolhida(usuarioSelecionado.getUniversidade().getIdUsuario());
    }
    
    public void listAcademicosPorCurso() {
        listaAcademicosUniLogada = this.getUsuarioService().listarAcademicosCursoSelecionado(curso.getIdCurso());
    }
    
    public void listUsuariosPorUniversidade() {
        listaUsuario = this.getUsuarioService().listaUsuariosPorUniversidade(usuarioSelecionado.getIdUsuario());
    }
    
    //Utilizado para filtrar os academicos do curso selecionado (universidade - academicos)
    public void listAcademicosTipo() {
        listaAcademicosUniLogada = this.getUsuarioService().listarAcademicosTipoSelecionado(tipoUsuario);
    }
    
    private void listar() {
        listaUsuario                    = this.getUsuarioService().listar(); //todos os usuarios
        listaUniversidades              = this.getUsuarioService().listUniversidades(); // todas as universidades
        listaUniversidadesPendentes     = this.getUsuarioService().listaUniversidadesPendentes(); //todas as universidades pendentes
        listaAluno                      = this.getUsuarioService().listaAlunos(); // todos os alunos
        listaCurso                      = this.getCursoService().listar(); // todos os cursos
        listaAcademicosUniLogada        = this.getUsuarioService().listaAcademicosUniLogada(); // todos os alunos da universidade logada
    }

    /* SALVAR USUARIO A PARTIR DO LOGIN , SEM USUARIO NA SESSAO */
    public void salvarUsuario() {
        try {
            this.getUsuarioSelecionado().setTipoUsuario("Universidade"); //INSERINDO O TIPO DO USUARIO AUTOMATICO
            this.getUsuarioSelecionado().setSituacao("Pendente"); //INSERINDO A SITUACAO AUTOMATICO

            if (this.getUsuarioSelecionado().getSenha().equals(this.getUsuarioSelecionado().getConfirmarSenha())) {
                this.getUsuarioService().salvar(usuarioSelecionado);
                addSucessMessage(this.getUsuarioService().getUsuarioDAO().getMensagem());
                this.resset();
            } else {
                addErrorMessage("As senhas são diferentes, digite novamente");
            }
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
            this.resset();
        }
    }

    /* SALVAR USUARIO A PARTIR DA TELA DE LISTAGEM DE USUARIOS .. COM USUARIO NA SESSAO */
    public String salvar() {
        try {
            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Usuario usuarioLogador = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

            if (usuarioLogador.getTipoUsuario().equals("Universidade")) { //SE O USUARIO FOR UMA UNIVERSIDADE
                this.getUsuarioSelecionado().setUniversidade(usuarioLogador); // SALVANDO A UNIVERSDADE AUTOMATICO
            }
            this.getUsuarioSelecionado().setSenha(this.getUsuarioSelecionado().getMatricula()); // SALVANDO A SENHA IGUAL A MATRICULA DO USUARIO
            this.getUsuarioSelecionado().setSituacao("Ativo");
            this.getUsuarioService().salvar(usuarioSelecionado);
            addSucessMessage(this.getUsuarioService().getUsuarioDAO().getMensagem());
        } catch (Exception e) {
            addErrorMessage(this.getUsuarioService().getUsuarioDAO().getMensagem());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    /* Metodo diferente pois no caso da universidade, 
       existe a opção de alterar meu cadastro e alterar cadastro aluno */
    public void alterarMeuCadastro() {
        try {
            this.getUsuarioService().alterar(usuarioLogado);
            addSucessMessage(this.getUsuarioService().getUsuarioDAO().getMensagem());
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar cadastro");
        }
    }

    public String alterar() {
        try {
            this.getUsuarioService().alterar(usuarioSelecionado);
            addSucessMessage("Usuário alterado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar usuário");
        }
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    /* Metodo utilizado apenas para alteração de senha do usuario */
    public void alterarSenha() {
        try {
            if (usuarioLogado != null) {/* Quando o proprio usuario vai trocar a senha */
                if (this.getUsuarioLogado().getSenha().equals(this.getUsuarioLogado().getConfirmarSenha())) {
                    this.getUsuarioService().alterarSenha(usuarioLogado);
                    addSucessMessage("Senha alterada com sucesso");
                } else {
                    addErrorMessage("As senhas são diferentes, tente novamente");
                }
            } else {/* Quando o usuario esqueceu sua senha, e é gerado uma senha aleatoria */ 
                this.getUsuarioService().alterarSenha(usuarioSelecionado);
            }
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar senha do usuário ");
        }
    }

    public String remover() {
        try {
            this.getUsuarioService().remover(usuarioSelecionado);
            addSucessMessage("Usuário removido com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao remover usuário");
        }
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String consultar() {
        return "listar.xhtml?faces-redirect=true";
    }

    public String cancelar() {
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String doLogin() {
        usuarioLogado = this.getUsuarioService().doLogin(this.usuarioSelecionado.getEmail(),
                this.usuarioSelecionado.getSenha());
        if (usuarioLogado != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuarioLogado", usuarioLogado); //Este usuarioLogado é meu objeto modelo que pode ser persistido.
            System.out.println("Usuario logado na sessao: " + usuarioLogado.getEmail()); //provisorio

            return "/paginas/principal/index.xhtml?faces-redirect=true";
        } else {
            return "/login/login.xhtml?faces-redirect=true";
        }

    }

    /* LOGOUT */
    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login/login.xhtml?faces-redirect=true";
    }

    public void solicitarNovaSenha() {
        Usuario usuario = this.getUsuarioService().solicitarNovaSenha(this.usuarioSelecionado.getEmail(),
                this.usuarioSelecionado.getCpf_cnpj());

        if (usuario != null) {
            setSenhaGerada(this.getUsuarioService().gerarNovaSenha());
            usuario.setSenha(getSenhaGerada());
            this.getUsuarioService().alterarSenha(usuario);
            addSucessMessage("Nova senha gerada com sucesso ! SENHA: " + getSenhaGerada());
            this.resset();
        } else {
            addErrorMessage("Usuario não encontrado ou dados inválidos");
        }
    }

    // ============ METODOS DE AÇÕES NA TELA ===========
    public String doIncluir() {
        /* provisorio, pois na hora de inserir um novo usuario ele estava trazendo os dados (email)
        do usuario logado */
        this.ressetUsuario(); 
        return "incluir.xhtml?faces-redirect=true";
    }
    
    /* Metodo utilizado pelo Admin para inserir Academico */
    public String doIncluirAcademico() {
        this.ressetUsuario(); 
        return "incluirAcademico.xhtml?faces-redirect=true";
    }
    
    /* Metodo utilizado pelo Admin para inserir Universidade */
    public String doIncluirUniversidade() {
        this.ressetUsuario(); 
        return "incluirUniversidade.xhtml?faces-redirect=true";
    }

    public String doCancelar() {
        return "listar.xhtml?faces-redirect=true";
    }

    public String doEditar() {
        return "editar.xhtml?faces-redirect=true";
    }

    public String doExcluir() {
        return "excluir.xhtml?faces-redirect=true";
    }

    public String doConsultar() {
        return "consultar.xhtml?faces-redirect=true";
    }

    public String doCancelarLogin() {
        return "login.xhtml?faces-redirect=true";
    }

    public String doSolicitarSenha() {
        return "solicitar_senha.xhtml?faces-redirect=true";
    }

    // ============ GETS AND SETS ===========  
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public CursoService getCursoService() {
        return cursoService;
    }

    public void setCursoService(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    public List<Curso> getListaCurso() {
        return listaCurso;
    }

    public void setListaCurso(List<Curso> listaCurso) {
        this.listaCurso = listaCurso;
    }

    public List<Usuario> getListaAluno() {
        return listaAluno;
    }

    public void setListaAluno(List<Usuario> listaAluno) {
        this.listaAluno = listaAluno;
    }

    public List<Usuario> getListaUniversidades() {
        return listaUniversidades;
    }

    public void setListaUniversidades(List<Usuario> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
    }

    public List<Curso> getListarCursosUniversidadeEscolhida() {
        return listarCursosUniversidadeEscolhida;
    }

    public void setListarCursosUniversidadeEscolhida(List<Curso> listarCursosUniversidadeEscolhida) {
        this.listarCursosUniversidadeEscolhida = listarCursosUniversidadeEscolhida;
    }

    public List<Usuario> getListaUniversidadesPendentes() {
        return listaUniversidadesPendentes;
    }

    public void setListaUniversidadesPendentes(List<Usuario> listaUniversidadesPendentes) {
        this.listaUniversidadesPendentes = listaUniversidadesPendentes;
    }

    public List<Usuario> getListaAcademicosUniLogada() {
        return listaAcademicosUniLogada;
    }

    public void setListaAcademicosUniLogada(List<Usuario> listaAcademicosUniLogada) {
        this.listaAcademicosUniLogada = listaAcademicosUniLogada;
    }

    public String getSenhaGerada() {
        return senhaGerada;
    }

    public void setSenhaGerada(String senhaGerada) {
        this.senhaGerada = senhaGerada;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
