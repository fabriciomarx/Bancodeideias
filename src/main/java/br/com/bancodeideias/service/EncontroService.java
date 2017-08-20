package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Encontro;
import br.com.bancodeideias.repository.EncontroDAO;
import java.io.Serializable;
import java.util.List;

public class EncontroService implements Serializable {

    private EncontroDAO encontroDAO;

    public EncontroService() {
        encontroDAO = new EncontroDAO();
    }

    public void salvar(Encontro encontro) {
        this.getEncontroDAO().salvar(encontro);
    }

    public void alterar(Encontro encontro) {
        this.getEncontroDAO().alterar(encontro);
    }

    public void remover(Encontro encontro) {
        this.getEncontroDAO().remover(encontro.getIdEncontro());

    }

    /* LISTA DE ENCONTROS FILTRADOS POR ACADEMICO (aluno) Metodo que a universidade usa */
    public List<Encontro> listarEncontrosAcademicoSelecionado(int id) {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosAcademicoSelecionado(id);
        return lista;
    }

    /* LISTA DE ENCONTROS FILTRADOS POR ACADEMICO (professor) Metodo que a universidade usa */
    public List<Encontro> listarEncontrosProfessorSelecionado(int id) {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosProfessorSelecionado(id);
        return lista;
    }

    public List<Encontro> listarEncontrosAcademicoSelecionadoParaProfessor(int id) {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosAcademicoSelecionadoParaProfessor(id);
        return lista;
    }

    /* LISTA TODOS OS ENCONTROS CADASTRADOS NO SISTEMA */
    public List<Encontro> listar() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listar();
        return lista;
    }

    /* LISTA TODOS OS ENCONTROS DE ALUNOS DA UNIVERSIDADE LOGADA */
    public List<Encontro> listarEncontrosParaUniv() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosParaUniv();
        return lista;
    }

    /* LISTA DE ENCONTROS DE ALUNOS QUE SEJAM DO CURSO QUE O COORDENADOR GERENCIA */
    public List<Encontro> listarEncontrosParaCoord() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosParaCoord();
        return lista;
    }

    /* LISTA TODOS OS ENCONTROS QUE A PESSOA CADASTROU OU FOI CHAMADA PARA PARTICIPAR */
    public List<Encontro> listarEncontrosAlu_Prof() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosAlu_Prof();
        return lista;
    }

    /* Listar apenas os encontros do aluno logado que já foram realizados 
        metodo utilizado na hora de incluir relatorios */
    public List<Encontro> listarEncontrosRealizadosAluno() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosRealizadosAluno();
        return lista;
    }


    /* ============ GETS AND SETS =========== */
    public EncontroDAO getEncontroDAO() {
        return encontroDAO;
    }

    public void setEncontroDAO(EncontroDAO encontroDAO) {
        this.encontroDAO = encontroDAO;
    }

}
