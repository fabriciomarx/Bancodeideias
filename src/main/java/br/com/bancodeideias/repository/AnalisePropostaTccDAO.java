package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.AnalisePropostatcc;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class AnalisePropostaTccDAO implements Serializable {

    public AnalisePropostaTccDAO() {
    }

    public void salvar(AnalisePropostatcc analisePropostaTcc) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(analisePropostaTcc);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(AnalisePropostatcc analisePropostaTcc) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(analisePropostaTcc);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        AnalisePropostatcc analisePropostaTccEncontrado = entityManager.find(AnalisePropostatcc.class, id);
        entityManager.remove(analisePropostaTccEncontrado);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<AnalisePropostatcc> listar() {
        System.out.println("ADM");
        List<AnalisePropostatcc> listaAnalisePropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM AnalisePropostatcc u");
            listaAnalisePropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe AnalisePropostaTcc DAO");
        }
        entityManager.close();
        return listaAnalisePropostaTcc;
    }
    
    public List<AnalisePropostatcc> listarAnalisesParaUniversidade() {
        System.out.println("Universidade");
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<AnalisePropostatcc> listaAnalisePropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM AnalisePropostatcc u WHERE u.academicoAnalista.universidade.idUsuario = "
            + usuarioLogado.getIdUsuario());
            listaAnalisePropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarAnalisesParaUniversidade - Classe AnalisePropostaTcc DAO");
        }
        entityManager.close();
        return listaAnalisePropostaTcc;
    }

}