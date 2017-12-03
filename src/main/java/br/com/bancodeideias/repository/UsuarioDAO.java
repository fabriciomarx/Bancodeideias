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

    public String mensagem;
    
    public UsuarioDAO() {
    }
    
    /* salvar antigo 
    public void salvar(Usuario usuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(usuario);
        entityManager.getTransaction().commit();
        entityManager.close();

    }*/
    
    public void salvar(Usuario usuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("Select u From Usuario u where u.email = :emailUsuario"
                + " OR u.cpf_cnpj  = :cpfUsuario OR u.matricula = :matriculaUsuario")
                .setParameter("emailUsuario", usuario.getEmail())
                .setParameter("cpfUsuario", usuario.getCpf_cnpj())
                .setParameter("matriculaUsuario", usuario.getMatricula());

        if (query.getResultList().isEmpty()) {
            entityManager.persist(usuario);
            this.setMensagem("Usuário salvo com sucesso !");
        } else {
            this.setMensagem("O Email ou CPF/CNPJ ou Matrícula já esta cadastrado!");
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    /* Com validação de email 
    public void alterar(Usuario usuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        entityManager.getTransaction().begin();
        
        Query query = entityManager.createQuery("Select u From Usuario u where u.email = :emailUsuario")
                .setParameter("emailUsuario", usuario.getEmail());

        if (query.getResultList().isEmpty()) {
             entityManager.merge(usuario);
            this.setMensagem("Usuário alterado com sucesso !");
        } else {
            this.setMensagem("O email já esta cadastrado! Tente outro email valido");
        }
        
        entityManager.getTransaction().commit();
        entityManager.close();
    }*/
    
    /* Sem validação, serve por exemplo para alterar senha */
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
    
    /* FILTRAR USUARIOS POR UNIVERSIDADE - UTILIZADO NA LISTA DE USUARIOS NO ADMIN */
    public List<Usuario> listaUsuariosPorUniversidade(int id) {
        List<Usuario> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.universidade.idUsuario = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listUsuariosPorUniversidade - UsuarioDAO");
        }
        entityManager.close();
        return lista;
    }
    
    /* LISTA DE ACADEMICOS FILTRADOS POR CURSO, USUARIO UNIVERSIDADE QUE UTILIZA */
    public List<Usuario> listarAcademicosCursoSelecionado(int id) {
        List<Usuario> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.curso.idCurso = " + id);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarAcademicosCursoSelecionado - Classe UsuarioDAO");
        }
        entityManager.close();
        return lista;
    }
    
    /* FILTRAR ACADEMICOS POR TIPO SELECIONADO, USUARIO UNIVERSIDADE QUE UTILIZA */
    public List<Usuario> listarAcademicosTipoSelecionado(String tipo) {
        List<Usuario> lista = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.tipoUsuario like " + tipo);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listarAcademicosTipoSelecionado - Classe UsuarioDAO");
        }
        entityManager.close();
        return lista;
    }

    /* LISTA TODOS OS USUARIOS CADASTRADOS NO SISTEMA */
    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario != 'Admin'"
                    + " AND u.situacao = 'Ativo'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listar - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* BUSCAR EMAIL DO USUARIO */
    public Usuario buscarEmail(String emailUsuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Usuario usuario = (Usuario) entityManager.createQuery("SELECT u from Usuario u where u.email = :emailUsuario AND u.situacao = 'Ativo'")
                    .setParameter("emailUsuario", emailUsuario).getSingleResult();
            return usuario;
        } catch (Exception e) {
            System.err.println("Erro no metodo buscarEmail - Classe UsuarioDAO");
            return null;
        }
    }

    /* LISTA DE ACADEMICOS CADASTRADOS NO SISTEMA */
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
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Aluno' AND u.situacao = 'Ativo' "
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getUniversidade().getIdUsuario();
            }
            Query query = entityManager.createQuery(sql);
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listaAcademicos - Classe Usuario DAO");
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
            System.out.println("Qutd " + query.getMaxResults());
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listaAcademicosUniLogada - Classe Usuario DAO");
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
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Professor' "
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getIdUsuario();
            } else { /*Fiz or 'Coordenador', para o caso do aluno escolher o coordenador como orientador'*/
                sql = "SELECT u FROM Usuario u where u.tipoUsuario = 'Professor' OR u.tipoUsuario = 'Coordenador' "
                        + "AND u.universidade.idUsuario = " + usuarioLogado.getUniversidade().getIdUsuario();
            }
            Query query = entityManager.createQuery(sql);
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listaProfessores - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* LISTA DE UNIVERSIDADES CADASTRADAS NO SISTEMA QUE ESTAO ATIVAS - USUARIO ADMIN QUE UTILIZA */
    public List<Usuario> listaUniversidades() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u where u.tipoUsuario = 'Universidade'"
                    + " AND u.situacao = 'Ativo'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro no metodo listaUniversidades - Classe Usuario DAO");
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* LISTA DE UNIVERSIDADES PENDENTES CADASTRADAS NO SISTEMA - USUARIO ADMIN QUE UTILIZA */
    public List<Usuario> listaUniversidadesPendentes() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.tipoUsuario = 'Universidade' AND u.situacao = 'Pendente'");
            listaUsuarios = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro no metodo listaUniversidadesPendentes - Classe Usuario DAO " 
             + e.getMessage());
        }
        entityManager.close();
        return listaUsuarios;
    }

    /* METODO PARA SOLICITAR NOVA SENHA */
    public Usuario solicitarNovaSenha(String emailUsuario, String cpfUsuario) {
        EntityManager entityManager = JPAConnection.getEntityManager();
        try {
            Usuario usuario = (Usuario) entityManager
                    .createQuery("Select u from Usuario u where u.email = :emailUsuario and u.cpf_cnpj = :cpfUsuario")
                    .setParameter("emailUsuario", emailUsuario)
                    .setParameter("cpfUsuario", cpfUsuario)
                    .getSingleResult();
            return usuario;
        } catch (Exception e) {
            System.out.println("USUARIO NÃO ENCONTRADO - DAO");
            return null;
        }
    }
    
    /* ================= Gets and Sets =================== */
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
