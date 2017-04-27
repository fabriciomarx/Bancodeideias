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
    /*
    public List<PropostaTcc> list(String tipoBusca){
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listar(tipoBusca);
        return lista;
    }*/

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
    
     /* Lista de propostas dos alunos do curso que o coordenador coordena */
    public List<PropostaTcc> listarPropostasParaCoord() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasParaCoord();
        return lista;
    }
    
    /* Todas Propostas pendentes */
    public List<PropostaTcc> listarPendentes() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPendentes();
        return lista;
    }
    
    /* lista de propostas pendentes para coordenador*/
    public List<PropostaTcc> listaPropostasPendentesDaUnivParaCoordenador() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listaPropostasPendentesDaUnivParaCoordenador();
        return lista;
    }
    
    /* Propostas pendentes da universidade */
    public List<PropostaTcc> listaPropostasPendentesDaUniv() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listaPropostasPendentesDaUniv();
        return lista;
    }
        
    
    /* Propostas que o orientador já aceitou e agora elas são projetos */
    public List<PropostaTcc> listarProjetos() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarProjetos();
        return lista;
    }
    
    /* Propostas que o professor foi escolhido para ser orientador */
    public List<PropostaTcc> listarPropostasParaOrientador() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasParaOrientador();
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
