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

@Named(value = "cursoController")
@SessionScoped
public class CursoController extends GenericController implements Serializable {

    private Curso           cursoSelecionado;
    private CursoService    cursoService;
    private List<Curso>     listaCurso;
    private List<Curso>     listaCursoFiltrados;
    private List<Curso>     listaCursoLogado;

    private List<Usuario>   listaUniversidade;
    private UsuarioService  usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        cursoSelecionado    = new Curso();
        cursoService        = new CursoService();
        listaCurso          = new ArrayList<>();
        listaCursoFiltrados = new ArrayList<>();
        listaCursoLogado    = new ArrayList<>();

        listaUniversidade   = new ArrayList<>();
        usuarioService      = new UsuarioService();
    }

    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        /* SE O USUARIO LOGADO FOR UNIVERSIDADE CARREGAR O METODO LISTAR LOGADO*/
        if (usuarioLogado.getTipoUsuario().equals("Universidade")) {
            listaCursoLogado    = this.getCursoService().listarCursoLogado();
            
        } else if (usuarioLogado.getTipoUsuario().equals("Admin")) {
            listaCurso          = this.getCursoService().listar();
            listaUniversidade   = this.getUsuarioService().listUniversidades();
            listaCursoFiltrados = this.getCursoService().listar();
        }
        
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            if (usuarioLogado.getTipoUsuario().equals("Admin")) { //SE O USUARIO FOR ADMIN NAO SALVAR A UNIVERSIDADE AUTOMATICO
                this.getCursoService().salvar(cursoSelecionado);
                
            } else {
                this.getCursoSelecionado().setUniversidade(usuarioLogado); //INSERINDO A UNIVERSIDADE AUTOMATICO
                this.getCursoService().salvar(cursoSelecionado);
            }
            
            addSucessMessage("Curso salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar curso: " + cursoSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            if(usuarioLogado.getTipoUsuario().equals("Admin")){ //SE O USUARIO FOR ADMIN NAO SALVAR A UNIVERSIDADE AUTOMATICO
                this.getCursoService().alterar(cursoSelecionado);
            }else{
                this.getCursoSelecionado().setUniversidade(usuarioLogado); //INSERINDO A UNIVERSIDADE AUTOMATICO
                this.getCursoService().alterar(cursoSelecionado);
            }
            
            addSucessMessage("Curso alterado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar curso: " + cursoSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getCursoService().remover(cursoSelecionado);
           
           //addSucessMessage("Curso deletado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar curso: " + cursoSelecionado.toString());
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
    
    public List<Curso> getListaCursoLogado() {
        return listaCursoLogado;
    }

    public void setListaCursoLogado(List<Curso> listaCursoLogado) {
        this.listaCursoLogado = listaCursoLogado;
    }

    public List<Curso> getListaCursoFiltrados() {
        return listaCursoFiltrados;
    }

    public void setListaCursoFiltrados(List<Curso> listaCursoFiltrados) {
        this.listaCursoFiltrados = listaCursoFiltrados;
    }
   
    
    
}
