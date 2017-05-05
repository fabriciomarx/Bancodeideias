package br.com.bancodeideias.controller;

import static br.com.bancodeideias.controller.GenericController.addErrorMessage;
import br.com.bancodeideias.domain.PropostaTcc;
import br.com.bancodeideias.domain.SituacaoProjeto;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.PropostaTccService;
import br.com.bancodeideias.service.SituacaoProjetoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "situacaoProjetoController")
@SessionScoped
public class SituacaoProjetoController extends GenericController implements Serializable{
    
    private SituacaoProjeto             situacaoProjetoSelecionada;
    private SituacaoProjetoService      situacaoProjetoService;
    private List<SituacaoProjeto>       listaSituacoes;
    private List<SituacaoProjeto>       listaSituaçoesCooord;
    
    private List<PropostaTcc>           listaProjetos;
    private PropostaTccService          propostaTccService;
    
    @PostConstruct
    public void preRenderPage(){
        this.resset();
        this.listar();
    }

    private void resset() {
        situacaoProjetoSelecionada      = new SituacaoProjeto();
        situacaoProjetoService          = new SituacaoProjetoService();
        listaSituacoes                  = new ArrayList<>();
        listaSituaçoesCooord            = new ArrayList<>();
        
        listaProjetos                   = new ArrayList<>();
        propostaTccService              = new PropostaTccService();
        
    }

    private void listar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        if (usuarioLogado.getTipoUsuario().equals("Coordenador")) {
            listaSituaçoesCooord = this.getSituacaoProjetoService().listarSituaçoesCooord();
        }

        listaSituacoes = this.getSituacaoProjetoService().listar();
        listaProjetos = this.getPropostaTccService().listarProjetos();

    }

    public String salvar() {
        try {
            this.getSituacaoProjetoService().salvar(situacaoProjetoSelecionada);
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar situação do projeto: " + situacaoProjetoSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getSituacaoProjetoService().alterar(situacaoProjetoSelecionada);
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar situação do projeto: " + situacaoProjetoSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getSituacaoProjetoService().remover(situacaoProjetoSelecionada);
        } catch (Exception e) {
            addErrorMessage("Erro ao remover situação do projeto: " + situacaoProjetoSelecionada.toString());
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

    /* ============== Gets and Sets ============ */
    public SituacaoProjeto getSituacaoProjetoSelecionada() {
        return situacaoProjetoSelecionada;
    }

    public void setSituacaoProjetoSelecionada(SituacaoProjeto situacaoProjetoSelecionada) {
        this.situacaoProjetoSelecionada = situacaoProjetoSelecionada;
    }

    public SituacaoProjetoService getSituacaoProjetoService() {
        return situacaoProjetoService;
    }

    public void setSituacaoProjetoService(SituacaoProjetoService situacaoProjetoService) {
        this.situacaoProjetoService = situacaoProjetoService;
    }

    public List<SituacaoProjeto> getListaSituacoes() {
        return listaSituacoes;
    }

    public void setListaSituacoes(List<SituacaoProjeto> listaSituacoes) {
        this.listaSituacoes = listaSituacoes;
    }

    public List<PropostaTcc> getListaProjetos() {
        return listaProjetos;
    }

    public void setListaProjetos(List<PropostaTcc> listaProjetos) {
        this.listaProjetos = listaProjetos;
    }

    public PropostaTccService getPropostaTccService() {
        return propostaTccService;
    }

    public void setPropostaTccService(PropostaTccService propostaTccService) {
        this.propostaTccService = propostaTccService;
    }

    public List<SituacaoProjeto> getListaSituaçoesCooord() {
        return listaSituaçoesCooord;
    }

    public void setListaSituaçoesCooord(List<SituacaoProjeto> listaSituaçoesCooord) {
        this.listaSituaçoesCooord = listaSituaçoesCooord;
    }

}
