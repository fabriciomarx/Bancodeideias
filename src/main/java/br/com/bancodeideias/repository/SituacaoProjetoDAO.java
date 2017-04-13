package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.SituacaoProjeto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SituacaoProjetoDAO implements Serializable{

    public SituacaoProjetoDAO() {
    }

    public void salvar(SituacaoProjeto situacaoProjeto) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(situacaoProjeto);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(SituacaoProjeto situacaoProjeto) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(situacaoProjeto);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        SituacaoProjeto situacaoEncontrada = entityManager.find(SituacaoProjeto.class, id);
        entityManager.remove(situacaoEncontrada);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<SituacaoProjeto> listar() {
        List<SituacaoProjeto> listaSituacoes = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM SituacaoProjeto u");
            listaSituacoes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe SituacaoProjeto DAO " + e.getMessage());
        }
        entityManager.close();
        return listaSituacoes;
    }
}
