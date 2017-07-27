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

    /* Filtrar relatorios do aluno selecionado que o orientador seja o usuario logado
        utilizo esse metodo na pagina do orientador - relatorios */
    public List<Relatorio> listarRelatorioAlunoSelecionado(int id) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Relatorio u WHERE u.encontro.aluno.idUsuario = "
                    + id + " AND u.encontro.orientador = " + usuarioLogado.getIdUsuario());
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarRelatorioAlunoSelecionado - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return lista;
    }

    /* Filtrar relatorios do aluno selecionado que o coordenador */
    public List<Relatorio> listarRelatorioAlunoSelecionadoParaCoord(int id) {
        //HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(
                    "SELECT u FROM Relatorio u WHERE u.encontro.aluno.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarRelatorioAlunoSelecionadoParaCoord - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return lista;
    }

    /* LISTAR TODOS OS RELATORIOS DO SISTEMA */
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

    /* LISTAR OS RELATORIOS DO ALUNO LOGADO */
    public List<Relatorio> listaRelatorioLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.encontro.aluno.idUsuario = " + usuarioLogado.getIdUsuario());
            listaRelatorios = query.getResultList();
            System.out.println("Numero = " + listaRelatorios.size());
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatorioLogado - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }

    /* LISTA DE RELATORIOS DOS ALUNOS PERTENCENTES A UNIVERSIDADE LOGADA */
    public List<Relatorio> listaRelatoriosUniLogada() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.encontro.aluno.universidade.idUsuario = " + usuarioLogado.getIdUsuario());
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatoriosUniLogada - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }

    /* LISTA DE RELATORIOS DOS ALUNOS PERTENCENTES AO CURSO QUE O COORDENADOR COORDENA */
    public List<Relatorio> listaRelatoriosCoordLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.encontro.aluno.curso.idCurso = " + usuarioLogado.getCurso().getIdCurso());
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatoriosCoordLogado - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }

    /* LISTA DE RELATORIOS DOS ALUNOS QUE O PROFESSOR ORIENTA */
    public List<Relatorio> listaRelatoriosOrientadorLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.encontro.orientador.idUsuario = " + usuarioLogado.getIdUsuario());
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaRelatoriosCoordLogado - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }

    /* TESTE PARA SELECIONAR O ALUNO E APARECER A LISTA DE RELATORIOS */
    public List<Relatorio> listRelatorioAlunoSelecionado(int idAluno) {
        List<Relatorio> listaRelatorios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Relatorio u where u.encontro.aluno.idUsuario = " + idAluno);
            listaRelatorios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo lista - Classe Relatorio DAO");
        }
        entityManager.close();
        return listaRelatorios;
    }
    /* TESTE PARA SELECIONAR O ALUNO E APARECER A LISTA DE RELATORIOS */
}
