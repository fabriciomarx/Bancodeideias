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

    /* Listar todos os encontros do sistema */
    public List<Encontro> listar() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listar();
        return lista;
    }

    /* Listar os encontros que a pessoa cadastrou 
        nesse caso a pessoa que é academico*/
    public List<Encontro> listarEncontrosQueCadastrei() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosQueCadastrei();
        return lista;
    }

    /* Listar os encontros que a pessoa foi chamada/selecionada 
        nesse caso a pessoa é participante */
    public List<Encontro> listarEncontrosQueFuiChamado() {
        List<Encontro> lista;
        lista = this.getEncontroDAO().listarEncontrosQueFuiChamado();
        return lista;
    }

    //getts and setts
    public EncontroDAO getEncontroDAO() {
        return encontroDAO;
    }

    public void setEncontroDAO(EncontroDAO encontroDAO) {
        this.encontroDAO = encontroDAO;
    }

}
