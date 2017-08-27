package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Ideia;
import br.com.bancodeideias.domain.Usuario;
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
import org.primefaces.context.RequestContext;

@Named(value = "ideiaController")
@SessionScoped
public class IdeiaController extends GenericController implements Serializable {

    private Ideia                           ideiaSelecionada;
    private IdeiaService                    ideiaService;
    
    private List<Ideia>                     listaIdeia;
    private List<Ideia>                     listaIdeiasPendente;
    private List<Ideia>                     listaIdeiasLogado;
    
    private List<Ideia>                     listaIdeiasdaUniversidade;
    private List<Ideia>                     listarIdeiasPendentesdaUniversidade;
    private List<Ideia>                     listarAnaliseIdeiasParaUniversidade;
    
    

    private List<Usuario>                   listaUsuario;
    private UsuarioService                  usuarioService;
    
    private String                          tipoIdeiaFiltro; //filtro de ideias por tipo
       

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    public void resset() {
        ideiaSelecionada                    = new Ideia();
        ideiaService                        = new IdeiaService();
        
        listaIdeia                          = new ArrayList<>();
        listaIdeiasPendente                 = new ArrayList<>();
        listaIdeiasLogado                   = new ArrayList<>();
        
        listaIdeiasdaUniversidade           = new ArrayList<>();
        listarIdeiasPendentesdaUniversidade = new ArrayList<>();
        listarAnaliseIdeiasParaUniversidade = new ArrayList<>();

        listaUsuario                        = new ArrayList<>();
        usuarioService                      = new UsuarioService();
    }
    

    //Utilizado para filtrar os tipos da ideia selecionado (universidade - ideias)
    public void listarTiposIdeiaSelecionado() {
        listaIdeiasdaUniversidade = this.getIdeiaService().listarTiposIdeiaSelecionado(tipoIdeiaFiltro);
    }

    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  

        switch (usuarioLogado.getTipoUsuario()) {
            case "Coordenador":
            case "Professor":
                listaIdeia          = this.getIdeiaService().listarIdeiasAprovadas(); //  todas ideias
                listaIdeiasPendente = this.getIdeiaService().listarIdeiasPendentes(); // ideias pendentes somente da universidade
                listaIdeiasLogado   = this.getIdeiaService().listarIdeiasLogado(); // somente as ideias do usuario logado
                break;
            case "Aluno":
                listaIdeiasLogado   = this.getIdeiaService().listarIdeiasLogado(); // somente as ideias do usuario logado
                listaIdeia          = this.getIdeiaService().listarIdeiasAprovadas();
                break;
            case "Universidade":
                listaIdeiasdaUniversidade = this.getIdeiaService().listarIdeiasdaUniversidade();
                listarIdeiasPendentesdaUniversidade = this.getIdeiaService().listarIdeiasPendentesdaUniversidade();
                listarAnaliseIdeiasParaUniversidade = this.getIdeiaService().listarAnaliseIdeiasParaUniversidade();
                break;
            case "Admin":
                listaIdeia          = this.getIdeiaService().listar(); //  todas ideias
                listaUsuario        = this.getUsuarioService().listar();
                break;
            default:
                break;
        }
         
        
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  
        try {
            //SE O USUARIO FOR ALUNO QUANDO ELE INSERIR UMA IDEIA A SITUAÇÃO É PENDENTE COMO DEFAUT
            if (usuarioLogado.getTipoUsuario().equals("Aluno")) {
                this.getIdeiaSelecionada().setSituacao("Em analise"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
                this.getIdeiaSelecionada().setDisponibilidade("Em analise"); //SALVANDO A DISPONIBILIDADE AUTOMATICO
            } else {
                this.getIdeiaSelecionada().setDisponibilidade("Disponível"); //SALVANDO A DISPONIBILIDADE AUTOMATICO
                this.getIdeiaSelecionada().setSituacao("Aprovado"); //INSERINDO A SITUAÇÃO ATIVO COMO DEFAUT
            }
            //SE O USUARIO FOR ADMIN QUANDO ELE INSERIR UMA IDEIA A SITUAÇÃO É ATIVO COMO DEFAUT

            
            this.getIdeiaSelecionada().setDataInscricao(new Date());  //SALVANDO A DATA ATUAL AUTOMATICO
            this.getIdeiaSelecionada().setUsuario(usuarioLogado); //INSERINDO O USUARIO AUTOMATICO
            this.getIdeiaService().salvar(ideiaSelecionada);
            addSucessMessage("Ideia salva com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar ideia: " + ideiaSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getIdeiaService().alterar(ideiaSelecionada);
            addSucessMessage("Ideia editada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar ideia: " + ideiaSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }
    
    /* Metodo que Universidade/Coordenador/Professor utiliza para analisar a ideia pendente*/
    public String alterarParaAnalise() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  
        try {
            this.getIdeiaSelecionada().setDisponibilidade("Disponível"); //SALVANDO A DISPONIBILIDADE AUTOMATICO
            this.getIdeiaSelecionada().setDataAnalise(new Date()); // setando a ideia automatico
            this.getIdeiaSelecionada().setAnalista(usuarioLogado); // setando o academico analista automatico
            this.getIdeiaService().alterar(ideiaSelecionada);
            addSucessMessage("Ideia analisada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao analisar ideia: " + ideiaSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getIdeiaService().remover(ideiaSelecionada);
            addSucessMessage("Ideia deletada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar ideia: " + ideiaSelecionada.toString());
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
    
    /* Metodo para chamar a pagina de inclusao de proposta */
    public String doIncluirNaProposta() {
        return "/paginas/aluno/proposta-tcc/incluir.xhtml?faces-redirect=true";
    }

    // ============ GETS AND SETS =========== 
    public Ideia getIdeiaSelecionada() {
        return ideiaSelecionada;
    }

    public void setIdeiaSelecionada(Ideia ideiaSelecionada) {
        this.ideiaSelecionada = ideiaSelecionada;
    }

    public IdeiaService getIdeiaService() {
        return ideiaService;
    }

    public void setIdeiaService(IdeiaService ideiaService) {
        this.ideiaService = ideiaService;
    }

    public List<Ideia> getListaIdeia() {
        return listaIdeia;
    }

    public void setListaIdeia(List<Ideia> listaIdeia) {
        this.listaIdeia = listaIdeia;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<Ideia> getListaIdeiasPendente() {
        return listaIdeiasPendente;
    }

    public void setListaIdeiasPendente(List<Ideia> listaIdeiasPendente) {
        this.listaIdeiasPendente = listaIdeiasPendente;
    }

    public List<Ideia> getListaIdeiasLogado() {
        return listaIdeiasLogado;
    }

    public void setListaIdeiasLogado(List<Ideia> listaIdeiasLogado) {
        this.listaIdeiasLogado = listaIdeiasLogado;
    }

    public List<Ideia> getListaIdeiasdaUniversidade() {
        return listaIdeiasdaUniversidade;
    }

    public void setListaIdeiasdaUniversidade(List<Ideia> listaIdeiasdaUniversidade) {
        this.listaIdeiasdaUniversidade = listaIdeiasdaUniversidade;
    }

    public List<Ideia> getListarIdeiasPendentesdaUniversidade() {
        return listarIdeiasPendentesdaUniversidade;
    }

    public void setListarIdeiasPendentesdaUniversidade(List<Ideia> listarIdeiasPendentesdaUniversidade) {
        this.listarIdeiasPendentesdaUniversidade = listarIdeiasPendentesdaUniversidade;
    }

    public String getTipoIdeiaFiltro() {
        return tipoIdeiaFiltro;
    }

    public void setTipoIdeiaFiltro(String tipoIdeiaFiltro) {
        this.tipoIdeiaFiltro = tipoIdeiaFiltro;
    }

    public List<Ideia> getListarAnaliseIdeiasParaUniversidade() {
        return listarAnaliseIdeiasParaUniversidade;
    }

    public void setListarAnaliseIdeiasParaUniversidade(List<Ideia> listarAnaliseIdeiasParaUniversidade) {
        this.listarAnaliseIdeiasParaUniversidade = listarAnaliseIdeiasParaUniversidade;
    }
    
    
    

}
