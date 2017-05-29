package kg.nais.filters;

import kg.nais.controllers.SessionController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kg.nais.tools.ViewPath.INDEX;
import static kg.nais.tools.ViewPath.REDIRECT;
import static kg.nais.tools.ViewPath.SIGN_IN;

/**
 * Created by B-207 on 5/28/2017.
 */

public class PMFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionController sc = (SessionController)((HttpServletRequest) request).getSession().getAttribute("sessionController");
        String contextPath = ((HttpServletRequest) request).getContextPath();
        if(sc == null || !sc.isLogged()) {
            ((HttpServletResponse) response).sendRedirect(contextPath + SIGN_IN + ".xhtml" + REDIRECT);
            sc = new SessionController();
        }
        int role = sc.getUser().getUserRole().getUserRoleId();
        if(role == 3) {
            ((HttpServletResponse) response).sendRedirect(contextPath + INDEX + ".xhtml" + REDIRECT);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }

}
