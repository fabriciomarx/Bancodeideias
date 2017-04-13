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
    
    /* LISTAR SOMENTE AS IDEIAS DO USUARIO LOGADO */
    public List<Ideia> listarIdeiasLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Ideia> listaIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u where u.usuario.idUsuario = " + usuarioLogado.getIdUsuario() + "");
            listaIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar ideias logado - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeia;
    }

    public List<Ideia> listarPendentes() {
        List<Ideia> listaIdeiaPendentes = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Ideia u where u.situacao = 'P'");
            listaIdeiaPendentes = query.getResultList();
            System.out.println("Passou ");
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPendentes - Classe Ideia DAO");
        }
        entityManager.close();
        return listaIdeiaPendentes;
    }
}
