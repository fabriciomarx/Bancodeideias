package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Relatorio;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.RelatorioService;
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

@Named(value = "relatorioController")
@SessionScoped
public class RelatorioController extends GenericController implements Serializable {

    private Relatorio           relatorioSelecionado;
    private RelatorioService    relatorioService;
    private List<Relatorio>     listaRelatorio;
   
    private List<Usuario>       listaAcademicos;
    private List<Usuario>       listaProfessores;
    private UsuarioService      usuarioService;
    
    
    private List<Relatorio>     listRelatorioAlunoSelecionado; 
    

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    public void resset() {
        relatorioSelecionado            = new Relatorio();
        relatorioService                = new RelatorioService();
        
        listaRelatorio                  = new ArrayList<>();
        
        usuarioService                  = new UsuarioService();
        listaProfessores                = new ArrayList<>();
        listaAcademicos                 = new ArrayList<>();
        listRelatorioAlunoSelecionado   = new ArrayList<>();
        
    }

    public void listar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        switch (usuarioLogado.getTipoUsuario()) {
            case "Admin":
                listaRelatorio = this.getRelatorioService().listar();
                break;
            case "Aluno":
                listaRelatorio = this.getRelatorioService().listaRelatorioLogado();
                break;
            case "Universidade":
                listaRelatorio = this.getRelatorioService().listaRelatoriosUniLogada();
                break;
            case "Coordenador":
                listaRelatorio = this.getRelatorioService().listaRelatoriosCoordLogado();
                break;
            case "Professor":
                listaRelatorio = this.getRelatorioService().listaRelatoriosOrientadorLogado();
                break;
            default:
                break;

        }
        listaProfessores = this.getUsuarioService().listaProfessores();
        listaAcademicos = this.getUsuarioService().listaAcademicos();

    }

    public void list() {
        listaRelatorio = this.getRelatorioService().listRelatorioAlunoSelecionado(relatorioSelecionado.getAcademico().getIdUsuario());
    }

    public String salvar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            //SE O USUARIO FOR ADMIN QUANDO ELE INSERIR UM RELATORIO NAO INSERIR O ACADEMICO AUTOMATICO
            if (usuarioLogado.getTipoUsuario().equals("Admin")) {
                this.getRelatorioSelecionado().setDataInscricao(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                this.getRelatorioSelecionado().setStatus("Ainda não visualizado"); //SALVANDO O STATUS AUTOMATICO
                this.getRelatorioService().salvar(relatorioSelecionado);
            } else {
                this.getRelatorioSelecionado().setDataInscricao(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                this.getRelatorioSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getRelatorioSelecionado().setStatus("Ainda não visualizado"); //SALVANDO O STATUS AUTOMATICO
                this.getRelatorioService().salvar(relatorioSelecionado);
            }

            addSucessMessage("Relatorio salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar Relatorio: " + relatorioSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getRelatorioService().alterar(relatorioSelecionado);
            addSucessMessage("Relatorio editado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar Relatorio: " + relatorioSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getRelatorioService().remover(relatorioSelecionado);
            addSucessMessage("Relatorio delatado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar Relatorio: " + relatorioSelecionado.toString());
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
    public Relatorio getRelatorioSelecionado() {
        return relatorioSelecionado;
    }

    public void setRelatorioSelecionado(Relatorio relatorioSelecionado) {
        this.relatorioSelecionado = relatorioSelecionado;
    }

    public RelatorioService getRelatorioService() {
        return relatorioService;
    }

    public void setRelatorioService(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public List<Relatorio> getListaRelatorio() {
        return listaRelatorio;
    }

    public void setListaRelatorio(List<Relatorio> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    public List<Usuario> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Usuario> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<Usuario> getListaAcademicos() {
        return listaAcademicos;
    }

    public void setListaAcademicos(List<Usuario> listaAcademicos) {
        this.listaAcademicos = listaAcademicos;
    }

    public List<Relatorio> getListRelatorioAlunoSelecionado() {
        return listRelatorioAlunoSelecionado;
    }

    public void setListRelatorioAlunoSelecionado(List<Relatorio> listRelatorioAlunoSelecionado) {
        this.listRelatorioAlunoSelecionado = listRelatorioAlunoSelecionado;
    }
}
