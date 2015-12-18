package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.UserFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ondrej Klein
 */
@Controller
@RequestMapping("/login-check")
public class LoginController {
    @Autowired
    private UserFacade userFacade;
    
    @Autowired 
    private HttpSession session;
    
    @RequestMapping(method=RequestMethod.POST)
    public String loginCheck(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
        UserDTO user = userFacade.findUserByEmail(username);
        if (user == null) {
            // username does not exist
            return "login";
        }
        
        UserAuthenticateDTO authDTO = new UserAuthenticateDTO();
        authDTO.setUserId(user.getId());
        authDTO.setPassword(password);
        if (userFacade.authenticate(authDTO)) {
            // authenticated
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getEmail());
            } else {
                model.addAttribute("authenticatedUser", user.getEmail());           
            }
            session.setAttribute("authenticated", user);
            return "home";
            
            
            
        }
        return "login";
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(RedirectAttributes red, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute("authenticatedAdmin");
        session.removeAttribute("authenticatedUser");
        red.addFlashAttribute("Info"," Successfully logged out");
        return "redirect:/login-check";
    }
}
    
