package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ondrej Klein
 */
@Controller
@RequestMapping("/login-check")
public class LoginController {
    @Autowired
    private UserFacade userFacade;
    
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
        UserRole role = null;
        if (userFacade.authenticate(authDTO)) {
            // authenticated
            if (userFacade.isAdmin(user)) {
                role = UserRole.ADMIN;
                model.addAttribute("authenticatedAdmin", user.getEmail());
            } else {
                role = UserRole.USER;
                model.addAttribute("authenticatedUser", user.getEmail());
            }
            return "home";
            
        } else {
            return "login";
        }
    }
}
