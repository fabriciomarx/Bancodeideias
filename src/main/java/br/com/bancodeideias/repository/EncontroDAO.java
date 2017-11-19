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
            Query query = entityManager.createQuery("SELECT e FROM Encontro e");
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listar - EncontroDAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* METODO PARA FILTRAR OS ENCONTROS POR UNIVERSIDADE. USUARIO ADMIN UTILIZA */
    public List<Encontro> listarEncontrosUniversidadeSelecionadaParaAdmin(int id) {
        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.aluno.universidade.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosAcademicoSelecionadoParaAdmin - EncontroDAO");
        }
        entityManager.close();
        return lista;
    }
    
    /* METODO PARA FILTRAR OS ENCONTROS POR ALUNO. USUARIOS UNIVERSIDADE, COORDENADOR E ADMIN UTILIZAM */
    public List<Encontro> listarEncontrosAcademicoSelecionado(int id) {
        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.aluno.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosAcademicoSelecionado - EncontroDAO");
        }
        entityManager.close();
        return lista;
    }

     /* METODO PARA FILTRAR OS ENCONTROS POR PROFESSOR. USUARIOS UNIVERSIDADE, COORDENADOR E ADMIN UTILIZAM */
    public List<Encontro> listarEncontrosProfessorSelecionado(int id) {
        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.orientador.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosProfessorSelecionado - EncontroDAO");
        }
        entityManager.close();
        return lista;
    }

    /* METODO PARA FILTRAR OS ENCONTROS POR ALUNO. USUARIO PROFESSOR UTILIZA  */
    public List<Encontro> listarEncontrosAcademicoSelecionadoParaProfessor(int id) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT e FROM Encontro e WHERE e.aluno.idUsuario = " + id
                    + " AND e.orientador.idUsuario = " + usuarioLogado.getIdUsuario());
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosAcademicoSelecionadoParaProfessor - EncontroDAO");
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
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.aluno.universidade.idUsuario = " + usuarioLogado.getIdUsuario());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosParaUniv - EncontroDAO");
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
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.aluno.curso.idCurso = " + usuarioLogado.getCurso().getIdCurso());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosParaCoord - EncontroDAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* LISTA TODOS OS ENCONTROS DO ALUNO E ORIENTADOR */
    public List<Encontro> listarEncontrosAlu_Prof() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.aluno.idUsuario = " + usuarioLogado.getIdUsuario()
                    + " OR e.orientador.idUsuario = " + usuarioLogado.getIdUsuario());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosAlu_Prof - EncontroDAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* Listar apenas os encontros do aluno logado que já foram realizados 
        metodo utilizado na hora de incluir relatorios */
    public List<Encontro> listarEncontrosRealizadosAluno() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT e FROM Encontro e "
                    + "WHERE e.aluno.idUsuario = " + usuarioLogado.getIdUsuario()
                    + " AND e.status = 'Realizado'");
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarEncontrosRealizadosAluno - EncontroDAO" );
        }
        entityManager.close();
        return listaEncontros;
    }

}
