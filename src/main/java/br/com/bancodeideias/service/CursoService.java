package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.repository.CursoDAO;
import java.io.Serializable;
import java.util.List;

public class CursoService implements Serializable {

    private CursoDAO cursoDAO;

    public CursoService() {
        cursoDAO = new CursoDAO();
    }

    public void salvar(Curso curso) {
        this.getCursoDAO().salvar(curso);
    }

    public void alterar(Curso curso) {
        this.getCursoDAO().alterar(curso);
    }

    public void remover(Curso curso) {
        this.getCursoDAO().remover(curso.getIdCurso());
    }

    /* Lista de todos os cursos */
    public List<Curso> listar() {
        List<Curso> lista;
        lista = this.getCursoDAO().listar();
        return lista;
    }

    /* Listar os cursos da universidade logada  */
    public List<Curso> listarCursoLogado() {
        List<Curso> lista;
        lista = this.getCursoDAO().listarCursoLogado();
        return lista;
    }

    // ============ Gets and sets ===========
    public CursoDAO getCursoDAO() {
        return cursoDAO;
    }

    public void setCursoDAO(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

}
