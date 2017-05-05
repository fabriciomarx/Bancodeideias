package br.com.bancodeideias.repository;

import br.com.bancodeideias.domain.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class UsuarioDAO implements Serializable {

    public UsuarioDAO() {
    }

    public void salvar(Usuario usuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(usuario);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void alterar(Usuario usuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(usuario);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remover(int id) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Usuario usuarioEncontrado = entityManager.find(Usuario.class, id);
        entityManager.remove(usuarioEncontrado);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /* Listar todos os usuario */
    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    public Usuario buscarEmail(String emailUsuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Usuario usuario = (Usuario) entityManager
                    .createQuery(
                            "SELECT u from Usuario u where u.email = :emailUsuario")
                    .setParameter("emailUsuario", emailUsuario)
                    .getSingleResult();
            return usuario;
        } catch (Exception e) {
            System.out.println("Erro no metodo buscarEmail - Classe Usuario DAO");
            return null;
        }
    }

    /* Lista de academicos */
    public List<Usuario> listaAcademicos() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' OR u.tipoUsuario = 'Professor' OR u.tipoUsuario = 'Coordenador'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaAcademicos - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* Lista de academicos da universidade logada*/
    public List<Usuario> listaAcademicosUniLogada() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' OR u.tipoUsuario = 'Professor' OR u.tipoUsuario = 'Coordenador'"
                    + " AND u.universidade.idUsuario = " + usuarioLogado.getIdUsuario());
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaAcademicosUniLogada - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* Lista de tdos professores */
    public List<Usuario> listaProfessores() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Professor'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaProfessores - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* Lista de universidades cadastradas */
    public List<Usuario> listaUniversidades() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Universidade'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaUniversidades - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* Metodo para o usuario solicitar uma nova senha */
    public Usuario solicitarNovaSenha(String emailUsuario, String matriculaUsuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Usuario usuario = (Usuario) entityManager
                    .createQuery("Select u from Usuario u where u.email = :emailUsuario "
                            + " and u.matricula = :matriculaUsuario")
                    .setParameter("emailUsuario", emailUsuario)
                    .setParameter("matriculaUsuario", matriculaUsuario)
                    .getSingleResult();
            System.out.println("Usuario encontrado DAO"); //provisorio
            return usuario;

        } catch (Exception e) {
            System.out.println("Usuario não encontrado DAO"); //provisorio
            return null;
        }

    }
}
