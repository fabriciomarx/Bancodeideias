package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class CursoDAO implements Serializable {

    public CursoDAO() {
    }

    public String msg;

    public void salvar(Curso curso) {
        EntityManager entityManager = JPAConnection.getEntityManager();

        List<Curso> listaCurso = new ArrayList<>();
        Query query = entityManager.createQuery("SELECT u FROM Curso u where u.nome =  '" + curso.getNome() + "'");
        listaCurso = query.getResultList();
        if (listaCurso.isEmpty()) {
            entityManager.getTransaction().begin();
            entityManager.persist(curso);
            entityManager.getTransaction().commit();
            entityManager.close();
            this.setMsg("Curso salvo com sucesso");
        } else {
            entityManager.close();
            System.out.println("Erro ao incluir, já existe um curso com o mesmo nome"); //provisorio
            this.setMsg("Já existe um curso com o mesmo nome");
        }
    }

    public void alterar(Curso curso) {
        EntityManager entityManager = JPAConnection.getEntityManager();

        List<Curso> listaCurso = new ArrayList<>();
        Query query = entityManager.createQuery(
                "SELECT u FROM Curso u where u.nome =  '" + curso.getNome() + "'");
        listaCurso = query.getResultList();
        if (listaCurso.isEmpty()) {
            entityManager.getTransaction().begin();
            entityManager.merge(curso);
            entityManager.getTransaction().commit();
            entityManager.close();
            this.setMsg("Curso alterado com sucesso");
        } else {
            entityManager.close();
            System.out.println("Erro ao alterar, já existe um curso com o mesmo nome"); //provisorio
            this.setMsg("Já existe um curso com o mesmo nome e/ou sigla");
        }
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Curso cursoEncontrado = entityManager.find(Curso.class, id);
        entityManager.remove(cursoEncontrado);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /* LISTAR TODOS OS CURSOS CADASTRADOS NO SISTEMA - USUARIO ADM */
    public List<Curso> listar() {
        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM Curso c");
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listar - CursoDAO");
        }
        entityManager.close();
        return listaCurso;
    }

    /* LISTAR SOMENTE OS CURSOS DA UNIVERSIDADE LOGADA - USUARIO UNIVERSIDADE */
    public List<Curso> listarCursoLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM Curso c "
                    + "WHERE c.universidade.idUsuario = " + usuarioLogado.getIdUsuario());
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarCursoLogado - CursoDAO");
        }
        entityManager.close();
        return listaCurso;
    }

    /* LISTAR OS CURSOS DA UNIVERSIDADE ESCOLHIDA (metodo para cadastro de usuario) */
    public List<Curso> listarCursosUniversidadeEscolhida(int idUniversidade) {
        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT c FROM Curso c "
                    + "WHERE c.universidade.idUsuario = " + idUniversidade);
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarCursoUniversidadeEscolhida - CursoDAO");
        }
        entityManager.close();
        return listaCurso;
    }

    /* Listar apenas os cursos da universidade escolhida - Usuario ADMIN */
    public List<Curso> listarCursosUniversidadeSelecionada(int id) {
        List<Curso> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM Curso c "
                    + "WHERE c.universidade.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarCursosUniversidadeSelecionada - CursoDAO");
        }
        entityManager.close();
        return lista;
    }

    /* ============ GETS AND SETS =========== */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
