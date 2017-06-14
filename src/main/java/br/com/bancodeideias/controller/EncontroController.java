package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Encontro;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.EncontroService;
import br.com.bancodeideias.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named(value = "encontroController")
@SessionScoped
public class EncontroController extends GenericController implements Serializable {

    private Usuario             usuario;
    
    private Encontro            encontroSelecionado;
    private EncontroService     encontroService;

    private List<Encontro>      listaEncontro;
    private List<Encontro>      listarEncontroQueCadastrei;
    private List<Encontro>      listarEncontrosAlu_Prof;

    private List<Usuario>       listaAcademico;
    private List<Usuario>       listaProfessores;
    private UsuarioService      usuarioService;

    private ScheduleModel       encontros; //modelo da agenda
    private ScheduleEvent       event;

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
        listarEncontroQueCadastrei  = new ArrayList<>();
        listarEncontrosAlu_Prof     = new ArrayList<>();

        listaAcademico              = new ArrayList<>();
        listaProfessores            = new ArrayList<>();
        usuarioService              = new UsuarioService();

        encontros                   = new DefaultScheduleModel();
        event                       = new DefaultScheduleEvent();

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
                listarEncontroQueCadastrei = this.getEncontroService().listarEncontrosQueCadastrei();
                listarEncontrosAlu_Prof = this.getEncontroService().listarEncontrosAlu_Prof();
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
        listaAcademico = this.getUsuarioService().listaAcademicos();
        listaProfessores = this.getUsuarioService().listaProfessores();
        encontros.addEvent(new DefaultScheduleEvent("Titulo", new Date(), new Date()));
    }
    
    public void listEncontros(){
        listaEncontro = this.getEncontroService().listarEncontrosAcademicoSelecionado(usuario.getIdUsuario());
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            encontros.addEvent(event);
        } else {
            encontros.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent evento) {
        event = (ScheduleEvent) evento.getObject();
    }

    public void novo(SelectEvent evento) {
        encontroSelecionado.setDataEncontro((Date) evento.getObject());
    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  

        /*Arrumando o erro que salvava uma data antes*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(encontroSelecionado.getDataEncontro());
        calendar.add(Calendar.DATE, 1); //somando um dia na data
        encontroSelecionado.setDataEncontro(calendar.getTime()); //data do encontro recebendo a nova data

        try {
            if (usuarioLogado.getTipoUsuario().equals("Admin")) { //SE O USUARIO FOR ADMIN NAO SALVAR O ACADEMICO AUTOMATICO
                this.getEncontroService().salvar(encontroSelecionado);
                this.getEncontroSelecionado().setStatus("Ainda não visualizado");
            } else {
                this.getEncontroSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getEncontroSelecionado().setStatus("Ainda não visualizado");
                this.getEncontroService().salvar(encontroSelecionado);
            }
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

    public List<Encontro> getListarEncontroQueCadastrei() {
        return listarEncontroQueCadastrei;
    }

    public void setListarEncontroQueCadastrei(List<Encontro> listarEncontroQueCadastrei) {
        this.listarEncontroQueCadastrei = listarEncontroQueCadastrei;
    }

    public List<Encontro> getListarEncontrosAlu_Prof() {
        return listarEncontrosAlu_Prof;
    }

    public void setListarEncontrosAlu_Prof(List<Encontro> listarEncontrosAlu_Prof) {
        this.listarEncontrosAlu_Prof = listarEncontrosAlu_Prof;
    }

    public ScheduleModel getEncontros() {
        return encontros;
    }

    public void setEncontros(ScheduleModel encontros) {
        this.encontros = encontros;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public List<Usuario> getListaAcademico() {
        return listaAcademico;
    }

    public void setListaAcademico(List<Usuario> listaAcademico) {
        this.listaAcademico = listaAcademico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

}
