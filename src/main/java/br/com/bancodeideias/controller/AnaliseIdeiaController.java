package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.AnaliseIdeia;
import br.com.bancodeideias.domain.Ideia;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.AnaliseIdeiaService;
import br.com.bancodeideias.service.IdeiaService;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "analiseIdeiaController")
@SessionScoped
public class AnaliseIdeiaController extends GenericController implements Serializable {

    private AnaliseIdeia            analiseIdeiaSelecionada;
    private AnaliseIdeiaService     analiseIdeiaService;
    private List<AnaliseIdeia>      listaAnaliseIdeia;
    private List<AnaliseIdeia>      listaAnaliseIdeiaParaUniversidade;

    private List<Ideia>             listaIdeia;
    private List<Ideia>             listaIdeiaParaUniversidade;
    private IdeiaService            ideiaService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        analiseIdeiaSelecionada             = new AnaliseIdeia();
        analiseIdeiaService                 = new AnaliseIdeiaService();
        listaAnaliseIdeia                   = new ArrayList<>();
        listaAnaliseIdeiaParaUniversidade   = new ArrayList<>();

        listaIdeia                          = new ArrayList<>();
        listaIdeiaParaUniversidade          = new ArrayList<>();
        ideiaService                        = new IdeiaService();

    }

    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        if (usuarioLogado.getTipoUsuario().equals("Admin")) {
            listaAnaliseIdeia = this.getAnaliseIdeiaService().listar();
            listaIdeia = this.getIdeiaService().listar();
        } else if (usuarioLogado.getTipoUsuario().equals("Universidade")) {
            listaAnaliseIdeiaParaUniversidade = this.getAnaliseIdeiaService().listarAnalisesParaUniversidade();
            listaIdeiaParaUniversidade = this.getIdeiaService().listarIdeiasdaUniversidade();
        }

    }

    public void salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            this.getAnaliseIdeiaSelecionada().setDataAnalise(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getAnaliseIdeiaSelecionada().setAcademicoAnalista(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO

            //ALTERANDO A SITUAÇÃO DA IDEIA PARA ATIVA
            Ideia ideia = this.analiseIdeiaSelecionada.getIdeia();
            ideia.setSituacao("A");
            this.getIdeiaService().alterar(ideia);

            this.getAnaliseIdeiaService().salvar(analiseIdeiaSelecionada);
            addSucessMessage("Analise salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar analise: " + analiseIdeiaSelecionada.toString());
        }
        this.resset();
        this.listar();

        ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath()
                    + "/paginas/administrador/analises/ideias/listar.xhtml?faces-redirect=true");
        } catch (IOException e) {
        }
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
            //ALTERANDO A SITUAÇÃO DA IDEIA PARA PENDENTE
            Ideia ideia = this.analiseIdeiaSelecionada.getIdeia();
            ideia.setSituacao("P");
            this.getIdeiaService().alterar(ideia);

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

    public List<AnaliseIdeia> getListaAnaliseIdeiaParaUniversidade() {
        return listaAnaliseIdeiaParaUniversidade;
    }

    public void setListaAnaliseIdeiaParaUniversidade(List<AnaliseIdeia> listaAnaliseIdeiaParaUniversidade) {
        this.listaAnaliseIdeiaParaUniversidade = listaAnaliseIdeiaParaUniversidade;
    }

    public List<Ideia> getListaIdeiaParaUniversidade() {
        return listaIdeiaParaUniversidade;
    }

    public void setListaIdeiaParaUniversidade(List<Ideia> listaIdeiaParaUniversidade) {
        this.listaIdeiaParaUniversidade = listaIdeiaParaUniversidade;
    }

}
