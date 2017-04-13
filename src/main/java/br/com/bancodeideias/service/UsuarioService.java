package br.com.bancodeideias.service;

import br.com.bancodeideias.domain.Usuario;
import br.com.bancodeideias.repository.UsuarioDAO;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
        String senha = this.convertStringToMd5(usuario.getSenha()); //Convertendo a senha para MD5
        usuario.setSenha(senha);
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

    public List<Usuario> listar() {
        List<Usuario> listaUsuario;
        listaUsuario = this.getUsuarioDAO().listar();
        return listaUsuario;
    }

    
    public Usuario solicitarNovaSenha(String email, String matricula){
        Usuario usuario = this.getUsuarioDAO().solicitarNovaSenha(email, matricula);
        if(usuario != null){
            System.out.println("Usuario encontrado SERVICE"); // provisorio
            return usuario;
        }   
        else{
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
    
   
 /*
    public List<Usuario> listAcademicos() {
        List<Usuario> listaUsuario = null;
        listaUsuario = this.getUsuarioDAO().listAcademicos();
        return listaUsuario;
    }*/
 /*
      public List<Usuario> listAcadUniLogada() {
        List<Usuario> listaUsuario = null;
        listaUsuario = this.getUsuarioDAO().listAcadUniLogada();
        return listaUsuario;
    }*/
 /*
    public List<Usuario> listUniversidades() {
        List<Usuario> listaUsuario = null;
        listaUsuario = this.getUsuarioDAO().listUniversidades();
        return listaUsuario;
    }*/
    //Converter senha para MD5
    /*Tentanto um novo login
    public Usuario logar(String email, String senha) {
        Usuario usuario = this.getUsuarioDAO().buscarLogin(email, senha);

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
    }*/
    
    /* =========Getts and setts ============= */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

}
