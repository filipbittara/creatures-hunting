package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    private HttpSession session;
    
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CreatureFacade creatureFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        List<AreaDTO> areas = areaFacade.getAllAreas();
        model.addAttribute("areas", areas);
        String creatures;
        Map<Long, String> creatureAreas = new HashMap<Long, String>();
        for (AreaDTO area : areas) {
            creatures = "";
            for (CreatureDTO creature : creatureFacade.getCreaturesByArea(area.getName())) {
                creatures += creature.getName() + ", ";
            }
            if ("".equals(creatures)) {
                creatures = "no creature";
            } else {
                creatures = creatures.substring(0, creatures.length() - 2);
            }
            creatureAreas.put(area.getId(), creatures);
        }
        model.addAttribute("creatureAreas", creatureAreas);
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getEmail());
            } else {
                model.addAttribute("authenticatedUser", user.getEmail());
            }
        }
        return "/area/list";
    }

    @ModelAttribute("creatures")
    public List<CreatureDTO> creatures() {
        return creatureFacade.getAllCreatures();
    }

    @RequestMapping(value = "/addCreature/{cid}/to/{id}", method = RequestMethod.GET)
    public String addCreature(@PathVariable long cid, @PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(cid);
        AreaDTO area = areaFacade.getArea(id);
        areaFacade.addCreatureToArea(creature.getId(), area.getId());
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" is in area \"" + area.getName() + "\".");
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("area", areaFacade.getArea(id));
        return ("area/detail");
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        AreaDTO area = areaFacade.getArea(id);
        areaFacade.deleteArea(area);
        redirectAttributes.addFlashAttribute("alert_success", "Product \"" + area.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }
}
