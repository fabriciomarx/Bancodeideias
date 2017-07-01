/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.repository.UsuarioDAO;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author fabri
 */
public class UsuarioService implements Serializable {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public void salvar(Usuario usuario) {
        String senha = this.convertStringToMd5(usuario.getSenha()); //Convertendo a senha para MD5
        usuario.setSenha(senha);
        this.getUsuarioDAO().salvar(usuario);

    }

    public void alterar(Usuario usuario) {
        String senhaAntiga = usuario.getSenha();
        
        String senhaNova = this.convertStringToMd5(usuario.getSenha()); //Convertendo a senha para MD5

       
        if (senhaAntiga.equals(senhaNova)) {
            usuario.setSenha(senhaAntiga);
        } else {
            usuario.setSenha(senhaNova);
        }
            
        System.out.println("Nova " + senhaNova);
        System.out.println("Antiga " + senhaAntiga);

        this.getUsuarioDAO().alterar(usuario);
    }

    public void remover(Usuario usuario) {
        this.getUsuarioDAO().remover(usuario.getIdUsuario());
    }

    //Fazer Login 
    public Usuario doLogin(String email, String senha) {
        Usuario usuario = this.getUsuarioDAO().buscarEmail(email);

        if (usuario != null) {
            if (usuario.getSenha().equals(this.convertStringToMd5(senha))) {
                return usuario;
            } else {

                System.out.println("Usuario e/ou senha invalidos");
            }
        } else {
            System.out.println("Usuario não encontrado");

        }
        return null;
    }

    private String convertStringToMd5(String valor) {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("MD5");
            byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : valorMD5) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println("Erro metodo convertStringToMd5 - Classe Usuario Service");
            return null;
        }

    }

    /* Listar todos os usuarios */
    public List<Usuario> listar() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listar();
        return listaUsuario;
    }

    /* Lista de academicos (aluno)*/
    public List<Usuario> listaAlunos() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listaAlunos();
        return listaUsuario;
    }
    
    /* Lista de academicos da universidade logada */
    public List<Usuario> listaAcademicosUniLogada() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listaAcademicosUniLogada();
        return listaUsuario;
    }

    /* Lista de professoes que estudao na universdade do aluno logado 
        -> metodo utilizado no encontro controller, para adicionar o participante no encontro*/
    public List<Usuario> listaProfessores() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listaProfessores();
        return listaUsuario;
    }
    
    /* LISTA DE UNIVERSIDADES PENDENTES CADASTRADAS NO SISTEMA */
    public List<Usuario> listaUniversidadesPendentes() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listaUniversidadesPendentes();
        return listaUsuario;
    }

    public Usuario solicitarNovaSenha(String email, String matricula) {
        Usuario usuario = this.getUsuarioDAO().solicitarNovaSenha(email, matricula);
        if (usuario != null) {
            System.out.println("Usuario encontrado SERVICE"); // provisorio
            return usuario;
        } else {
            System.out.println("Usuario nao encontrado SERVICE"); // provisorio
            return null;
        }
    }

    public String gerarNovaSenha() {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

        String senha = "";

        for (int x = 0; x < 10; x++) {
            int j = (int) (Math.random() * carct.length);
            senha += carct[j];

        }

        return senha;

    }

    /* Lista de universidades */
    public List<Usuario> listUniversidades() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listaUniversidades();
        return listaUsuario;
    }
    
    /*
    public void quantidadePorTipo(){
        this.getUsuarioDAO().quantidadeCadaTipo();
    }*/
    
    public int quantidadeAluno() {
        int qtdeAlu = 0;
        qtdeAlu = this.getUsuarioDAO().quantidadeAluno();
        return qtdeAlu;
    }

    public int quantidadeCoordenador() {
        int qtdeCoord = 0;
        qtdeCoord = this.getUsuarioDAO().quantidadeCoordenador();
        return qtdeCoord;
    }

    public int quantidadeProfessor() {
        int qtdePro = 0;
        qtdePro = this.getUsuarioDAO().quantidadeProfessor();
        return qtdePro;
    }

    public int quantidadeUniversidade() {
        int qtdeUni = 0;
        qtdeUni = this.getUsuarioDAO().quantidadeUniversidade();
        return qtdeUni;
    }

    /* =========Getts and setts ============= */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

}
