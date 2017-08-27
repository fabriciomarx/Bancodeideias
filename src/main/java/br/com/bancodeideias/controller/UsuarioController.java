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

    private List<Curso>         listaCurso; //todos cursos do sistema
    private List<Curso>         listarCursosUniversidadeEscolhida;
    private CursoService        cursoService;
    
    private Curso               curso;
    
     
    private String              senhaGerada;
    
    private String              tipoUsuario; //filtro de usuarios .. Universidade /Academicos

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
        //FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    }

    private void resset() {
        usuarioSelecionado                  = new Usuario();
        usuarioService                      = new UsuarioService();
        listaUsuario                        = new ArrayList<>();
        listaAluno                          = new ArrayList<>();
        listaUniversidades                  = new ArrayList<>();
        listaUniversidadesPendentes         = new ArrayList<>();
        listaAcademicosUniLogada            = new ArrayList<>();

        //usuarioLogado                       = new Usuario(); //estava dando erro apos inserir academico, usuario universidade

        listaCurso                          = new ArrayList<>();
       // listarCursosUniversidadeEscolhida   = new ArrayList<>(); //estava dando erro apos inserir academico, usuario universidade
        cursoService                        = new CursoService();
        
        
        curso                               = new Curso();
        
        tipoUsuario                         = "";
    }
    
    //Utilizado para filtrar os academicos do curso selecionado (universidade - academicos)
    public void listAcademicos() {
        listaAcademicosUniLogada = this.getUsuarioService().listarAcademicosCursoSelecionado(curso.getIdCurso());
    }
    
    //Utilizado para filtrar os academicos do curso selecionado (universidade - academicos)
    public void listAcademicosTipo() {
        listaAcademicosUniLogada = this.getUsuarioService().listarAcademicosTipoSelecionado(tipoUsuario);
    }
    
    private void listar() {
        
        listaUsuario                = this.getUsuarioService().listar();
        listaUniversidades          = this.getUsuarioService().listUniversidades();
        listaUniversidadesPendentes = this.getUsuarioService().listaUniversidadesPendentes();
        listaAluno                  = this.getUsuarioService().listaAlunos();

        listaCurso                  = this.getCursoService().listar(); // Todos os cursos do sistema ADMIN

        listaAcademicosUniLogada    = this.getUsuarioService().listaAcademicosUniLogada();
  
    }

    private void listarCursosUniversidadeEscolhida() {
        listarCursosUniversidadeEscolhida   = this.getCursoService().listarCursosUniversidadeEscolhida(usuarioLogado.getIdUsuario());
    }

    /* SALVAR USUARIO A PARTIR DO LOGIN , SEM USUARIO NA SESSAO */
    public void salvarUsuario() {
        try {
            this.getUsuarioSelecionado().setTipoUsuario("Universidade"); //INSERINDO O TIPO DO USUARIO AUTOMATICO
            this.getUsuarioSelecionado().setSituacao("Pendente"); //INSERINDO A SITUACAO AUTOMATICO

            if (this.getUsuarioSelecionado().getSenha().equals(this.getUsuarioSelecionado().getConfirmarSenha())) {
                this.getUsuarioService().salvar(usuarioSelecionado);
                addSucessMessage("Inserido com sucesso ! Aguarde a analise do Administrador para acessar o sistema");
                this.resset();
            } else {
                addErrorMessage("As senhas são diferentes, digite novamente");
            }
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
            this.resset();
        }
        //return "login.xhtml?faces-redirect=true"; 
    }
    
    /* SALVAR USUARIO A PARTIR DA TELA DE LISTAGEM DE USUARIOS .. COM USUARIO NA SESSAO */
    public String salvar() {
        try {
            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Usuario usuarioLogador = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO
            
            if (usuarioLogador.getTipoUsuario().equals("Universidade")) { //SE O USUARIO FOR UMA UNIVERSIDADE
                this.getUsuarioSelecionado().setSituacao("Ativo"); // SALVANDO A SITUACAO AUTOMATICO
                this.getUsuarioSelecionado().setUniversidade(usuarioLogador); // SALVANDO A UNIVERSDADE AUTOMATICO
                this.getUsuarioSelecionado().setSenha(this.getUsuarioSelecionado().getMatricula()); // SALVANDO A SENHA IGUAL A MATRICULA DO USUARIO
                this.getUsuarioService().salvar(usuarioSelecionado);
            } else {
                this.getUsuarioSelecionado().setSituacao("Ativo");
                this.getUsuarioService().salvar(usuarioSelecionado);
            }
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar usuario: " + usuarioSelecionado.toString());
        }
        this.resset();
        this.listar();
        //this.listarCursosUniversidadeEscolhida();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogador = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO
        try {
            /* Se o usuario for admin quer dizer que ele esta alterando outros usuarios "usuario selecionado"
             nao alterado ele "usuario logado" */
            if (usuarioLogador.getTipoUsuario().equals("Admin") || usuarioLogador.getTipoUsuario().equals("Universidade")) {
                this.getUsuarioService().alterar(usuarioSelecionado);
            } else {
                this.getUsuarioService().alterar(usuarioLogado);
            }
            addSucessMessage("Usuario alterado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar usuario");
        }
        //this.resset(); comentei porque estava dando erro no aluno/cadastro/editar
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }
    
    /* Metodo utilizado apenas para alteração de senha */
    public String alterarSenha() {
        try {
            if (this.getUsuarioLogado().getSenha().equals(this.getUsuarioLogado().getConfirmarSenha())) {
                this.getUsuarioService().alterarSenha(usuarioLogado);
                addSucessMessage("Senha alterada com sucesso");
            } else {
                addErrorMessage("As senhas são diferentes, digite novamente");
            }
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar senha usuario: " + usuarioSelecionado.toString());
        }
        //this.resset(); comentei porque estava dando erro no aluno/cadastro/editar
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

 
    public String remover() {
        try {
            this.getUsuarioService().remover(usuarioSelecionado);
            addSucessMessage("Usuario removido com sucesso !");
        } catch (Exception e) {
            addErrorMessage("Erro ao remover usuario: " + usuarioSelecionado.toString());
        }
        //this.resset();
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

    //LOGAR NO SISTEMA PROVISORIO
    public String doLogin() {
        usuarioLogado = this.getUsuarioService().doLogin(this.usuarioSelecionado.getEmail(),
                this.usuarioSelecionado.getSenha());
        if (usuarioLogado != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuarioLogado", usuarioLogado); //Este usuarioLogado é meu objeto modelo que pode ser persistido.
            System.out.println("Usuario logado na sessao: " + usuarioLogado.getEmail()); //provisorio

            
            //coloquei aqui porque estava dando erro se eu colocasse no metodo "listar"
            this.listarCursosUniversidadeEscolhida();
            //listarCursosUniversidadeEscolhida = this.getCursoService().listarCursosUniversidadeEscolhida(usuarioLogado.getIdUsuario());
            return "/paginas/principal/index.xhtml?faces-redirect=true";
            
        } else {
            return "/login/login.xhtml?faces-redirect=true";
        }

    }

    //LOGOUT PROVISORIO
    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login/login.xhtml?faces-redirect=true";

    }

    
    public void solicitarNovaSenha() {
        Usuario usuario = this.getUsuarioService().solicitarNovaSenha(this.usuarioSelecionado.getEmail(),
                        this.usuarioSelecionado.getMatricula());

        if (usuario != null) {
            setSenhaGerada(this.getUsuarioService().gerarNovaSenha());
            System.out.println("Senha gerada: " + getSenhaGerada());
            usuario.setSenha(getSenhaGerada());
            this.getUsuarioService().alterarSenha(usuario);
            addSucessMessage("Nova senha gerada com sucesso ! SENHA: " + getSenhaGerada());
            this.resset();
        } else {
            addErrorMessage("Usuario não encontrado ou dados invalidos");
        }
        //return "/login/login.xhtml?faces-redirect=true";
    }

    /*
    public void enviarEmail() {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465); //porta padrao
            email.setDebug(true); //para ver o processo de envio
            email.setAuthenticator(new DefaultAuthenticator("famarx.29@gmail.com", "estrela2911"));
            email.setSSLOnConnect(true); //conexao segura
            email.setFrom("famarx.29@gmail.com"); //de quem é esse email
            email.setSubject("TestMail"); // assunto
            email.setMsg("This is a test mail ... :-)"); //mensagem
            email.addTo("fabricio_m.s@hotmail.com");  //para quem vai esse email
            email.send(); //fazer envio do email
            email.setTLS(true);
            email.setSSL(true);

            System.out.println("Email enviado");
        } catch (EmailException e) {
            System.out.println("Email não enviado " + e.getMessage());

        }

    }

   
    public void enviarEmail(String destinatario, String mensagem) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465); //porta padrao
            email.setDebug(true); //para ver o processo de envio no console
            email.setAuthenticator(new DefaultAuthenticator("famarx.29@gmail.com", "estrela2911"));
            email.setSSLOnConnect(true); //conexao segura
            email.setFrom("famarx.29@gmail.com"); //de quem é esse email
            email.setSubject("Solicitação de nova senha"); // assunto
            email.setMsg(
                    "Utilize a nova senha -> " + mensagem + " <- para acessar o sistema Banco de ideias,"
                            + " não se esqueça de altera - lá após login para sua segurança" ); //mensagem
            email.addTo(destinatario);  //para quem vai esse email
            email.addReplyTo("famarx.29@gmail.com"); //a pessoa pode responder para esse email ( o mesmo que envio )
            email.send(); //fazer envio do email
            
            System.out.println("EMAIL ENVIADO"); // para aparecer no console
            addSucessMessage("Senha enviada para o email"); // para aparecer ao usuario
        } catch (EmailException e) {
             System.out.println("EMAIL NÃO ENVIADO " + e.getMessage() ); // para aparecer no console
             addErrorMessage("Erro ao enviar email, tente novamente mais tarde"); //para aparecer ao usuario
            
        }

    }*/

 /*Metodo para aceitar universidades, serve para o usuario adm*/
    public String aceitar() {
        try {
            this.getUsuarioSelecionado().setSituacao("Ativo");
            this.getUsuarioService().alterar(usuarioSelecionado);
            addSucessMessage("Universidade aceita com sucesso");
           
        } catch (Exception e) {
            addErrorMessage("Erro ao aceitar universidade: " + usuarioSelecionado.toString());
        }
        //this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    // ============ METODOS DE AÇÕES NA TELA ===========
    public String doIncluir() {
        return "incluir.xhtml?faces-redirect=true";
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
    
    public String doCancelarLogin(){
        return "login.xhtml?faces-redirect=true";
    }
    
    public String doSolicitarSenha(){
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
