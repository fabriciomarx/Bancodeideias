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
    private List<AnalisePropostatcc>    listaAnaliseParaUniversidade;

    private List<PropostaTcc>           listaProposta;
    private List<PropostaTcc>           listaPropostaParaUniversidade;
    private PropostaTccService          propostaTccService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        analisePropTccSelecionada       = new AnalisePropostatcc();
        analisePropostaTccService       = new AnalisePropostaTccService();
        listaAnalise                    = new ArrayList<>();
        listaAnaliseParaUniversidade    = new ArrayList<>();

        listaProposta                   = new ArrayList<>();
        listaPropostaParaUniversidade   = new ArrayList<>();
        propostaTccService              = new PropostaTccService();

    }

    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");  //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        
        if(usuarioLogado.getTipoUsuario().equals("Admin")){
            listaAnalise                    = this.getAnalisePropostaTccService().listar();
            listaProposta                   = this.getPropostaTccService().listarPendentes();
        }else if(usuarioLogado.getTipoUsuario().equals("Universidade")){
            listaAnaliseParaUniversidade    = this.getAnalisePropostaTccService().listarAnalisesParaUniversidade();
            listaPropostaParaUniversidade   = this.getPropostaTccService().listaPropostasPendentesDaUniv();
        }
        
        
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
            //ALTERANDO A SITUAÇÃO DA PROPOSTA PARA PENDENTE..caso a analise seja excluida
            PropostaTcc propostaTcc = this.analisePropTccSelecionada.getPropostaTcc();
            propostaTcc.setSituacao("P");
            this.getPropostaTccService().alterar(propostaTcc);
            
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

    public List<AnalisePropostatcc> getListaAnaliseParaUniversidade() {
        return listaAnaliseParaUniversidade;
    }

    public void setListaAnaliseParaUniversidade(List<AnalisePropostatcc> listaAnaliseParaUniversidade) {
        this.listaAnaliseParaUniversidade = listaAnaliseParaUniversidade;
    }

    public List<PropostaTcc> getListaPropostaParaUniversidade() {
        return listaPropostaParaUniversidade;
    }

    public void setListaPropostaParaUniversidade(List<PropostaTcc> listaPropostaParaUniversidade) {
        this.listaPropostaParaUniversidade = listaPropostaParaUniversidade;
    }
    
    
    
}
