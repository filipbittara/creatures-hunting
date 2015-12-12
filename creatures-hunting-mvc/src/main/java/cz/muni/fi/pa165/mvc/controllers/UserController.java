package cz.muni.fi.pa165.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.muni.fi.pa165.facade.UserFacade;
import javax.inject.Inject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ondrej Klein
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Inject
    private UserFacade userFacade;
    
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userFacade.getAllUsers());
        return "user/list";
    }
}
