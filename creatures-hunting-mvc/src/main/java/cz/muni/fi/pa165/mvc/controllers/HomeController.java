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

    @ModelAttribute("areas")
    public List<AreaDTO> getAreas() {    
        return areaFacade.getAllAreas();
    }
    
    @RequestMapping(value = {"/areas"}, method = RequestMethod.POST)
    public String areas(Model model, HttpServletRequest request) {
        String result = "";
        List<AreaDTO> areas = new ArrayList<>(); 
        for(String name : request.getParameterValues("field")) {
            if(!"".equals(name)) {
                for(AreaDTO a : areaFacade.getAllAreas()) {
                    if(name.equals(a.getName())) {
                        areas.add(a);
                    }
                }
            }
        }
              
        for(WeaponDTO w : weaponFacade.getWeaponsToGoTroughAreas(areas)) {
            result += w.getName() + ", ";
        }
        
        if(result.length() > 0) {
            result = result.substring(0, result.length() - 2);
        }
        
        model.addAttribute("weaponUse", result);  
        return "/home";
    }

}