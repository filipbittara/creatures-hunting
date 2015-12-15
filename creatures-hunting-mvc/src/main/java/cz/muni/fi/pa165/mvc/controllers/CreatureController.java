package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.facade.CreatureFacade;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.persistence.entity.Creature;
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
@RequestMapping("/creature")
public class CreatureController {

    @Autowired
    private CreatureFacade creatureFacade;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("creatures", creatureFacade.getAllCreatures());
        return "/creature/list";
    }

    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("creature", creatureFacade.getCreature(id));
        return ("creature/detail");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(id);
        creatureFacade.deleteCreature(id);
        redirectAttributes.addFlashAttribute("alert_success", "Product \"" + creature.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }

    @RequestMapping("/creatureImage/{id}")
    public void creatureImage(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreatureDTO creatureDTO = creatureFacade.getCreature(id);
        byte[] image = creatureDTO.getImage();
        if (image == null) {
            response.sendRedirect(request.getContextPath() + "/no-image.svg");
        } else {
            response.setContentType(creatureDTO.getImageMimeType());
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }
}

