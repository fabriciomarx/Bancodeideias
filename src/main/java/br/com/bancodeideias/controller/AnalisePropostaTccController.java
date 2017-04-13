package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.AnalisePropostatcc;
import br.com.bancodeideias.domain.PropostaTcc;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.AnalisePropostaTccService;
import br.com.bancodeideias.service.PropostaTccService;
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

@Named(value = "analisePropostaTccController")
@SessionScoped
public class AnalisePropostaTccController extends GenericController implements Serializable {

    private AnalisePropostatcc          analisePropTccSelecionada;
    private AnalisePropostaTccService   analisePropostaTccService;
    private List<AnalisePropostatcc>    listaAnalise;

    private List<PropostaTcc>           listaProposta;
    private PropostaTccService          propostaTccService;

    private List<Usuario>               listaAcademico;
    private UsuarioService              usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        analisePropTccSelecionada   = new AnalisePropostatcc();
        analisePropostaTccService   = new AnalisePropostaTccService();
        listaAnalise                = new ArrayList<>();

        listaProposta               = new ArrayList<>();
        propostaTccService          = new PropostaTccService();

        listaAcademico              = new ArrayList<>();
        usuarioService              = new UsuarioService();
    }

    private void listar() {
        listaAnalise    = this.getAnalisePropostaTccService().listar();
        listaProposta   = this.getPropostaTccService().listar();
        listaAcademico  = this.getUsuarioService().listar();
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");  //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            this.getAnalisePropTccSelecionada().setDataAnalise(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getAnalisePropTccSelecionada().setAcademicoAnalista(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
            
            //ALTERANDO A SITUAÇÃO DA PROPOSTA PARA ATIVA
            PropostaTcc propostaTcc = this.analisePropTccSelecionada.getPropostaTcc();
            propostaTcc.setSituacao("A");
            this.getPropostaTccService().alterar(propostaTcc);
            
            /* ASSIM NAO DEU CERTO : 
            this.getAnalisePropTccSelecionada().getPropostaTcc().setSituacao("A");*/
            
            this.getAnalisePropostaTccService().salvar(analisePropTccSelecionada);
            
            addSucessMessage("Analise salva com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar Analise: " + analisePropTccSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            this.getAnalisePropTccSelecionada().setDataAnalise(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getAnalisePropTccSelecionada().setAcademicoAnalista(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
            this.getAnalisePropostaTccService().alterar(analisePropTccSelecionada);
            addSucessMessage("Analise editada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar Analise: " + analisePropTccSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getAnalisePropostaTccService().remover(analisePropTccSelecionada);
            addSucessMessage("Analise excluida com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao excluir Analise: " + analisePropTccSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    //aceitar ou recusar proposta de tcc
    public String aceitar() {
        //this.getAnalisePropTccSelecionada().getPropostaTcc().setSituacao("A");
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String recusar() {
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
    public AnalisePropostatcc getAnalisePropTccSelecionada() {
        return analisePropTccSelecionada;
    }

    public void setAnalisePropTccSelecionada(AnalisePropostatcc analisePropTccSelecionada) {
        this.analisePropTccSelecionada = analisePropTccSelecionada;
    }

    public AnalisePropostaTccService getAnalisePropostaTccService() {
        return analisePropostaTccService;
    }

    public void setAnalisePropostaTccService(AnalisePropostaTccService analisePropostaTccService) {
        this.analisePropostaTccService = analisePropostaTccService;
    }

    public List<AnalisePropostatcc> getListaAnalise() {
        return listaAnalise;
    }

    public void setListaAnalise(List<AnalisePropostatcc> listaAnalise) {
        this.listaAnalise = listaAnalise;
    }

    public List<PropostaTcc> getListaProposta() {
        return listaProposta;
    }

    public void setListaProposta(List<PropostaTcc> listaProposta) {
        this.listaProposta = listaProposta;
    }

    public PropostaTccService getPropostaTccService() {
        return propostaTccService;
    }

    public void setPropostaTccService(PropostaTccService propostaTccService) {
        this.propostaTccService = propostaTccService;
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

}
