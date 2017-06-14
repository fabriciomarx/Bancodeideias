package br.com.bancodeideias.controller;

import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.service.CursoService;
import br.com.bancodeideias.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@Named(value = "cursoController")
@SessionScoped
public class CursoController extends GenericController implements Serializable {

    private Curso           cursoSelecionado;
    private CursoService    cursoService;

    private List<Curso>     listaCurso;

    private List<Usuario>   listaUniversidade;
    private UsuarioService  usuarioService;
    
    private LineChartModel areaModel;

    @PostConstruct
    public void preRenderPage() {
        this.resset();
        this.listar();
        this.createAreaModel();
    }
    
    private void createAreaModel() {
        areaModel = new LineChartModel();
        
        LineChartSeries boys = new LineChartSeries();
        boys.setFill(true);
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
        
        LineChartSeries girls = new LineChartSeries();
        girls.setFill(true);
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);
        
        areaModel.addSeries(boys);
        areaModel.addSeries(girls);
        
        areaModel.setTitle("Area Chart");
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);

        Axis xAxis = new CategoryAxis("Years");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        
        //int qtdeMax = this.getCursoService().quantidadeCurso();
        yAxis.setMin(0);
        yAxis.setMax(300);

    }
    

    private void resset() {
        cursoSelecionado                  = new Curso();
        cursoService                      = new CursoService();
        listaCurso                        = new ArrayList<>();
        
        listaUniversidade                 = new ArrayList<>();
        usuarioService                    = new UsuarioService();
    }

   
    private void listar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    

        switch (usuarioLogado.getTipoUsuario()) {
            case "Universidade":
                /* SE O USUARIO LOGADO FOR UNIVERSIDADE CARREGAR O METODO LISTAR LOGADO*/
                listaCurso = this.getCursoService().listarCursoLogado();
                break;
            case "Admin":
                /* SE O USUARIO LOGADO FOR ADMIN CARREGAR O METODO LISTAR TODOS OS CURSOS*/
                listaCurso = this.getCursoService().listar();
                listaUniversidade = this.getUsuarioService().listUniversidades();
                break;
        }

    }

    public String salvar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO SALVO NA SESSÃO    
        try {
            switch (usuarioLogado.getTipoUsuario()) { //SE O USUARIO FOR ADMIN NAO SALVAR A UNIVERSIDADE AUTOMATICO
                case "Admin":
                    this.getCursoService().salvar(cursoSelecionado);
                    break;
                case "Universidade":
                    this.getCursoSelecionado().setUniversidade(usuarioLogado); //INSERINDO A UNIVERSIDADE AUTOMATICO
                    this.getCursoService().salvar(cursoSelecionado);
                    break;
            }
            addSucessMessage("Curso salvo com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao salvar curso: " + cursoSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String alterar() {
        try {
            this.getCursoService().alterar(cursoSelecionado);
            addSucessMessage("Curso alterado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao alterar curso: " + cursoSelecionado.toString());
        }
        this.resset();
        this.listar();
        return "listar.xhtml?faces-redirect=true";
    }

    public String remover() {
        try {
            this.getCursoService().remover(cursoSelecionado);
            addSucessMessage("Curso deletado com sucesso");
        } catch (Exception e) {
            addErrorMessage("Erro ao deletar curso: " + cursoSelecionado.toString());
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
    public Curso getCursoSelecionado() {
        return cursoSelecionado;
    }

    public void setCursoSelecionado(Curso cursoSelecionado) {
        this.cursoSelecionado = cursoSelecionado;
    }

    public CursoService getCursoService() {
        return cursoService;
    }

    public void setCursoService(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    public List<Curso> getListaCurso() {
        return listaCurso;
    }

    public void setListaCurso(List<Curso> listaCurso) {
        this.listaCurso = listaCurso;
    }

    public List<Usuario> getListaUniversidade() {
        return listaUniversidade;
    }

    public void setListaUniversidade(List<Usuario> listaUniversidade) {
        this.listaUniversidade = listaUniversidade;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public LineChartModel getAreaModel() {
        return areaModel;
    }

    public void setAreaModel(LineChartModel areaModel) {
        this.areaModel = areaModel;
    }
    
    
}
