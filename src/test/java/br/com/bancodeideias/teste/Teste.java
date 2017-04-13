/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.teste;

import br.com.bancodeideias.domain.AnalisePropostatcc;
import br.com.bancodeideias.domain.Curso;
import br.com.bancodeideias.domain.PropostaTcc;
import br.com.bancodeideias.domain.SituacaoProjeto;
import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.repository.CursoDAO;
import br.com.bancodeideias.repository.SituacaoProjetoDAO;
import br.com.bancodeideias.repository.UsuarioDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

public class Teste {
    @Test
    public void salvar() throws ParseException { //quando for universidade
       List<AnalisePropostatcc> listaAnalisePropostaTcc = new ArrayList<>();
       listaAnalisePropostaTcc.size();
        System.out.println("Resultado: " + listaAnalisePropostaTcc.size());
        
    }
    
    @Test
    @Ignore
    public void buscar(){
        Usuario usuario = new Usuario();
        UsuarioDAO dao = new UsuarioDAO();
        
        usuario.setEmail("administrador@hotmail.com");
        usuario.setMatricula("123456");
        try {
            dao.solicitarNovaSenha(usuario.getEmail(), usuario.getMatricula());
            System.out.println("Usuario encontrado teste");
        } catch (Exception e) {
        }
        
    }
}
