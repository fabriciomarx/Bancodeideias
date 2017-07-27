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

    public void salvar(Curso curso) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(curso);
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

    /* LISTAR TODOS OS CURSOS CADASTRADOS NO SISTEMA - USUARIO ADM QUE UTILIZA */
    public List<Curso> listar() {
        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Curso u");
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Curso DAO");
        }
        entityManager.close();
        return listaCurso;
    }

    /* LISTAR SOMENTE OS CURSOS DA UNIVERSIDADE LOGADA */
    public List<Curso> listarCursoLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Curso u "
                    + "WHERE u.universidade.idUsuario = " + usuarioLogado.getIdUsuario());
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarCursoLogado - Classe Curso DAO");
        }
        entityManager.close();
        return listaCurso;
    }

    /* LISTAR OS CURSOS DA UNIVERSIDADE ESCOLHIDA (metodo para cadastro de usuario)*/
    public List<Curso> listarCursosUniversidadeEscolhida(int idUniversidade) {
        List<Curso> listaCurso = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Curso u "
                    + "WHERE u.universidade.idUsuario = " + idUniversidade);
            listaCurso = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarCursoUniversidadeEscolhida - Classe Curso DAO");
        }
        entityManager.close();
        return listaCurso;
    }
    
    public int quantidadeAlunoPorCurso() {
        int qtdeAlu = 0;
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(*) FROM Usuario u where u.tipoUsuario = 'Aluno'");
            qtdeAlu = Integer.parseInt(query.getSingleResult().toString());
            System.out.println("Qtd Aluno = " + qtdeAlu);

        } catch (Exception e) {
            System.out.println("Erro no metodo quantidadeAluno - Classe Curso DAO");
        }
        entityManager.close();
        return qtdeAlu;
    }
    
    /*
    public boolean validarDados(String nome) {
        
    }*/
    
    
}
