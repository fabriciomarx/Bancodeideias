package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.Encontro;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class EncontroDAO implements Serializable {

    public EncontroDAO() {
    }

    public void salvar(Encontro encontro) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(encontro);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(Encontro encontro) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(encontro);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Encontro encontrosEncontrado = entityManager.find(Encontro.class, id);
        entityManager.remove(encontrosEncontrado);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /* LISTA TODOS OS ENCONTROS CADASTRADOS NO SISTEMA */
    public List<Encontro> listar() {
        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u");
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Encontro DAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* LISTA DE ENCONTROS FILTRADOS POR ACADEMICO (aluno) Metodo que a universidade usa */
    public List<Encontro> listarEncontrosAcademicoSelecionado(int id) {
        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u "
                    + "WHERE u.aluno.idUsuario = "
                    + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarEncontrosAcademicoSelecionado - Classe EncontroDAO");
        }
        entityManager.close();
        return lista;
    }
    
    

    /* LISTA DE ENCONTROS FILTRADOS POR ACADEMICO (professor) Metodo que a universidade usa */
    public List<Encontro> listarEncontrosProfessorSelecionado(int id) {
        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u "
                    + "WHERE u.orientador.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarEncontrosProfessorSelecionado - Classe EncontroDAO");
        }
        entityManager.close();
        return lista;
    }
    
    /* LISTA DE ENCONTROS FILTRADOS POR ACADEMICO (aluno) Metodo que a professor usa */
    public List<Encontro> listarEncontrosAcademicoSelecionadoParaProfessor(int id) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Encontro u WHERE u.aluno.idUsuario = " + id
                    + " AND u.orientador.idUsuario = " + usuarioLogado.getIdUsuario());
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarEncontrosAcademicoSelecionadoParaProfessor - Classe EncontroDAO");
        }
        entityManager.close();
        return lista;
    }

    /* LISTA TODOS OS ENCONTROS DE ALUNOS DA UNIVERSIDADE LOGADA */
    public List<Encontro> listarEncontrosParaUniv() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u "
                    + "WHERE u.aluno.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarEncontrosParaUniv - Classe Encontro DAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* LISTA DE ENCONTROS DE ALUNOS QUE SEJAM DO CURSO QUE O COORDENADOR GERENCIA */
    public List<Encontro> listarEncontrosParaCoord() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u "
                    + "WHERE u.aluno.curso.idCurso = "
                    + usuarioLogado.getCurso().getIdCurso());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarEncontrosParaCoord - Classe Encontro DAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* LISTA TODOS OS ENCONTROS QUE A PESSOA CADASTROU OU FOI CHAMADA PARA PARTICIPAR */
    public List<Encontro> listarEncontrosAlu_Prof() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u "
                    + "WHERE u.aluno.idUsuario = " + usuarioLogado.getIdUsuario()
                    + " OR u.orientador.idUsuario = " + usuarioLogado.getIdUsuario());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarEncontrosAlu_Prof - Classe Encontro DAO");
        }
        entityManager.close();
        return listaEncontros;
    }

}
