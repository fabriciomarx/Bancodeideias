package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.AnalisePropostatcc;
import br.com.bancodeideias.repository.AnalisePropostaTccDAO;
import java.io.Serializable;
import java.util.List;

public class AnalisePropostaTccService implements Serializable {

    private AnalisePropostaTccDAO analisePropostaTccDAO;

    public AnalisePropostaTccService() {
        analisePropostaTccDAO = new AnalisePropostaTccDAO();
    }

    public void salvar(AnalisePropostatcc analisePropostaTcc) {
        this.getAnalisePropostaTccDAO().salvar(analisePropostaTcc);
    }

    public void alterar(AnalisePropostatcc analisePropostaTcc) {
        this.getAnalisePropostaTccDAO().alterar(analisePropostaTcc);
    }

    public void remover(AnalisePropostatcc AnalisePropostaTcc) {
        this.getAnalisePropostaTccDAO().remover(AnalisePropostaTcc.getIdAnalise());
    }

    public List<AnalisePropostatcc> listar() {
        List<AnalisePropostatcc> lista;
        lista = this.getAnalisePropostaTccDAO().listar();
        return lista;
    }

    //gets and setts
    public AnalisePropostaTccDAO getAnalisePropostaTccDAO() {
        return analisePropostaTccDAO;
    }

    public void setAnalisePropostaTccDAO(AnalisePropostaTccDAO analisePropostaTccDAO) {
        this.analisePropostaTccDAO = analisePropostaTccDAO;
    }

}
