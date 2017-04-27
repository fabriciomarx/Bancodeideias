package br.com.bancodeideias.util;

import br.com.bancodeideias.domain.Usuario;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

@WebFilter(filterName = "FiltroSistema", urlPatterns = {"/paginas/*"})
public class FiltroSistema implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
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
            
        } else {
            chain.doFilter(request, response);
        }

        String requestPath;
        requestPath = req.getRequestURI().toLowerCase(); //o que o usuario registrou para entrar no sistema

/*        if((!usuario.getTipoUsuario().equals("Universidade")) && (requestPath.contains("/paginas/universidade/"))){
            res.sendRedirect(req.getContextPath() + "/paginas/principal/index.xhtml?faces-redirect=true");
        }*/
        
        /*if se o tipo usuario for diferente de Admin e mesmo assim o usuario quiser ir pra pagina do admin
        ele será redirecionado para a index*/
        /*if ((!"Admin".equals(usuario.getTipoUsuario())) && (requestPath.contains("/paginas/administrador/"))) {
            String contextPath = req.getContextPath();
            res.sendRedirect(contextPath + "/paginas/principal/index.xhtml?faces-redirect=true");
        }else if ((!"Aluno".equals(usuario.getTipoUsuario())) && (requestPath.contains("/paginas/aluno/"))) {
            String contextPath = req.getContextPath();
            res.sendRedirect(contextPath + "/paginas/principal/index.xhtml?faces-redirect=true");
           
          
        } else if ((!"Universidade".equals(usuario.getTipoUsuario())) && (requestPath.contains("/paginas/universidade"))) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/paginas/index.xhtml?faces-redirect=true");
            return;
        } else if ((!"Coordenador".equals(usuario.getTipoUsuario())) && (requestPath.contains("/paginas/coordenador"))) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/paginas/index.xhtml?faces-redirect=true");
            return;
        } else if ((!"Orientador".equals(usuario.getTipoUsuario())) && (requestPath.contains("/paginas/orientador"))) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/paginas/index.xhtml?faces-redirect=true");
            return;
        }*/
    }

}
