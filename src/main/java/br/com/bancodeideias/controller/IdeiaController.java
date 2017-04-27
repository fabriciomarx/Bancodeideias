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

@Named(value = "ideiaController")
@SessionScoped
public class IdeiaController extends GenericController implements Serializable {

    private Ideia           ideiaSelecionada;
    private IdeiaService    ideiaService;
    
    private List<Ideia>     listaIdeia;
    private List<Ideia>     listaIdeiasPendente;
    private List<Ideia>     listaIdeiasLogado;
    private List<Ideia>     listaIdeiasdaUniversidade;

    private List<Usuario>   listaUsuario;
    private UsuarioService  usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    public void resset() {
        ideiaSelecionada            = new Ideia();
        ideiaService                = new IdeiaService();
        
        listaIdeia                  = new ArrayList<>();
        listaIdeiasPendente         = new ArrayList<>();
        listaIdeiasLogado           = new ArrayList<>();
        listaIdeiasdaUniversidade   = new ArrayList<>();

        listaUsuario                = new ArrayList<>();
        usuarioService              = new UsuarioService();
    }

    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  

        if(usuarioLogado.getTipoUsuario().equals("Admin"))
            listaIdeia                  = this.getIdeiaService().listar(); //  todas ideias 
        else if(usuarioLogado.getTipoUsuario().equals("Coordenador"))
            listaIdeiasPendente         = this.getIdeiaService().listarPendentes(); // pendentes
        else if(usuarioLogado.getTipoUsuario().equals("Universidade"))
            listaIdeiasdaUniversidade   = this.getIdeiaService().listarIdeiasdaUniversidade();
        
        listaIdeiasLogado           = this.getIdeiaService().listarIdeiasLogado(); // somente a do usuario logado
        listaUsuario                = this.getUsuarioService().listar();
        
       
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  
        try {
            //SE O USUARIO FOR ALUNO QUANDO ELE INSERIR UMA IDEIA A SITUAÇÃO É PENDENTE COMO DEFAUT
            if (usuarioLogado.getTipoUsuario().equals("Aluno")) {
                this.getIdeiaSelecionada().setSituacao("P"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
            }
            //SE O USUARIO FOR ADMIN QUANDO ELE INSERIR UMA IDEIA A SITUAÇÃO É ATIVO COMO DEFAUT
            if (usuarioLogado.getTipoUsuario().equals("Admin") || usuarioLogado.getTipoUsuario().equals("Universidade") 
                   || usuarioLogado.getTipoUsuario().equals("Coordenador")  ) {
                this.getIdeiaSelecionada().setSituacao("A"); //INSERINDO A SITUAÇÃO ATIVO COMO DEFAUT
            }
            this.getIdeiaSelecionada().setDataIdeia(new Date());  //SALVANDO A DATA ATUAL AUTOMATICO
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
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  
        try {
            //SE O USUARIO FOR ALUNO QUANDO ELE INSERIR UMA IDEIA A SITUAÇÃO É PENDENTE COMO DEFAUT
            if (usuarioLogado.getTipoUsuario().equals("Aluno")) {
                this.getIdeiaSelecionada().setSituacao("P"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
            }
             //SE O USUARIO FOR ADMIN QUANDO ELE INSERIR UMA IDEIA A SITUAÇÃO É ATIVO COMO DEFAUT
            if (usuarioLogado.getTipoUsuario().equals("Admin") || usuarioLogado.getTipoUsuario().equals("Universidade")  
                    || usuarioLogado.getTipoUsuario().equals("Coordenador") ) {
                this.getIdeiaSelecionada().setSituacao("A"); //INSERINDO A SITUAÇÃO ATIVO COMO DEFAUT
            }
            this.getIdeiaSelecionada().setDataIdeia(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
            this.getIdeiaSelecionada().setUsuario(usuarioLogado); //INSERINDO O USUARIO AUTOMATICO
            this.getIdeiaService().alterar(ideiaSelecionada);
            addSucessMessage("Ideia editada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar ideia: " + ideiaSelecionada.toString());
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
    
    

}
