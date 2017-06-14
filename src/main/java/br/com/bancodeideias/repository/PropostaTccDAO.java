package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.PropostaTcc;
import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class PropostaTccDAO implements Serializable {

    public PropostaTccDAO() {
    }

    public void salvar(PropostaTcc propostaTcc) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(propostaTcc);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(PropostaTcc propostaTcc) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(propostaTcc);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        PropostaTcc propostaTccEncontrada = entityManager.find(PropostaTcc.class, id);
        entityManager.remove(propostaTccEncontrada);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    //Utilizado no filtro do orientador (pagina aprovar proposta )
    public List<PropostaTcc> listarPropostasAlunoSelecionado(int id) {
        List<PropostaTcc> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("Select u From PropostaTcc u where u.academico.idUsuario = "
                    + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPropostasAlunoSelecionado - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return lista;
    }
    
    /* LISTA DE TODAS AS PROPOSTAS DE TCC CADASTRADAS NO SISTEMA */
    public List<PropostaTcc> listar() {
        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u");
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* LISTA DE PROPOSTAS DO ALUNO LOGADO */
    public List<PropostaTcc> listarPropostasLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.academico.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
            System.out.println("DEU CERTO LISTAR LOGADO");
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* LISTA DE PROPOSTAS DOS ALUNOS PERTENCENTES AO CURSO QUE O COORDENADOR COORDENA */
    public List<PropostaTcc> listarPropostasParaCoord() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.situacao = 'Aprovado' AND u.academico.curso.idCurso = "
                    + usuarioLogado.getCurso().getIdCurso());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPropostasParaCoord - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* Lista de propostas para o coordenador, propostas pendentes que sejam da mesma universidade e do mesmo curso 
    que o coordenador e tambem propostas que o orientador já aprovou*/
    public List<PropostaTcc> listaPropostasPendentesDaUnivParaCoordenador() {

        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u "
                    + "where u.situacao = 'Em analise' "
                    + "AND u.aprovacaoOrientador = 'Aprovado'"
                    + "AND u.academico.curso.idCurso = "
                    + usuarioLogado.getCurso().getIdCurso());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaPropostasPendentesDaUnivParaCoordenador - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* LISTA DE PROPOSTAS PENDENTES DA UNIVERSIDADE LOGADA */
    public List<PropostaTcc> listaPropostasPendentesDaUniv() {

        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.situacao = 'Em analise' AND u.academico.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaPropostasPendentesDaUniv - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }
    
    /* LISTA DE PROPOSTAS PENDENTES DA UNIVERSIDADE LOGADA */
    public List<PropostaTcc> listaPropostasDaUniv() {

        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.situacao = 'Aprovado' AND u.academico.universidade.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaPropostasPendentesDaUniv - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* LISTA DE PROJETOS, JA ACEITO PELO ORIENTADOR E PELO COORDENADOR */
    public List<PropostaTcc> listarProjetos() {
        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.aprovacaoOrientador = 'Em analise' AND u.situacao = 'Aprovado'");
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarProjetos - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* LISTA DE PROPOSTAS QUE O PROFESSOR FOI ESCOLHIDO COMO ORIENTADOR */
    public List<PropostaTcc> listarPropostasParaOrientador() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u WHERE u.aprovacaoOrientador = 'Em analise' AND u.orientador.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPropostasParaOrientador - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

}
