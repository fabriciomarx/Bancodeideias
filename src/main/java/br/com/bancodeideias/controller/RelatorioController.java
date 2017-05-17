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
    private List<Relatorio>     listaTodosRelatorio;       // admin utiliza esse metodo
    private List<Relatorio>     listaRelatoriosLogado;     // aluno utiliza esse metodo
    private List<Relatorio>     listaRelatorioUniLogada;   // universidade utiliza esse metodo
    private List<Relatorio>     listaRelatorioCoordLogado; // coordenador utiliza esse metodo
    private List<Relatorio>     listaRelatoriosOrientadorLogado;
    
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
        listaTodosRelatorio             = new ArrayList<>();
        listaRelatoriosLogado           = new ArrayList<>();
        listaRelatorioUniLogada         = new ArrayList<>();
        listaRelatorioCoordLogado       = new ArrayList<>();
        listaRelatoriosOrientadorLogado = new ArrayList<>();
        
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
                listaTodosRelatorio = this.getRelatorioService().listar();
                break;
            case "Aluno":
                listaRelatoriosLogado = this.getRelatorioService().listaRelatorioLogado();
                break;
            case "Universidade":
                listaRelatorioUniLogada = this.getRelatorioService().listaRelatoriosUniLogada();
                break;
            case "Coordenador":
                listaRelatorioCoordLogado = this.getRelatorioService().listaRelatoriosCoordLogado();
            case "Professor":
                listaRelatoriosOrientadorLogado = this.getRelatorioService().listaRelatoriosOrientadorLogado();

            default:
                break;
        }

        listaProfessores = this.getUsuarioService().listaProfessores();
        listaAcademicos = this.getUsuarioService().listaAcademicos();
        
        
    }
    
    public void list(){
        listRelatorioAlunoSelecionado = this.getRelatorioService().listRelatorioAlunoSelecionado(relatorioSelecionado.getIdRelatorio());
    }

    public String salvar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            //SE O USUARIO FOR ADMIN QUANDO ELE INSERIR UM RELATORIO NAO INSERIR O ACADEMICO AUTOMATICO
            if (usuarioLogado.getTipoUsuario().equals("Admin")) {
                this.getRelatorioSelecionado().setDataRelatorio(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                this.getRelatorioSelecionado().setStatusOrientador("Enviado e ainda não visto"); //SALVANDO O STATUS AUTOMATICO
                this.getRelatorioService().salvar(relatorioSelecionado);
            } else {
                this.getRelatorioSelecionado().setDataRelatorio(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                this.getRelatorioSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getRelatorioSelecionado().setStatusOrientador("Enviado e ainda não visto"); //SALVANDO O STATUS AUTOMATICO
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
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            if (usuarioLogado.getTipoUsuario().equals("Professor")) {
                this.getRelatorioService().alterar(relatorioSelecionado);
                addSucessMessage("Relatorio editado com sucesso");

            } else {
                this.getRelatorioSelecionado().setDataRelatorio(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                this.getRelatorioSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getRelatorioService().alterar(relatorioSelecionado);
                addSucessMessage("Relatorio editado com sucesso");
            }
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

    public List<Relatorio> getListaTodosRelatorio() {
        return listaTodosRelatorio;
    }

    public void setListaTodosRelatorio(List<Relatorio> listaTodosRelatorio) {
        this.listaTodosRelatorio = listaTodosRelatorio;
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

    public List<Relatorio> getListaRelatoriosLogado() {
        return listaRelatoriosLogado;
    }

    public void setListaRelatoriosLogado(List<Relatorio> listaRelatoriosLogado) {
        this.listaRelatoriosLogado = listaRelatoriosLogado;
    }

    public List<Relatorio> getListaRelatorioUniLogada() {
        return listaRelatorioUniLogada;
    }

    public void setListaRelatorioUniLogada(List<Relatorio> listaRelatorioUniLogada) {
        this.listaRelatorioUniLogada = listaRelatorioUniLogada;
    }

    public List<Relatorio> getListaRelatorioCoordLogado() {
        return listaRelatorioCoordLogado;
    }

    public void setListaRelatorioCoordLogado(List<Relatorio> listaRelatorioCoordLogado) {
        this.listaRelatorioCoordLogado = listaRelatorioCoordLogado;
    }

    public List<Relatorio> getListaRelatoriosOrientadorLogado() {
        return listaRelatoriosOrientadorLogado;
    }

    public void setListaRelatoriosOrientadorLogado(List<Relatorio> listaRelatoriosOrientadorLogado) {
        this.listaRelatoriosOrientadorLogado = listaRelatoriosOrientadorLogado;
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
