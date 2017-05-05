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

    /* LISTAR TODAS AS IDEIAS */
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
    
    /* Listar ideias dos alunos da universidade */
    public List<Ideia> listarIdeiasdaUniversidade() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u WHERE u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarIdeiasdaUniversidade - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }
    
     /* Listar ideias pendentes da universidade logada */
    public List<Ideia> listarIdeiasPendentesdaUniversidade() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u WHERE u.situacao = 'P' AND u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarIdeiasPendentesdaUniversidade - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }
    
    /* LISTAR SOMENTE AS IDEIAS DO USUARIO LOGADO */
    public List<Ideia> listarIdeiasLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u where u.usuario.idUsuario = " + usuarioLogado.getIdUsuario());
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo  listarIdeiasLogado - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    /* ideias pendentes */
    public List<Ideia> listarPendentes() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeiaPendentes = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u where u.situacao = 'P' AND u.usuario.universidade.idUsuario = "
                    + usuarioLogado.getUniversidade().getIdUsuario());
            listaIdeiaPendentes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPendentes - Classe Ideia DAO " + e.getMessage());
        }
        entityManager.close();
        return listaIdeiaPendentes;
    }
}
