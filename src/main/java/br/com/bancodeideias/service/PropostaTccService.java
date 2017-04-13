package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.PropostaTcc;
import br.com.bancodeideias.repository.PropostaTccDAO;
import java.io.Serializable;
import java.util.List;

public class PropostaTccService implements Serializable {

    private PropostaTccDAO propostaTccDAO;

    public PropostaTccService() {
        propostaTccDAO = new PropostaTccDAO();
    }

    public void salvar(PropostaTcc propostaTcc) {
        this.getPropostaTccDAO().salvar(propostaTcc);
    }

    public void alterar(PropostaTcc propostaTcc) {
        this.getPropostaTccDAO().alterar(propostaTcc);
    }

    public void remover(PropostaTcc propostaTcc) {
        this.getPropostaTccDAO().remover(propostaTcc.getIdProposta());
    }

    /* Todas propostas */
    public List<PropostaTcc> listar() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listar();
        return lista;
    }

    /* Propostas do aluno logado */
    public List<PropostaTcc> listarPropostasLogado() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasLogado();
        return lista;
    }
    
    /* Propostas pendentes */
    public List<PropostaTcc> listarPendentes() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPendentes();
        return lista;
    }

    //getts and setts
    public PropostaTccDAO getPropostaTccDAO() {
        return propostaTccDAO;
    }

    public void setPropostaTccDAO(PropostaTccDAO propostaTccDAO) {
        this.propostaTccDAO = propostaTccDAO;
    }
}
