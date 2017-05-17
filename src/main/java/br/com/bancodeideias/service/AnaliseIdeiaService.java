package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.AnaliseIdeia;
import br.com.bancodeideias.repository.AnaliseIdeiaDAO;
import java.io.Serializable;
import java.util.List;

public class AnaliseIdeiaService implements Serializable {

    private AnaliseIdeiaDAO analiseIdeiaDAO;

    public AnaliseIdeiaService() {
        analiseIdeiaDAO = new AnaliseIdeiaDAO();
    }

    public void salvar(AnaliseIdeia analiseIdeia) {
        this.getAnaliseIdeiaDAO().salvar(analiseIdeia);
    }

    public void alterar(AnaliseIdeia analiseIdeia) {
        this.getAnaliseIdeiaDAO().alterar(analiseIdeia);
    }

    public void remover(AnaliseIdeia analiseIdeia) {
        this.getAnaliseIdeiaDAO().remover(analiseIdeia.getIdAnalise());
    }

    public List<AnaliseIdeia> listar() {
        List<AnaliseIdeia> lista;
        lista = this.getAnaliseIdeiaDAO().listar();
        return lista;
    }
    
    public List<AnaliseIdeia> listarAnalisesParaUniversidade() {
        List<AnaliseIdeia> lista;
        lista = this.getAnaliseIdeiaDAO().listarAnalisesParaUniversidade();
        return lista;
    }

    //getts and setts
    public AnaliseIdeiaDAO getAnaliseIdeiaDAO() {
        return analiseIdeiaDAO;
    }

    public void setAnaliseIdeiaDAO(AnaliseIdeiaDAO analiseIdeiaDAO) {
        this.analiseIdeiaDAO = analiseIdeiaDAO;
    }

}
