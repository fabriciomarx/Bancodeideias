package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.Relatorio;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class RelatorioDAO implements Serializable {

    public RelatorioDAO() {
    }

    public void salvar(Relatorio relatorio) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(relatorio);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(Relatorio relatorio) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(relatorio);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Relatorio relatorioQuinzenalEncontrado = entityManager.find(Relatorio.class, id);
        entityManager.remove(relatorioQuinzenalEncontrado);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /* Lista todos os relatorios cadastrados*/
    public List<Relatorio> listar() {
        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u");
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }

    /* Lista os relatorios do aluno logado */
    public List<Relatorio> listaRelatorioLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.academico.idUsuario = " + usuarioLogado.getIdUsuario());
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatorioLogado - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }
    
    /* Lista os relatorios dos alunos da universidade logada */
    public List<Relatorio> listaRelatoriosUniLogada() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.academico.universidade.idUsuario = " + usuarioLogado.getIdUsuario());
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatoriosUniLogada - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }
   
    /* Lista de relatorios dos alunos do curso que o coordenador coordena */
    public List<Relatorio> listaRelatoriosCoordLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.academico.curso.idCurso = " + usuarioLogado.getCurso().getIdCurso());
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatoriosCoordLogado - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }
    
    //falta fazer para o orientador*/
}
