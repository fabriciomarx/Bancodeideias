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

    public String mensagem;

    public CursoDAO() {

    }

    public void salvar(Curso curso) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();

        /* Faz um select banco antes de inserir, é verificado se a sigla ja existe naquela universidade */
        Query query = entityManager
                .createQuery("Select u From Curso u where u.universidade.idUsuario = :usuarioLogdo and u.sigla = :siglaCurso")
                .setParameter("siglaCurso", curso.getSigla())
                .setParameter("usuarioLogdo", usuarioLogado.getIdUsuario());

        if (query.getResultList().isEmpty()) {
            entityManager.persist(curso);
            this.setMensagem("Curso salvo com sucesso !");
        } else {
            this.setMensagem("A sigla já esta cadastrada!");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(Curso curso) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(curso);
        entityManager.getTransaction().commit();
        entityManager.close();
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

    /* LISTAR OS CURSOS DA UNIVERSIDADE ESCOLHIDA - USUARIO ADMIN UTILIZA (FILTRO DOS CURSOS) */
    public List<Curso> listarCursosUniversidadeEscolhida(int idUniversidade) {
        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT c FROM Curso c WHERE c.universidade.idUsuario = " + idUniversidade);
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarCursoUniversidadeEscolhida - CursoDAO");
        }
        entityManager.close();
        return listaCurso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
