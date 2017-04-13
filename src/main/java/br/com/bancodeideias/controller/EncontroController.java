package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Encontro;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.EncontroService;
import br.com.bancodeideias.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "encontroController")
@SessionScoped
public class EncontroController extends GenericController implements Serializable {

    private Encontro                encontroSelecionado;
    private EncontroService         encontroService;
    private List<Encontro>          listaEncontro; // todos encontros
    private List<Encontro>          listarEncontroQueCadastrei;
    private List<Encontro>          listarEncontroQueFuiChamado;

    private List<Usuario>           listaAcademico;
    private UsuarioService          usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        encontroSelecionado         = new Encontro();
        encontroService             = new EncontroService();
        listaEncontro               = new ArrayList<>();
        listarEncontroQueCadastrei  = new ArrayList<>();
        listarEncontroQueFuiChamado = new ArrayList<>();

        listaAcademico              = new ArrayList<>();
        usuarioService              = new UsuarioService();
    }

    private void listar() {
        listaEncontro               = this.getEncontroService().listar();
        listarEncontroQueCadastrei  = this.getEncontroService().listarEncontrosQueCadastrei();
        listarEncontroQueFuiChamado = this.getEncontroService().listarEncontrosQueFuiChamado();

        listaAcademico              = this.getUsuarioService().listar();
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  
        try {
            if(usuarioLogado.getTipoUsuario().equals("Admin") || 
                    usuarioLogado.getTipoUsuario().equals("Coordenador")){ //SE O USUARIO FOR ADMIN NAO SALVAR O ACADEMICO AUTOMATICO
                this.getEncontroService().salvar(encontroSelecionado);
            }else{
                this.getEncontroSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getEncontroService().salvar(encontroSelecionado);
            }
            
            addSucessMessage("Encontro salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar encontro: " + encontroSelecionado.toString());
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
                this.getEncontroService().alterar(encontroSelecionado);
            }else{
                this.getEncontroSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getEncontroService().alterar(encontroSelecionado);
            }    
           
            addSucessMessage("Encontro salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar encontro: " + encontroSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getEncontroService().remover(encontroSelecionado);
            addSucessMessage("Encontro deletado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao excluir encontro: " + encontroSelecionado.toString());
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
    public Encontro getEncontroSelecionado() {
        return encontroSelecionado;
    }

    public void setEncontroSelecionado(Encontro encontroSelecionado) {
        this.encontroSelecionado = encontroSelecionado;
    }

    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }

    public List<Encontro> getListaEncontro() {
        return listaEncontro;
    }

    public void setListaEncontro(List<Encontro> listaEncontro) {
        this.listaEncontro = listaEncontro;
    }

    public List<Usuario> getListaAcademico() {
        return listaAcademico;
    }

    public void setListaAcademico(List<Usuario> listaAcademico) {
        this.listaAcademico = listaAcademico;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<Encontro> getListarEncontroQueCadastrei() {
        return listarEncontroQueCadastrei;
    }

    public void setListarEncontroQueCadastrei(List<Encontro> listarEncontroQueCadastrei) {
        this.listarEncontroQueCadastrei = listarEncontroQueCadastrei;
    }

    public List<Encontro> getListarEncontroQueFuiChamado() {
        return listarEncontroQueFuiChamado;
    }

    public void setListarEncontroQueFuiChamado(List<Encontro> listarEncontroQueFuiChamado) {
        this.listarEncontroQueFuiChamado = listarEncontroQueFuiChamado;
    }
    
    
}
