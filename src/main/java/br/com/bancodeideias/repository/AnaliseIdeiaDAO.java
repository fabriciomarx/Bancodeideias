package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.AnaliseIdeia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

}
