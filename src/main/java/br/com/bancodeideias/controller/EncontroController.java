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

    private Usuario                 usuario;
    
    private Encontro                encontroSelecionado;
    private EncontroService         encontroService;

    private List<Encontro>          listaEncontro;

    private List<Usuario>           listaAluno;
    private List<Usuario>           listaProfessores;
    
    private UsuarioService          usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        usuario                     = new Usuario(); //usado no filtro de encontros
        
        encontroSelecionado         = new Encontro();
        encontroService             = new EncontroService();

        listaEncontro               = new ArrayList<>();

        listaAluno                  = new ArrayList<>();
        listaProfessores            = new ArrayList<>();
        
        usuarioService              = new UsuarioService();
    }
    
    public void listEncontros(){
        listaEncontro = this.getEncontroService().listarEncontrosAcademicoSelecionado(usuario.getIdUsuario());
    }
    
    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  

        switch (usuarioLogado.getTipoUsuario()) {
            case "Admin":
                listaEncontro = this.getEncontroService().listar();
                break;
            case "Aluno":
            case "Professor":
                listaEncontro = this.getEncontroService().listarEncontrosAlu_Prof();
                break;
            case "Coordenador":
                listaEncontro = this.getEncontroService().listarEncontrosParaCoord();
                break;
            case "Universidade":
                listaEncontro = this.getEncontroService().listarEncontrosParaUniv();
                break;
            default:
                break;
        }
        listaAluno       = this.getUsuarioService().listaAlunos();
        listaProfessores = this.getUsuarioService().listaProfessores();
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  

        try {
           if(usuarioLogado.getTipoUsuario().equals("Professor")){
                this.getEncontroSelecionado().setOrientador(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                
            }else if(usuarioLogado.getTipoUsuario().equals("Aluno")){
                this.getEncontroSelecionado().setAluno(usuarioLogado); 
            }
            this.getEncontroSelecionado().setStatus("Ainda não visualizado");
            this.getEncontroService().salvar(encontroSelecionado);
            addSucessMessage("Encontro salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar encontro: " + encontroSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getEncontroService().alterar(encontroSelecionado);
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
    
    public String doIncluirRelatorio(){
        return "/paginas/aluno/relatorios/incluir.xhtml?faces-redirect=true";
    }
    
    public String doAlterarStatus(){
        return "alterarStatus.xhtml?faces-redirect=true";
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
}
