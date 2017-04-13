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

    /* Todas as ideias */
    public List<Ideia> listar() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listar();
        return lista;
    }
    
    /* Ideias do usuario logado */
     public List<Ideia> listarIdeiasLogado() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarIdeiasLogado();
        return lista;
    }

    /* Ideias pendentes */
    public List<Ideia> listarPendentes() {
        List<Ideia> lista;
        lista = this.getIdeiaDAO().listarPendentes();
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
