package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Relatorio;
import br.com.bancodeideias.repository.RelatorioDAO;
import java.io.Serializable;
import java.util.List;

public class RelatorioService implements Serializable {

    private RelatorioDAO relatorioQuinzenalDAO;

    public RelatorioService() {
        relatorioQuinzenalDAO = new RelatorioDAO();
    }

    public void salvar(Relatorio relatorio) {
        this.getRelatorioQuinzenalDAO().salvar(relatorio);
    }

    public void alterar(Relatorio relatorio) {
        this.getRelatorioQuinzenalDAO().alterar(relatorio);
    }

    public void remover(Relatorio relatorio) {
        this.getRelatorioQuinzenalDAO().remover(relatorio.getIdRelatorio());
    }

    
    public List<Relatorio> listar() {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listar();
        return listaRelatorios;
    }
    
    
    public List<Relatorio> listarRelatorioAlunoSelecionado(int id) {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listarRelatorioAlunoSelecionado(id);
        return listaRelatorios;
    }
    
    public List<Relatorio> listarRelatorioAlunoSelecionadoParaCoord(int id) {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listarRelatorioAlunoSelecionadoParaCoord(id);
        return listaRelatorios;
    }

    /* LISTAR OS RELATORIOS DO ALUNO LOGADO */
    public List<Relatorio> listaRelatorioLogado() {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listaRelatorioLogado();
        return listaRelatorios;
    }

    /* LISTA DE RELATORIOS DOS ALUNOS PERTENCENTES A UNIVERSIDADE LOGADA */
    public List<Relatorio> listaRelatoriosUniLogada() {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listaRelatoriosUniLogada();
        return listaRelatorios;
    }

    /* LISTA DE RELATORIOS DOS ALUNOS PERTENCENTES AO CURSO QUE O COORDENADOR COORDENA */
    public List<Relatorio> listaRelatoriosCoordLogado() {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listaRelatoriosCoordLogado();
        return listaRelatorios;
    }

    /* TESTE PARA SELECIONAR O ALUNO E APARECER A LISTA DE RELATORIOS */
    public List<Relatorio> listaRelatoriosOrientadorLogado() {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listaRelatoriosOrientadorLogado();
        return listaRelatorios;
    }

    public List<Relatorio> listRelatorioAlunoSelecionado(int idUsuario) {
        List<Relatorio> listaRelatorios;
        listaRelatorios = this.getRelatorioQuinzenalDAO().listRelatorioAlunoSelecionado(idUsuario);
        return listaRelatorios;
    }

    //gets and setts
    public RelatorioDAO getRelatorioQuinzenalDAO() {
        return relatorioQuinzenalDAO;
    }

    public void setRelatorioQuinzenalDAO(RelatorioDAO relatorioQuinzenalDAO) {
        this.relatorioQuinzenalDAO = relatorioQuinzenalDAO;
    }

}
