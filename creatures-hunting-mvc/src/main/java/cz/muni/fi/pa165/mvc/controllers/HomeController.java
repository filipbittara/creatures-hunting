package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private CreatureFacade creatureFacade;
    
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "/home";
    }
    
    @RequestMapping(value = {"/circle/{x}/{y}/{radius}"}, method = RequestMethod.GET)
    public String circle(Model model, @PathVariable double x, @PathVariable double y, @PathVariable double radius) {
        String creatures = "";
        for (CreatureDTO c: creatureFacade.getCreaturesInCircle(y, x, radius)) {
            creatures += c.getName() + ", ";
        }
        if(creatures.length() > 0) {
            creatures = creatures.substring(0, creatures.length() - 2);
        }
        model.addAttribute("circle", creatures);
        return "/home";
    }
}
