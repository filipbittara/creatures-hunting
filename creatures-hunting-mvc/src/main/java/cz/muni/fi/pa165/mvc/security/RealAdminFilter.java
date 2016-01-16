/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mvc.security;

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.UserFacade;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Filip Bittara
 */
@WebFilter(urlPatterns={"/creature/admin/*","/area/admin/*","/weapon/admin/*"})
public class RealAdminFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
         UserFacade userFacade = WebApplicationContextUtils.getWebApplicationContext(servletRequest.getServletContext()).getBean(UserFacade.class);
        if (request.getSession().getAttribute("authenticated") != null) {
            if (!userFacade.isAdmin((UserDTO) request.getSession().getAttribute("authenticated"))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not worthy of this function. Shoo! SHOO! Go away.");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }  
}
