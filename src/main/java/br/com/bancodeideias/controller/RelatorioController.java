package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Encontro;
import br.com.bancodeideias.domain.Relatorio;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.EncontroService;
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

    private Usuario                     usuario;
    
    private Relatorio                   relatorioSelecionado;
    private RelatorioService            relatorioService;
    
    private List<Relatorio>             listaRelatorio;
   
    private List<Usuario>               listaAluno;
    private List<Usuario>               listaProfessores;
    private UsuarioService              usuarioService;
    
    private List<Encontro>              listaEncontros;
    private EncontroService             encontroService;
    

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    public void resset() {
        usuario                         = new Usuario();
        
        relatorioSelecionado            = new Relatorio();
        relatorioService                = new RelatorioService();
        
        listaRelatorio                  = new ArrayList<>();
        
        usuarioService                  = new UsuarioService();
        listaProfessores                = new ArrayList<>();
        listaAluno                      = new ArrayList<>();
        
        listaEncontros                  = new ArrayList<>();
        encontroService                 = new EncontroService();
        
    }
    
    /* Metodo utilizado para filtro de relatorios, utilizado no ORIENTADOR/RELATORIOS */
    public void listaRelatorios() {
        listaRelatorio = this.getRelatorioService().listRelatorioAlunoSelecionado(usuario.getIdUsuario());
    }
    
    /* Metodo utilizado para filtro de relatorios, utilizado no COORDENADOR/RELATORIOS */
    public void listaRelatoriosCoord() {
        listaRelatorio = this.getRelatorioService().listarRelatorioAlunoSelecionadoParaCoord(usuario.getIdUsuario());
    }
    
    /* Metodo utilizado para filtro de relatorios, utilizado na UNIVERSIDADE/RELATORIOS */
    public void listaRelatoriosUniversidade() {
        listaRelatorio = this.getRelatorioService().listarRelatorioAlunoSelecionadoParaUniversidade(usuario.getIdUsuario());
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
                listaEncontros = this.getEncontroService().listarEncontrosRealizadosAluno(); //provisorio
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
        listaAluno       = this.getUsuarioService().listaAlunos();
    }

    public String salvar() {
        try {
            this.getRelatorioSelecionado().setDataInscricao(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getRelatorioSelecionado().setStatus("Ainda não visualizado"); //SALVANDO O STATUS AUTOMATICO
            this.getRelatorioService().salvar(relatorioSelecionado);

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
    
    public String doAlterarStatus() {
        return "alterarStatus.xhtml?faces-redirect=true";
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

    public List<Usuario> getListaAluno() {
        return listaAluno;
    }

    public void setListaAluno(List<Usuario> listaAluno) {
        this.listaAluno = listaAluno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Encontro> getListaEncontros() {
        return listaEncontros;
    }

    public void setListaEncontros(List<Encontro> listaEncontros) {
        this.listaEncontros = listaEncontros;
    }

    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }
    
    
    
    
}
