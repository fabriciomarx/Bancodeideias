package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Ideia;
import br.com.bancodeideias.repository.IdeiaDAO;
import java.io.Serializable;
import java.util.List;

public class IdeiaService implements Serializable {

    private IdeiaDAO ideiaDAO;

    public IdeiaService() {
        ideiaDAO = new IdeiaDAO();
    }

    public void salvar(Ideia ideia) {
        this.getIdeiaDAO().salvar(ideia);
    }

    public void alterar(Ideia ideia) {
        this.getIdeiaDAO().alterar(ideia);

    }

    public void remover(Ideia ideia) {
        this.getIdeiaDAO().remover(ideia.getIdIdeia());
    }

    /* LISTAR TODAS AS IDEIAS CADASTRADAS NO SISTEMA*/
    public List<Ideia> listar() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listar();
        return lista;
    }

    /* LISTAR TODAS AS IDEIAS APROVADAS NO SISTEMA*/
    public List<Ideia> listarIdeiasAprovadas() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasAprovadas();
        return lista;
    }

    /* LISTAR TODAS AS IDEIAS RECUSADAS NO SISTEMA*/
    public List<Ideia> listarIdeiasRecusadas() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasRecusadas();
        return lista;
    }

    /* LISTA DE IDEIAS CADASTRADAS POR ALUNOS PERTENCENTES A UNIVERSIDADE LOGADA */
    public List<Ideia> listarIdeiasdaUniversidade() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasdaUniversidade();
        return lista;
    }

    /* LISTA DE IDEIAS PENDENTES DOS ALUNOS PERTENCENTES A UNIVERSIDADE LOGADA */
    public List<Ideia> listarIdeiasPendentesdaUniversidade() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasPendentesdaUniversidade();
        return lista;
    }

    /* LISTAR SOMENTE AS IDEIAS CADASTRADAS DO USUARIO LOGADO */
    public List<Ideia> listarIdeiasLogado() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasLogado();
        return lista;
    }

    /* LISTAR IDEIAS PENDENTES DA UNIVERSIDADE - METODO QUE SERVE PARA PROFESSOR E COORDENADOR*/
    public List<Ideia> listarIdeiasPendentes() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasPendentes();
        return lista;
    }

    //getss and setts
    public IdeiaDAO getIdeiaDAO() {
        return ideiaDAO;
    }

    public void setIdeiaDAO(IdeiaDAO ideiaDAO) {
        this.ideiaDAO = ideiaDAO;
    }

}
