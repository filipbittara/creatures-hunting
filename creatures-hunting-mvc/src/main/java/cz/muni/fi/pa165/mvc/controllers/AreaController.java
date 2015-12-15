package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author David Kizivat
 */
@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaFacade areaFacade;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("areas", areaFacade.getAllAreas());
        return "/area/list";
    }

    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("area", areaFacade.getArea(id));
        return ("area/detail");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        AreaDTO area = areaFacade.getArea(id);
        areaFacade.deleteArea(area);
        redirectAttributes.addFlashAttribute("alert_success", "Product \"" + area.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }
}

