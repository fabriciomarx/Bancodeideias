/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.util;

import br.com.bancodeideias.domain.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "FiltroAdmin", urlPatterns = {"/paginas/universidade/*"})
public class FiltroUniversidade implements Filter {

    public FiltroUniversidade() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession sessao = (HttpSession) req.getSession();

        Usuario usuario = null;
        if (sessao != null) {
            usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        }

        //se o usuario nao estiver logado e quiser ir para /paginas/* ele é redireciando para o login
        if (usuario == null) {
            res.sendRedirect(req.getContextPath() + "/login/login.xhtml");

        } else if (!usuario.getTipoUsuario().equals("Universidade")) {
            res.sendRedirect(req.getContextPath() + "/paginas/principal/index.xhtml");

        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

}
