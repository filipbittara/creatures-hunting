package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ondrej Klein
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    UserFacade userFacade;
    
    @Autowired
    HttpSession session;
    
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getEmail());
            } else {
                model.addAttribute("authenticatedUser", user.getEmail());
            }
        }
        return "/home";
    }
}
