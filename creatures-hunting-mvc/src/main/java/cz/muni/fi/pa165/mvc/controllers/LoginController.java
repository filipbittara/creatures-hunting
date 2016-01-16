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
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserFacade userFacade;
    
    @Autowired 
    private HttpSession session;
    
    @RequestMapping(value="/login-check", method=RequestMethod.POST)
    public String loginCheck(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
        UserDTO user = userFacade.findUserByUsername(username);
        if (user == null) {
            // username does not exist
            return "redirect:/";
        }
        
        UserAuthenticateDTO authDTO = new UserAuthenticateDTO();
        authDTO.setUserId(user.getId());
        authDTO.setPassword(password);
        if (userFacade.authenticate(authDTO)) {
            // authenticated
            session.setAttribute("authenticated", user);
            return "redirect:/home";            
            
        }
        return "redirect:/";
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(RedirectAttributes red, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute("authenticated");
        red.addFlashAttribute("Info"," Successfully logged out");
        return "redirect:/";
    }
}
    
