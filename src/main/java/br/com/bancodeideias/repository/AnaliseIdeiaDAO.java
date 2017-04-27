package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.AnaliseIdeia;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class AnaliseIdeiaDAO implements Serializable {

    public AnaliseIdeiaDAO() {
    }

    public void salvar(AnaliseIdeia analiseIdeia) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(analiseIdeia);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(AnaliseIdeia analiseIdeia) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(analiseIdeia);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        AnaliseIdeia analiseIdeiaEncontrada = entityManager.find(AnaliseIdeia.class, id);
        entityManager.remove(analiseIdeiaEncontrada);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<AnaliseIdeia> listar() {
        System.out.println("ADM");
        List<AnaliseIdeia> listaAnaliseIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM AnaliseIdeia u");
            listaAnaliseIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe AnaliseIdeia DAO");
        }
        entityManager.close();
        return listaAnaliseIdeia;
        
    }
    
    public List<AnaliseIdeia> listarAnalisesParaUniversidade() {
        System.out.println("Universidade");
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<AnaliseIdeia> listaAnaliseIdeia = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM AnaliseIdeia u WHERE u.academicoAnalista.universidade.idUsuario = "
            + usuarioLogado.getIdUsuario());
            listaAnaliseIdeia = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarAnalisesParaUniversidade - Classe AnaliseIdeia DAO");
        }
        entityManager.close();
        return listaAnaliseIdeia;
        
    }

}
