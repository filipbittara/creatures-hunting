package cz.muni.fi.pa165.mvc.security;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 *
 * @author David Kizivat
 */

@WebFilter(urlPatterns={"/creature/*", "/home/*","/user/*", "/area/*", "/weapon/*"})
public class AdminFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
         if (request.getSession().getAttribute("authenticated") == null) {
            response.sendRedirect(request.getContextPath());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
