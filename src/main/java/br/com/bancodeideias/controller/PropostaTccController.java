package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Ideia;
import br.com.bancodeideias.domain.PropostaTcc;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.IdeiaService;
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

@Named(value = "propostaTccController")
@SessionScoped
public class PropostaTccController extends GenericController implements Serializable {

    private Usuario                     usuario;
    
    private PropostaTcc                 propostaTccSelecionada;
    private PropostaTccService          propostaTccService;
    
    private List<PropostaTcc>           listaPropostaTcc;
    private List<PropostaTcc>           listaPropostasPendentes;
    private List<PropostaTcc>           listaPropostasPendentesDaUniv; 
    private List<PropostaTcc>           listarPropostasQueOrientadorParticipa;
    
    private List<PropostaTcc>           listaPropostasPendentesDaUnivParaCoordenador ;
    
    private List<Ideia>                 listaIdeias;
    private IdeiaService                ideiaService;

    private List<Usuario>               listaProfessores;
    private List<Usuario>               listaAluno;
    
    private UsuarioService              usuarioService;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        usuario                                         = new Usuario();
        
        propostaTccSelecionada                          = new PropostaTcc();
        propostaTccService                              = new PropostaTccService();
        
        listaPropostaTcc                                = new ArrayList<>();
        listarPropostasQueOrientadorParticipa           = new ArrayList<>();
        listaPropostasPendentes                         = new ArrayList<>();
        listaPropostasPendentesDaUniv                   = new ArrayList<>();
      
        listaPropostasPendentesDaUnivParaCoordenador    = new ArrayList<>();
        
        listaIdeias                                     = new ArrayList<>();
        ideiaService                                    = new IdeiaService();

        listaProfessores                                = new ArrayList<>();
        listaAluno                                      = new ArrayList<>();
        
        usuarioService                                  = new UsuarioService();
    }
    
    /*Utilizado no filtro, usuario orientador-professsor - pagina aprovações de propostas*/
    public void listaPropostas() {
        listarPropostasQueOrientadorParticipa = this.getPropostaTccService().listarPropostasAlunoSelecionado(usuario.getIdUsuario());
        //listarPropostasQueOrientadorParticipa = this.getPropostaTccService().listarPropostasAlunoSelecionado(usuario.getIdUsuario());
    }
    
    /*Utilizado no filtro, usuario coordenador - pagina propostas de tcc */
    public void listaPropostasCoord() {
        listaPropostaTcc = this.getPropostaTccService().listarPropostasAlunoSelecionadoCoord(usuario.getIdUsuario());
    }

    private void listar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        switch (usuarioLogado.getTipoUsuario()) {
            case "Aluno":
                listaPropostaTcc = this.getPropostaTccService().listarPropostasLogado();
                break;
            case "Coordenador":
                listaPropostaTcc = this.getPropostaTccService().listarPropostasParaCoord();
                listaPropostasPendentesDaUnivParaCoordenador = this.getPropostaTccService().listaPropostasPendentesDaUnivParaCoordenador();
                break;
            case "Admin":
                listaPropostaTcc = this.getPropostaTccService().listar();
                break;
            case "Universidade":
                listaPropostasPendentesDaUniv = this.getPropostaTccService().listaPropostasPendentesDaUniv();
                listaPropostaTcc    = this.getPropostaTccService().listaPropostasDaUniv();
             case "Professor":
                listaPropostaTcc = this.getPropostaTccService().listarPropostasParaOrientador();
                listarPropostasQueOrientadorParticipa = this.getPropostaTccService().listarPropostasQueOrientadorParticipa();
                break;
            default:
                break;
        }
        listaIdeias      = this.getIdeiaService().listarIdeiasAprovadas()    ;
        listaProfessores = this.getUsuarioService().listaProfessores();
        listaAluno       = this.getUsuarioService().listaAlunos();
    }

    public String salvar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            
            switch (usuarioLogado.getTipoUsuario()) {
                case "Aluno":
                    this.getPropostaTccSelecionada().setSituacao("Em analise"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
                    this.getPropostaTccSelecionada().setAprovacaoOrientador("Em analise"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
                    this.getPropostaTccSelecionada().setDataInscricao(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                    this.getPropostaTccSelecionada().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                    this.getPropostaTccService().salvar(propostaTccSelecionada);
                    break;
                case "Admin":
                    this.getPropostaTccSelecionada().setSituacao("Em analise"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
                    this.getPropostaTccSelecionada().setDataInscricao(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                    this.getPropostaTccService().salvar(propostaTccSelecionada);
                    break;
                default:
                    break;
            }
            addSucessMessage("Proposta Tcc salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar proposta Tcc: " + propostaTccSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        try {
            if (usuarioLogado.getTipoUsuario().equals("Coordenador")) {
                //Ideia ideia = new Ideia();
                //ideia.setDisponibilidade("Indisponivel");
                //this.getPropostaTccSelecionada().setProblema(ideia);
                this.getPropostaTccSelecionada().setDataAnalise(new Date()); // se o usuario for coordenador, setar a data automatico
                this.getPropostaTccSelecionada().setAnalista(usuarioLogado);
                this.getPropostaTccService().alterar(propostaTccSelecionada);
            } else {
                this.getPropostaTccService().alterar(propostaTccSelecionada);
            }
            addSucessMessage("Proposta Tcc editada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao editar proposta Tcc: " + propostaTccSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getPropostaTccService().remover(propostaTccSelecionada);
            addSucessMessage("Proposta Tcc deletada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar proposta Tcc: " + propostaTccSelecionada.toString());
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
    
    public String doConsultarPropostaRecusada() {
        return "consultarMotivo.xhtml?faces-redirect=true";
    }
    
    

    /* Serve para o orientador aceitar se vai orientar a proposta do aluno */
    public String doAceitar() {
        return "aceitar.xhtml?faces-redirect=true";
    }

    /* Serve para o orientador recusar se vai orientar a proposta do aluno */
    public String doRecusar() {
        return "recusar.xhtml?faces-redirect=true";
    }

    // ============ GETS AND SETS ===========  
    public PropostaTcc getPropostaTccSelecionada() {
        return propostaTccSelecionada;
    }

    public void setPropostaTccSelecionada(PropostaTcc propostaTccSelecionada) {
        this.propostaTccSelecionada = propostaTccSelecionada;
    }

    public PropostaTccService getPropostaTccService() {
        return propostaTccService;
    }

    public void setPropostaTccService(PropostaTccService propostaTccService) {
        this.propostaTccService = propostaTccService;
    }

    public List<PropostaTcc> getListaPropostaTcc() {
        return listaPropostaTcc;
    }

    public void setListaPropostaTcc(List<PropostaTcc> listaPropostaTcc) {
        this.listaPropostaTcc = listaPropostaTcc;
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

    public List<PropostaTcc> getListaPropostasPendentes() {
        return listaPropostasPendentes;
    }

    public void setListaPropostasPendentes(List<PropostaTcc> listaPropostasPendentes) {
        this.listaPropostasPendentes = listaPropostasPendentes;
    }

    public List<Ideia> getListaIdeias() {
        return listaIdeias;
    }

    public void setListaIdeias(List<Ideia> listaIdeias) {
        this.listaIdeias = listaIdeias;
    }

    public IdeiaService getIdeiaService() {
        return ideiaService;
    }

    public void setIdeiaService(IdeiaService ideiaService) {
        this.ideiaService = ideiaService;
    }

    public List<PropostaTcc> getListaPropostasPendentesDaUniv() {
        return listaPropostasPendentesDaUniv;
    }

    public void setListaPropostasPendentesDaUniv(List<PropostaTcc> listaPropostasPendentesDaUniv) {
        this.listaPropostasPendentesDaUniv = listaPropostasPendentesDaUniv;
    }

    public List<Usuario> getListaAluno() {
        return listaAluno;
    }

    public void setListaAluno(List<Usuario> listaAluno) {
        this.listaAluno = listaAluno;
    }

    public List<PropostaTcc> getListaPropostasPendentesDaUnivParaCoordenador() {
        return listaPropostasPendentesDaUnivParaCoordenador;
    }

    public void setListaPropostasPendentesDaUnivParaCoordenador(List<PropostaTcc> listaPropostasPendentesDaUnivParaCoordenador) {
        this.listaPropostasPendentesDaUnivParaCoordenador = listaPropostasPendentesDaUnivParaCoordenador;
    }

    public List<PropostaTcc> getListarPropostasQueOrientadorParticipa() {
        return listarPropostasQueOrientadorParticipa;
    }

    public void setListarPropostasQueOrientadorParticipa(List<PropostaTcc> listarPropostasQueOrientadorParticipa) {
        this.listarPropostasQueOrientadorParticipa = listarPropostasQueOrientadorParticipa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
   }
