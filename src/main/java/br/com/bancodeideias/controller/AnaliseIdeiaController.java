package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.AnaliseIdeia;
import br.com.bancodeideias.domain.Ideia;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.AnaliseIdeiaService;
import br.com.bancodeideias.service.IdeiaService;
import br.com.bancodeideias.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "analiseIdeiaController")
@SessionScoped
public class AnaliseIdeiaController extends GenericController implements Serializable {

    private AnaliseIdeia            analiseIdeiaSelecionada;
    private AnaliseIdeiaService     analiseIdeiaService;
    private List<AnaliseIdeia>      listaAnaliseIdeia;

    private List<Usuario>           listaAcademico;
    private UsuarioService          usuarioService;

    private List<Ideia>             listaIdeia;
    private IdeiaService            ideiaService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        analiseIdeiaSelecionada     = new AnaliseIdeia();
        analiseIdeiaService         = new AnaliseIdeiaService();
        listaAnaliseIdeia           = new ArrayList<>();

        listaAcademico              = new ArrayList<>();
        usuarioService              = new UsuarioService();

        listaIdeia                  = new ArrayList<>();
        ideiaService                = new IdeiaService();

    }

    private void listar() {
        listaAnaliseIdeia   = this.getAnaliseIdeiaService().listar();
        listaAcademico      = this.getUsuarioService().listar();
        listaIdeia          = this.getIdeiaService().listar();
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            this.getAnaliseIdeiaSelecionada().setDataAnalise(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getAnaliseIdeiaSelecionada().setAcademicoAnalista(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
            
            //ALTERANDO A SITUAÇÃO DA IDEIA PARA ATIVA
            Ideia ideia = this.analiseIdeiaSelecionada.getIdeia();
            ideia.setSituacao("A");
            this.getIdeiaService().alterar(ideia);
            /* ASSIM NAO DEU CERTO: 
               this.getAnaliseIdeiaSelecionada().getIdeia().setSituacao("A");*/
            
            this.getAnaliseIdeiaService().salvar(analiseIdeiaSelecionada);
            addSucessMessage("Analise salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar analise: " + analiseIdeiaSelecionada.toString());
        }
        this.resset(); 
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            this.getAnaliseIdeiaSelecionada().setDataAnalise(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getAnaliseIdeiaSelecionada().setAcademicoAnalista(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
            this.getAnaliseIdeiaService().alterar(analiseIdeiaSelecionada);
            addSucessMessage("Analise editada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar analise: " + analiseIdeiaSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getAnaliseIdeiaService().remover(analiseIdeiaSelecionada);
            addSucessMessage("Analise deletada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar analise: " + analiseIdeiaSelecionada.toString());
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
    public AnaliseIdeia getAnaliseIdeiaSelecionada() {
        return analiseIdeiaSelecionada;
    }

    public void setAnaliseIdeiaSelecionada(AnaliseIdeia analiseIdeiaSelecionada) {
        this.analiseIdeiaSelecionada = analiseIdeiaSelecionada;
    }

    public AnaliseIdeiaService getAnaliseIdeiaService() {
        return analiseIdeiaService;
    }

    public void setAnaliseIdeiaService(AnaliseIdeiaService analiseIdeiaService) {
        this.analiseIdeiaService = analiseIdeiaService;
    }

    public List<AnaliseIdeia> getListaAnaliseIdeia() {
        return listaAnaliseIdeia;
    }

    public void setListaAnaliseIdeia(List<AnaliseIdeia> listaAnaliseIdeia) {
        this.listaAnaliseIdeia = listaAnaliseIdeia;
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

    public List<Ideia> getListaIdeia() {
        return listaIdeia;
    }

    public void setListaIdeia(List<Ideia> listaIdeia) {
        this.listaIdeia = listaIdeia;
    }

    public IdeiaService getIdeiaService() {
        return ideiaService;
    }

    public void setIdeiaService(IdeiaService ideiaService) {
        this.ideiaService = ideiaService;
    }

}
