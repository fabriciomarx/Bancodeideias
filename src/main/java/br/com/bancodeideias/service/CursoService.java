package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.domain.Encontro;
import br.com.bancodeideias.repository.CursoDAO;
import java.io.Serializable;
import java.util.List;

public class CursoService implements Serializable {

    private CursoDAO cursoDAO;
    private String msgService;

    public CursoService() {
        cursoDAO = new CursoDAO();
    }

    public void salvar(Curso curso) {
        this.setMsgService(this.getCursoDAO().getMsg());
        this.getCursoDAO().salvar(curso);
    }

    public void alterar(Curso curso) {
        this.setMsgService(this.getCursoDAO().getMsg());
       // this.getCursoDAO().alterar(curso);
    }

    public void remover(Curso curso) {
        this.getCursoDAO().remover(curso.getIdCurso());
    }

    /* LISTAR TODOS OS CURSOS CADASTRADOS NO SISTEMA */
    public List<Curso> listar() {
        List<Curso> lista;
        lista = this.getCursoDAO().listar();
        return lista;
    }

    /* LISTAR SOMENTE OS CURSOS DA UNIVERSIDADE LOGADA */
    public List<Curso> listarCursoLogado() {
        List<Curso> lista;
        lista = this.getCursoDAO().listarCursoLogado();
        return lista;
    }

    /* LISTAR OS CURSOS DA UNIVERSIDADE ESCOLHIDA (na hora do cadastro de usuario)*/
    public List<Curso> listarCursosUniversidadeEscolhida(int id) {
        List<Curso> lista;
        lista = this.getCursoDAO().listarCursosUniversidadeEscolhida(id);
        return lista;
    }
    
    /* Listar apenas os cursos da universidade escolhida - Usuario ADMIN */
    public List<Curso> listarCursosUniversidadeSelecionada(int id) {
        List<Curso> lista;
        lista = this.getCursoDAO().listarCursosUniversidadeSelecionada(id);
        return lista;
    }
    
    /* ============ GETS AND SETS =========== */
    public CursoDAO getCursoDAO() {
        return cursoDAO;
    }

    public void setCursoDAO(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    public String getMsgService() {
        return msgService;
    }

    public void setMsgService(String msgService) {
        this.msgService = msgService;
    }

}
