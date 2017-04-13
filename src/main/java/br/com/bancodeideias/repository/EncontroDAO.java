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

    /* Listar todos os encontros do sistema */
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
    
    /* Listar os encontros que a pessoa cadastrou 
        nesse caso a pessoa que é academico*/
    public List<Encontro> listarEncontrosQueCadastrei() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u where u.academico.idUsuario = " + usuarioLogado.getIdUsuario());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Encontro DAO");
        }
        entityManager.close();
        return listaEncontros;
    }

    /* Listar os encontros que a pessoa foi chamada/selecionada 
        nesse caso a pessoa é participante */
    public List<Encontro> listarEncontrosQueFuiChamado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Encontro> listaEncontros = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Encontro u where u.participante.idUsuario = " + usuarioLogado.getIdUsuario());
            listaEncontros = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Encontro DAO");
        }
        entityManager.close();
        return listaEncontros;
    }

}
