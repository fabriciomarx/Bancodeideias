package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.SituacaoProjeto;
import br.com.bancodeideias.repository.SituacaoProjetoDAO;
import java.io.Serializable;
import java.util.List;

public class SituacaoProjetoService implements Serializable {

    private SituacaoProjetoDAO situacaoProjetoDAO;

    public SituacaoProjetoService() {
        situacaoProjetoDAO = new SituacaoProjetoDAO();
    }

    public void salvar(SituacaoProjeto situacaoProjeto) {
        this.getSituacaoProjetoDAO().salvar(situacaoProjeto);

    }

    public void alterar(SituacaoProjeto situacaoProjeto) {
        this.getSituacaoProjetoDAO().alterar(situacaoProjeto);
    }

    public void remover(SituacaoProjeto situacaoProjeto) {
        this.getSituacaoProjetoDAO().remover(situacaoProjeto.getIdSituacao());
    }

    public List<SituacaoProjeto> listar() {
        List<SituacaoProjeto> listaSituacoes;
        listaSituacoes = this.getSituacaoProjetoDAO().listar();
        return listaSituacoes;
    }

    public List<SituacaoProjeto> listarSituaçoesCooord() {
        List<SituacaoProjeto> listaSituacoes;
        listaSituacoes = this.getSituacaoProjetoDAO().listarSituaçoesCooord();
        return listaSituacoes;
    }

    /* =========Getts and setts ============= */
    public SituacaoProjetoDAO getSituacaoProjetoDAO() {
        return situacaoProjetoDAO;
    }

    public void setSituacaoProjetoDAO(SituacaoProjetoDAO situacaoProjetoDAO) {
        this.situacaoProjetoDAO = situacaoProjetoDAO;
    }

}
