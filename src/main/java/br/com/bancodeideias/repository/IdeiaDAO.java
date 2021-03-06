package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.Ideia;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class IdeiaDAO implements Serializable {

    public IdeiaDAO() {
    }

    public void salvar(Ideia ideia) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(ideia);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(Ideia ideia) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(ideia);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Ideia ideiaEncontrada = entityManager.find(Ideia.class, id);
        entityManager.remove(ideiaEncontrada);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /* LISTA DE ideias filtrados por tipo Metodo que a universidade e admin utilizam */
    public List<Ideia> listarTiposIdeiaSelecionado(String tipo) {
        List<Ideia> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u "
                    + "WHERE u.tipoIdeia like " + tipo);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarTiposIdeiaSelecionado - Classe IdeiaDAO");
        }
        entityManager.close();
        return lista;
    }

    /* LISTA DE ideias filtrados por universidade, usuario admin que usa */
    public List<Ideia> listarIdeiasUniversidadeSelecionada(Integer idUniversidade) {
        List<Ideia> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u "
                    + "WHERE u.usuario.universidade.idUsuario = " + idUniversidade);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarIdeiasUniversidadeSelecionada - Classe IdeiaDAO");
        }
        entityManager.close();
        return lista;
    }

    /* LISTAR TODAS AS IDEIAS CADASTRADAS NO SISTEMA*/
    public List<Ideia> listar() {
        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u");
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Ideia DAO");
        }

        entityManager.close();
        return listaIdeia;
    }

    /* LISTAR TODAS AS IDEIAS APROVADAS NO SISTEMA e que estejam disponiveis para utilização */
    public List<Ideia> listarIdeiasAprovadas() {
        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u where u.situacao = 'Aprovado'"
                    + " AND u.disponibilidade = 'Disponível'");
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarIdeiasAprovadas - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    /* LISTAR TODAS AS IDEIAS RECUSADAS NO SISTEMA
    public List<Ideia> listarIdeiasRecusadas() {
        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u where u.situacao = 'Em analise'");
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarIdeiasRecusadas - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }*/

 /* LISTA DE IDEIAS CADASTRADAS POR ALUNOS PERTENCENTES A UNIVERSIDADE LOGADA */
    public List<Ideia> listarIdeiasdaUniversidade() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Ideia u WHERE u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario() + " OR u.usuario.idUsuario = " + usuarioLogado.getIdUsuario());
            /* OR serve para mostrar as ideias que a universidade sugeriu tbm */
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarIdeiasdaUniversidade - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    /*Metodo que universidade utiliza para verificar a lista de analise das ideias dos alunos */
    public List<Ideia> listarAnaliseIdeiasParaUniversidade() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Ideia u WHERE u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario() + " AND u.usuario.tipoUsuario = 'Aluno'"
                    + " AND u.situacao = 'Aprovado'");
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarAnaliseIdeiasParaUniversidade - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    /* LISTA DE IDEIAS PENDENTES DOS ALUNOS PERTENCENTES A UNIVERSIDADE LOGADA */
    public List<Ideia> listarIdeiasPendentesdaUniversidade() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Ideia u WHERE u.situacao = 'Em análise' AND u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarIdeiasPendentesdaUniversidade - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    /* LISTAR SOMENTE AS IDEIAS CADASTRADAS DO USUARIO LOGADO */
    public List<Ideia> listarIdeiasLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Ideia u where u.usuario.idUsuario = " + usuarioLogado.getIdUsuario());
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo  listarIdeiasLogado - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    /* LISTAR IDEIAS PENDENTES DA UNIVERSIDADE - METODO QUE SERVE PARA PROFESSOR E COORDENADOR*/
    public List<Ideia> listarIdeiasPendentes() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeiaPendentes = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Ideia u where u.situacao = 'Em análise' AND u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getUniversidade().getIdUsuario());
            listaIdeiaPendentes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPendentes - Classe Ideia DAO " + e.getMessage());
        }
        entityManager.close();
        return listaIdeiaPendentes;
    }
}
