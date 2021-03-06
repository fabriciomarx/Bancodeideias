package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.CursoService;
import br.com.bancodeideias.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "cursoController")
@SessionScoped
public class CursoController extends GenericController implements Serializable {

    private Usuario                         usuario;
    
    private Curso                           cursoSelecionado;
    private CursoService                    cursoService;

    private List<Curso>                     listaCurso;

    private List<Usuario>                   listaUniversidade;
    private UsuarioService                  usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        usuario                           = new Usuario();
        
        cursoSelecionado                  = new Curso();
        cursoService                      = new CursoService();
        
        listaCurso                        = new ArrayList<>();
        
        listaUniversidade                 = new ArrayList<>();
        usuarioService                    = new UsuarioService();
    }
    
    /* Utilizado filtro - Usuario Admin */
    public void listCursos() {
        listaCurso = this.getCursoService().listarCursosUniversidadeEscolhida(usuario.getIdUsuario());
    }

    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        switch (usuarioLogado.getTipoUsuario()) {
            case "Universidade":
                /* SE O USUARIO LOGADO FOR UNIVERSIDADE CARREGAR O METODO LISTAR LOGADO*/
                listaCurso = this.getCursoService().listarCursoLogado();
                break;
            case "Admin":
                /* SE O USUARIO LOGADO FOR ADMIN CARREGAR O METODO LISTAR TODOS OS CURSOS*/
                listaCurso = this.getCursoService().listar();
                listaUniversidade = this.getUsuarioService().listUniversidades();
                break;
        }
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        Calendar cal = Calendar.getInstance(); // pegando o ano atual para salvar na hora de incluir o curso
        int anoAtual = cal.get(Calendar.YEAR);

        try {
            if (usuarioLogado.getTipoUsuario().equals("Universidade")) {
                this.getCursoSelecionado().setUniversidade(usuarioLogado); //INSERINDO A UNIVERSIDADE AUTOMATICO
            }
            this.getCursoSelecionado().setAno(anoAtual);
            this.getCursoService().salvar(cursoSelecionado);
            addSucessMessage(this.getCursoService().getCursoDAO().getMensagem());
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar curso");
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getCursoService().alterar(cursoSelecionado);
            addSucessMessage("Curso alterado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar curso");
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getCursoService().remover(cursoSelecionado);
            addSucessMessage("Curso deletado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar curso");
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String cancelar() {
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    /* ============ METODOS DE AÇÕES NA TELA =========== */
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

    /* ============ GETS AND SETS =========== */
    public Curso getCursoSelecionado() {
        return cursoSelecionado;
    }

    public void setCursoSelecionado(Curso cursoSelecionado) {
        this.cursoSelecionado = cursoSelecionado;
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

    public List<Usuario> getListaUniversidade() {
        return listaUniversidade;
    }

    public void setListaUniversidade(List<Usuario> listaUniversidade) {
        this.listaUniversidade = listaUniversidade;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
