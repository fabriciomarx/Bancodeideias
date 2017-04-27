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

    /* Utilizado no filtro do orientador (pagina aprovar proposta )
    public List<PropostaTcc> listar(String tipoBusca){
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        String sql = "";
        if(tipoBusca.equals("A")){
            sql = "SELECT u FROM PropostaTcc u "
                    + "WHERE u.academico.tipoUsuario = 'Aluno' "
                    + "AND u.aprovacaoOrientador = 'P' "
                    + "AND u.orientador.idUsuario = "
                    + usuarioLogado.getIdUsuario();
        }
        else
            sql = "SELECT u FROM PropostaTcc u "
                    + "WHERE u.academico.curso.idCurso = "
                    + usuarioLogado.getCurso().getIdCurso()
                    + " AND u.aprovacaoOrientador = 'P' "
                    + "AND u.orientador.idUsuario = "
                    + usuarioLogado.getIdUsuario();
        
        
        List<PropostaTcc> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery(sql);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listartipoBusca - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return lista;
    } */
    
    /* Listar todas as propostas de tcc */
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
    
    /* Lista de propostas do aluno logado */
    public List<PropostaTcc> listarPropostasLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.academico.idUsuario = " +
                     usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
            System.out.println("DEU CERTO LISTAR LOGADO");
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }
    
    /* Lista de propostas dos alunos do curso que o coordenador coordena */
    public List<PropostaTcc> listarPropostasParaCoord() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.academico.curso.idCurso = "
                    + usuarioLogado.getCurso().getIdCurso());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPropostasParaCoord - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

    /* Propostas Pendentes */
    public List<PropostaTcc> listarPendentes() {
        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.situacao = 'P'");
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPendentes - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }
    
    /* Lista de propostas para o coordenador, propostas pendentes que sejam da mesma universidade e do mesmo curso 
    que o coordenador */
    public List<PropostaTcc> listaPropostasPendentesDaUnivParaCoordenador() {
        
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.situacao = 'P' AND u.academico.universidade.idUsuario = "
            + usuarioLogado.getUniversidade().getIdUsuario() + " AND u.curso.idCurso = " + usuarioLogado.getCurso().getIdCurso());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaPropostasPendentesDaUnivParaCoordenador - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }
    
    public List<PropostaTcc> listaPropostasPendentesDaUniv() {
        
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.situacao = 'P' AND u.academico.universidade.idUsuario = "
            + usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaPropostasPendentesDaUniv - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }
    
    
    
    
    
    /* Propostas que o orientador já aceitou e agora elas são projetos */
    public List<PropostaTcc> listarProjetos() {
        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u where u.aprovacaoOrientador = 'A'");
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarProjetos - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }
    
    /* Propostas que o professor foi escolhido para ser orientador */
    public List<PropostaTcc> listarPropostasParaOrientador() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<PropostaTcc> listaPropostaTcc = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM PropostaTcc u WHERE u.aprovacaoOrientador = 'P' AND u.orientador.idUsuario = "
                    + usuarioLogado.getIdUsuario());
            listaPropostaTcc = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarPropostasParaOrientador - Classe PropostaTcc DAO");
        }
        entityManager.close();
        return listaPropostaTcc;
    }

}
