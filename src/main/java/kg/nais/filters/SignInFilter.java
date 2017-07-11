package kg.nais.filters;

import kg.nais.controllers.SessionController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kg.nais.tools.ViewPath.INDEX;
import static kg.nais.tools.ViewPath.REDIRECT;

/**
 * Created by B-207 on 5/28/2017.
 */

public class SignInFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SessionController sc = (SessionController)((HttpServletRequest) request).getSession().getAttribute("sessionController");
        String contextPath = ((HttpServletRequest) request).getContextPath();
        if(sc == null){
            sc = new SessionController();
        }
        if(sc.isLogged())
            ((HttpServletResponse) response).sendRedirect(contextPath + INDEX + REDIRECT);

        chain.doFilter(request, response);
    }

    public void destroy() {

    }

}
