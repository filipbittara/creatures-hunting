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

@WebFilter
public class AdminFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String auth = request.getHeader("Authorization");

        if (auth == null) {
            response401(response);
            return;
        }

        String[] credentials = new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
        String loginName = credentials[0];
        String password = credentials[1];

        UserFacade userFacade = WebApplicationContextUtils.getWebApplicationContext(servletRequest.getServletContext()).getBean(UserFacade.class);
        UserDTO matchingUser = userFacade.findUserByEmail(loginName);

        if (matchingUser == null) {
            log.warn("no user with email {}", loginName);
            response401(response);
            return;
        }

        UserAuthenticateDTO userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setUserId(matchingUser.getId());
        userAuthenticateDTO.setPassword(password);

        if (!userFacade.authenticate(userAuthenticateDTO)) {
            log.warn("wrong credentials: user={} password={}", loginName, password);
            return;
        }

        request.setAttribute("authenticatedUser", matchingUser);

        filterChain.doFilter(request, response);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"email and password here\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> You are not authorized to view this page.</body></html>");
    }

    @Override
    public void destroy() {

    }
}
