package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    
    @Autowired
    private AreaFacade areaFacade;
    
    @Autowired
    private WeaponFacade weaponFacade;
    
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

    @ModelAttribute("areas")
    public List<AreaDTO> getAreas() {    
        return areaFacade.getAllAreas();
    }

}