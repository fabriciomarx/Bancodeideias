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
    
    /* LISTA DE Academicos filtrados por curso Metodo que a universidade usa */
    public List<Usuario> listarAcademicosCursoSelecionado(int id) {
        List<Usuario> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.curso.idCurso = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarAcademicosCursoSelecionado - Classe UsuarioDAO");
        }
        entityManager.close();
        return lista;
    }
    
    /* Filtrar academicos por tipo selecionado - Universidade - academicos */
    public List<Usuario> listarAcademicosTipoSelecionado(String tipo) {
        List<Usuario> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.tipoUsuario like " + tipo);
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listarAcademicosTipoSelecionado - Classe UsuarioDAO");
        }
        entityManager.close();
        return lista;
    }

    /* LISTA TODOS OS USUARIOS CADASTRADOS NO SISTEMA */
    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario != 'Admin'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listar - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* BUSCAR EMAIL DO USUARIO */
    public Usuario buscarEmail(String emailUsuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Usuario usuario = (Usuario) entityManager
                    .createQuery(
                            "SELECT u from Usuario u where u.email = :emailUsuario AND u.situacao = 'Ativo'")
                    .setParameter("emailUsuario", emailUsuario)
                    .getSingleResult();
            return usuario;
        } catch (Exception e) {
            System.out.println("Erro no metodo buscarEmail - Classe Usuario DAO");
            return null;
        }
    }

    /* LISTA DE ACADEMICOS(alunos) CADASTRADOS NO SISTEMA */
    public List<Usuario> listaAlunos() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            String sql = ""; //vazio
            if (usuarioLogado.getTipoUsuario().equals("Coordenador")) {
                /* se o usuario logado for Coordenador, carregar a lista de alunos do curso dele */
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' AND u.curso.idCurso = "
                        + usuarioLogado.getCurso().getIdCurso();
            } else if (usuarioLogado.getTipoUsuario().equals("Admin")) {
                /* se o usuario logado for Admin, carregar a lista de todos os alunos */
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' ";
            } else if (usuarioLogado.getTipoUsuario().equals("Universidade")) {
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' "
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getIdUsuario();
            } else {
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' "
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getUniversidade().getIdUsuario();
            }
            Query query = entityManager.createQuery(sql);
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaAcademicos - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* LISTA DE ACADEMICOS DA UNIVERSIADDE LOGADA */
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

    /* LISTA DE PROFESSORES CADASTRADOS NO SISTEMA */
    public List<Usuario> listaProfessores() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado"); //RECUPERANDO O USUARIO LOGADO NA SESSAO

        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            String sql = "";
            if (usuarioLogado.getTipoUsuario().equals("Admin")) {
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Professor'";

            } else if (usuarioLogado.getTipoUsuario().equals("Universidade")) {
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' "
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getIdUsuario();
            } else {
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Professor'"
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getUniversidade().getIdUsuario();
            }
            Query query = entityManager.createQuery(sql);
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaProfessores - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* LISTA DE UNIVERSIDADES CADASTRADAS NO SISTEMA QUE ESTAO ATIVAS */
    public List<Usuario> listaUniversidades() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Universidade'"
                    + " AND u.situacao = 'Ativo'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaUniversidades - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* LISTA DE UNIVERSIDADES PENDENTES CADASTRADAS NO SISTEMA */
    public List<Usuario> listaUniversidadesPendentes() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Universidade' and u.situacao = 'Pendente'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaUniversidadesPendentes - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* METODO PARA SOLICITAR NOVA SENHA */
    public Usuario solicitarNovaSenha(String emailUsuario, String matriculaUsuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Usuario usuario = (Usuario) entityManager
                    .createQuery("Select u from Usuario u where u.email = :emailUsuario and u.matricula = :matriculaUsuario")
                    .setParameter("emailUsuario", emailUsuario)
                    .setParameter("matriculaUsuario", matriculaUsuario)
                    .getSingleResult();
            return usuario;
        } catch (Exception e) {
            System.out.println("USUARIO NÃO ENCONTRADO - DAO");
            return null;
        }
    }
}
