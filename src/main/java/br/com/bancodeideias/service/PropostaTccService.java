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

    public List<PropostaTcc> listarPropostasAlunoSelecionado(int id) {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasAlunoSelecionado(id);
        return lista;
    }
    
    /*Filtro utilizado no usuario universidade, nas propostas de tcc dos alunos*/
     public List<PropostaTcc> listarPropostasAlunoSelecionadoParaUniversidade(int id) {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasAlunoSelecionadoParaUniversidade(id);
        return lista;
    }

    public List<PropostaTcc> listarPropostasAlunoSelecionadoCoord(int id) {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasAlunoSelecionadoCoord(id);
        return lista;
    }

    /* LISTA DE TODAS AS PROPOSTAS DE TCC CADASTRADAS NO SISTEMA */
    public List<PropostaTcc> listar() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listar();
        return lista;
    }

    /* LISTA DE PROPOSTAS DO ALUNO LOGADO */
    public List<PropostaTcc> listarPropostasLogado() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasLogado();
        return lista;
    }

    /* LISTA DE PROPOSTAS DOS ALUNOS PERTENCENTES AO CURSO QUE O COORDENADOR COORDENA */
    public List<PropostaTcc> listarPropostasParaCoord() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasParaCoord();
        return lista;
    }

    /* lista de propostas pendentes para coordenador*/
    public List<PropostaTcc> listaPropostasPendentesDaUnivParaCoordenador() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listaPropostasPendentesDaUnivParaCoordenador();
        return lista;
    }

    /* LISTA DE PROPOSTAS PENDENTES DA UNIVERSIDADE LOGADA */
    public List<PropostaTcc> listaPropostasPendentesDaUniv() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listaPropostasPendentesDaUniv();
        return lista;
    }

    /* LISTA DE PROPOSTAS DOS ALUNOS DA UNIVERSIDADE LOGADA */
    public List<PropostaTcc> listaPropostasDaUniv() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listaPropostasDaUniv();
        return lista;
    }

    /* LISTA DE PROPOSTAS QUE O PROFESSOR FOI ESCOLHIDO COMO ORIENTADOR */
    public List<PropostaTcc> listarPropostasParaOrientador() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasParaOrientador();
        return lista;
    }

    public List<PropostaTcc> listarPropostasQueOrientadorParticipa() {
        List<PropostaTcc> lista;
        lista = this.getPropostaTccDAO().listarPropostasQueOrientadorParticipa();
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
