package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.facade.WeaponFacade;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Ondrej Klein
 */
@Controller
@RequestMapping("/weapon")
public class WeaponController {
    
    @Autowired
    private WeaponFacade weaponFacade;
    
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("weapons", weaponFacade.getAllWeapons());
        return "/weapon/list";
    }
    
    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("weapon", weaponFacade.getWeaponById(id));
        return ("weapon/detail");
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
    public String delete(@PathVariable long id, Model model) {
        // TODO - fill the delete method
        //return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
        return "";
    }
}

