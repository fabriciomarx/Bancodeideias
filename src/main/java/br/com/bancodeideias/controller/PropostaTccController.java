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

    private Usuario             usuario;
    
    private PropostaTcc         propostaTccSelecionada;
    private PropostaTccService  propostaTccService;
    
    private List<PropostaTcc>   listaPropostaTcc;
    private List<PropostaTcc>   listaPropostasPendentes;
    private List<PropostaTcc>   listaPropostasPendentesDaUniv; 
   
    private List<PropostaTcc>   listaProjetos;
    private List<PropostaTcc>   listaPropostasParaCoord;
    private List<PropostaTcc>   listaPropostasParaOrientador;
    private List<PropostaTcc>   listaPropostasPendentesDaUnivParaCoordenador ;
    
    private List<Ideia>         listaIdeias;
    private IdeiaService        ideiaService;

    private List<Usuario>       listaProfessores;
    private List<Usuario>       listaAcademicos;
    private UsuarioService      usuarioService;
    
    //String tipoUsuario;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
    }

    private void resset() {
        usuario                         = new Usuario();
        propostaTccSelecionada          = new PropostaTcc();
        propostaTccService              = new PropostaTccService();
        listaPropostaTcc                = new ArrayList<>();
        listaPropostasPendentes         = new ArrayList<>();
        listaPropostasPendentesDaUniv   = new ArrayList<>();
        
        listaProjetos                   = new ArrayList<>();
        listaPropostasParaCoord         = new ArrayList<>();
        listaPropostasParaOrientador    = new ArrayList<>();
        listaPropostasPendentesDaUnivParaCoordenador  = new ArrayList<>();
        
        listaIdeias                     = new ArrayList<>();
        ideiaService                    = new IdeiaService();

        listaProfessores                = new ArrayList<>();
        listaAcademicos                 = new ArrayList<>();
        usuarioService                  = new UsuarioService();
    }
    
    public void listaPropostas() {
        listaPropostasParaOrientador = this.getPropostaTccService().listarPropostasAlunoSelecionado(usuario.getIdUsuario());
    }

    private void listar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        switch (usuarioLogado.getTipoUsuario()) {
            case "Aluno":
                listaPropostaTcc = this.getPropostaTccService().listarPropostasLogado();
                break;
            case "Coordenador":
                listaPropostasParaCoord = this.getPropostaTccService().listarPropostasParaCoord();
                listaPropostasPendentesDaUnivParaCoordenador = this.getPropostaTccService().listaPropostasPendentesDaUnivParaCoordenador();
                break;
            case "Admin":
                listaPropostaTcc = this.getPropostaTccService().listar();
                break;
            case "Universidade":
           
                listaPropostasPendentesDaUniv = this.getPropostaTccService().listaPropostasPendentesDaUniv();
                listaProjetos = this.getPropostaTccService().listarProjetos();
                listaPropostaTcc = this.getPropostaTccService().listaPropostasDaUniv();
             case "Professor":
                listaPropostasParaOrientador = this.getPropostaTccService().listarPropostasParaOrientador();
                break;
            default:
                break;
        }
        listaIdeias = this.getIdeiaService().listarIdeiasAprovadas()    ;
        listaProfessores = this.getUsuarioService().listaProfessores();
        listaAcademicos = this.getUsuarioService().listaAcademicos();

       // listaPropostasParaOrientador    = this.getPropostaTccService().list();
    }

    public String salvar() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            //SE O USUARIO FOR ALUNO QUANDO ELE INSERIR UMA PROPOSTA A SITUAÇÃO É PENDENTE COMO DEFAUT
            switch (usuarioLogado.getTipoUsuario()) {
                case "Aluno":
                    this.getPropostaTccSelecionada().setSituacao("Em analise"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
                    this.getPropostaTccSelecionada().setAprovacaoOrientador("Em analise"); //INSERINDO A SITUAÇÃO PENDENTE COMO DEFAUT
                    this.getPropostaTccSelecionada().setDataInscricao(new Date()); //SALVANDO A DATA ATUAL AUTOMATICO
                    this.getPropostaTccSelecionada().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                    this.getPropostaTccService().salvar(propostaTccSelecionada);
                    break;
                case "Admin":
                    //SE O USUARIO FOR ADMIN NAO SALVAR O ACADEMICO AUTOMATICO
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

    /*Metodo para aceitar proposta de tcc, serve para o usuario professor*/
    public String aceitar() {
        try {
            this.getPropostaTccSelecionada().setAprovacaoOrientador("Aprovado");
            this.getPropostaTccService().alterar(propostaTccSelecionada);
            addSucessMessage("Proposta Tcc aceita com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao aceitar proposta Tcc: " + propostaTccSelecionada.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    /*Metodo para recusar proposta de tcc, serve para o usuario professor*/
    public String recusar() {
        try {
            this.getPropostaTccSelecionada().setAprovacaoOrientador("Recusado");
            this.getPropostaTccService().alterar(propostaTccSelecionada);
            addSucessMessage("Proposta Tcc recusada com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao recusar proposta Tcc: " + propostaTccSelecionada.toString());
        }
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

    public List<PropostaTcc> getListaProjetos() {
        return listaProjetos;
    }

    public void setListaProjetos(List<PropostaTcc> listaProjetos) {
        this.listaProjetos = listaProjetos;
    }

    public List<PropostaTcc> getListaPropostasParaCoord() {
        return listaPropostasParaCoord;
    }

    public void setListaPropostasParaCoord(List<PropostaTcc> listaPropostasParaCoord) {
        this.listaPropostasParaCoord = listaPropostasParaCoord;
    }

    public List<PropostaTcc> getListaPropostasParaOrientador() {
        return listaPropostasParaOrientador;
    }

    public void setListaPropostasParaOrientador(List<PropostaTcc> listaPropostasParaOrientador) {
        this.listaPropostasParaOrientador = listaPropostasParaOrientador;
    }

    public List<PropostaTcc> getListaPropostasPendentesDaUniv() {
        return listaPropostasPendentesDaUniv;
    }

    public void setListaPropostasPendentesDaUniv(List<PropostaTcc> listaPropostasPendentesDaUniv) {
        this.listaPropostasPendentesDaUniv = listaPropostasPendentesDaUniv;
    }

    public List<Usuario> getListaAcademicos() {
        return listaAcademicos;
    }

    public void setListaAcademicos(List<Usuario> listaAcademicos) {
        this.listaAcademicos = listaAcademicos;
    }

    public List<PropostaTcc> getListaPropostasPendentesDaUnivParaCoordenador() {
        return listaPropostasPendentesDaUnivParaCoordenador;
    }

    public void setListaPropostasPendentesDaUnivParaCoordenador(List<PropostaTcc> listaPropostasPendentesDaUnivParaCoordenador) {
        this.listaPropostasPendentesDaUnivParaCoordenador = listaPropostasPendentesDaUnivParaCoordenador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
   }
