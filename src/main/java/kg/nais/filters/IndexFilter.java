package kg.nais.filters;

import kg.nais.controllers.SessionController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kg.nais.tools.ViewPath.REDIRECT;
import static kg.nais.tools.ViewPath.SIGN_IN;

/**
 * Created by iskyan on 5/28/17.
 */

public class IndexFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionController sc = (SessionController)((HttpServletRequest) request).getSession().getAttribute("sessionController");
        String contextPath = ((HttpServletRequest) request).getContextPath();
        if(sc == null || !sc.isLogged()) {
            ((HttpServletResponse) response).sendRedirect(contextPath + SIGN_IN + REDIRECT);
            sc = new SessionController();
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }

}
