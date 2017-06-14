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
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController extends GenericController implements Serializable {

    private Usuario             usuarioSelecionado;
    private UsuarioService      usuarioService;
    private List<Usuario>       listaUsuario;
    private List<Usuario>       listaAcademicos;
    private List<Usuario>       listaUniversidades;
    private List<Usuario>       listaUniversidadesPendentes;
    private List<Usuario>       listaAcademicosUniLogada;
    
    private Usuario             usuarioLogado;

    private List<Curso>         listaCurso; //todos cursos do sistema
    private List<Curso>         listarCursosUniversidadeEscolhida;
    private CursoService        cursoService;

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
        listaAcademicos                     = new ArrayList<>();
        listaUniversidades                  = new ArrayList<>();
        listaUniversidadesPendentes         = new ArrayList<>();
        listaAcademicosUniLogada            = new ArrayList<>();

        usuarioLogado                       = new Usuario();

        listaCurso                          = new ArrayList<>();
        listarCursosUniversidadeEscolhida   = new ArrayList<>();
        cursoService                        = new CursoService();
    }

    private void listar() {
        listaUsuario                        = this.getUsuarioService().listar();
        listaUniversidades                  = this.getUsuarioService().listUniversidades();
        listaUniversidadesPendentes         = this.getUsuarioService().listaUniversidadesPendentes();
        listaAcademicos                     = this.getUsuarioService().listaAcademicos();
        
        listaCurso                          = this.getCursoService().listar();
        
        listaAcademicosUniLogada            = this.getUsuarioService().listaAcademicosUniLogada();
        
       
    }

    private void listarCursosUniversidadeEscolhida() {
        listarCursosUniversidadeEscolhida   = this.getCursoService().listarCursosUniversidadeEscolhida(usuarioLogado.getIdUsuario());
    }

    public String salvar() {
        try {

            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Usuario usuarioLogador = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO
            //SE O USUARIO FOR UMA UNIVERSIDADE AO INSERIR UM NOVO ACADEMICO, SALVAR O NOME DELA AUTOMATICO
            //NO CAMPO 'UNIVERSIDADE'
            if (usuarioLogador.getTipoUsuario().equals("Universidade")) {
                this.getUsuarioSelecionado().setSituacao("Ativo");
                this.getUsuarioSelecionado().setUniversidade(usuarioLogador);
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
        this.listarCursosUniversidadeEscolhida();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getUsuarioService().alterar(usuarioLogado);
            addSucessMessage("Usuario alterado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar usuario: " + usuarioSelecionado.toString());
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
        this.resset();
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

    public String solicitarNovaSenha() {
        Usuario usuario
                = this.getUsuarioService().solicitarNovaSenha(this.usuarioSelecionado.getEmail(),
                        this.usuarioSelecionado.getMatricula());

        if (usuario != null) {
            String senha = this.getUsuarioService().gerarNovaSenha();
            System.out.println("Senha gerada: " + senha + " " + usuario.getEmail());
            usuario.setSenha(senha);
            this.getUsuarioService().alterar(usuario);

            /* enviando a senha por email */
            //enviarEmail(usuario.getEmail(),senha);
            //enviarEmail();
        } else {
            addErrorMessage("Usuario não encontrado e/ou Dados invalidos");
            System.out.println("Usuario não encontrado e/ou Dados invalidos");
        }
        return "/login/login.xhtml?faces-redirect=true";

    }

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
            System.out.println("Email enviado");
        } catch (EmailException e) {
            System.out.println("Email não enviado " + e.getMessage());

        }

    }

    /* Enviar email 
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
        this.resset();
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

    public List<Usuario> getListaAcademicos() {
        return listaAcademicos;
    }

    public void setListaAcademicos(List<Usuario> listaAcademicos) {
        this.listaAcademicos = listaAcademicos;
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
}
