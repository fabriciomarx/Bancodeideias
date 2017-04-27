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

    private Encontro                encontroSelecionado;
    private EncontroService         encontroService;
    private List<Encontro>          listaEncontro; // todos encontros
    private List<Encontro>          listarEncontroQueCadastrei;
    private List<Encontro>          listarEncontroQueFuiChamado;
    private List<Encontro>          listarEncontrosParaCoord;

    private List<Usuario>           listaAcademico;
    private List<Usuario>           listaProfessores;
    private UsuarioService          usuarioService;
    
    private ScheduleModel           encontros; //modelo da agenda
    private ScheduleEvent           event;
    
  
    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
        
    }

    private void resset() {
        encontroSelecionado         = new Encontro();
        encontroService             = new EncontroService();
        listaEncontro               = new ArrayList<>();
        listarEncontroQueCadastrei  = new ArrayList<>();
        listarEncontroQueFuiChamado = new ArrayList<>();
        listarEncontrosParaCoord    = new ArrayList<>();
        
        listaAcademico              = new ArrayList<>();
        listaProfessores            = new ArrayList<>();
        usuarioService              = new UsuarioService();
        
        encontros                   = new DefaultScheduleModel();
        event                       = new DefaultScheduleEvent();
       
        
    }
    
    
    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO  
        
        if (usuarioLogado.getTipoUsuario().equals("Admin")) {
            listaEncontro = this.getEncontroService().listar();
            listaAcademico = this.getUsuarioService().listaAcademicos();
            
        } else if(usuarioLogado.getTipoUsuario().equals("Aluno") || usuarioLogado.getTipoUsuario().equals("Professor")){
            listarEncontroQueCadastrei = this.getEncontroService().listarEncontrosQueCadastrei();
            listarEncontroQueFuiChamado = this.getEncontroService().listarEncontrosQueFuiChamado();
        }else if(usuarioLogado.getTipoUsuario().equals("Coordenador") )
            listarEncontrosParaCoord = this.getEncontroService().listarEncontrosParaCoord();
        
            listaProfessores = this.getUsuarioService().listaProfessores();
            
        
        
        encontros.addEvent(new DefaultScheduleEvent("Titulo", new Date(),new Date()));
        
    }
    
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
            encontros.addEvent(event);
        else
            encontros.updateEvent(event);
        
        event = new DefaultScheduleEvent();
    }
    
    public void onEventSelect(SelectEvent evento) {
        event = (ScheduleEvent) evento.getObject();
    }
    
    public void novo(SelectEvent evento){
        encontroSelecionado.setDataEncontro((Date)evento.getObject());
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
            if(usuarioLogado.getTipoUsuario().equals("Admin")){ //SE O USUARIO FOR ADMIN NAO SALVAR O ACADEMICO AUTOMATICO
                this.getEncontroService().salvar(encontroSelecionado);
               
            }else{
                this.getEncontroSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
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
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            if(usuarioLogado.getTipoUsuario().equals("Admin")){ //SE O USUARIO FOR ADMIN NAO SALVAR A UNIVERSIDADE AUTOMATICO
                this.getEncontroService().alterar(encontroSelecionado);
            }else{
                this.getEncontroSelecionado().setAcademico(usuarioLogado); //INSERINDO O ACADEMICO AUTOMATICO
                this.getEncontroService().alterar(encontroSelecionado);
            }    
           
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

    public List<Encontro> getListarEncontroQueFuiChamado() {
        return listarEncontroQueFuiChamado;
    }

    public void setListarEncontroQueFuiChamado(List<Encontro> listarEncontroQueFuiChamado) {
        this.listarEncontroQueFuiChamado = listarEncontroQueFuiChamado;
    }

    public List<Encontro> getListarEncontrosParaCoord() {
        return listarEncontrosParaCoord;
    }

    public void setListarEncontrosParaCoord(List<Encontro> listarEncontrosParaCoord) {
        this.listarEncontrosParaCoord = listarEncontrosParaCoord;
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
    
    

    
    
    
    
    
}
