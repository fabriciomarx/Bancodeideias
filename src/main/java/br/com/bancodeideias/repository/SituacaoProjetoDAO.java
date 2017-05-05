package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.SituacaoProjeto;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class SituacaoProjetoDAO implements Serializable {

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

    /* Listar todos */
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

    /* Listar todos as situações daquela universidade */
    public List<SituacaoProjeto> listarSituaçoesCooord() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<SituacaoProjeto> listaSituacoes = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM SituacaoProjeto u WHERE u.projetoTcc.academico.curso.idCurso = "
                    + usuarioLogado.getCurso().getIdCurso());
            listaSituacoes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe listarSituaçoesCooord DAO " + e.getMessage());
        }
        entityManager.close();
        return listaSituacoes;
    }

}
