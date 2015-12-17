package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.mvc.forms.CreatureDTOValidator;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/creature")
public class CreatureController {

    @Autowired
    private CreatureFacade creatureFacade;
    
    @Autowired
    private WeaponFacade weaponFacade;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("creatures", creatureFacade.getAllCreatures());
        return "/creature/list";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        model.addAttribute("creatureCreate", new CreatureDTO());
        return "creature/new";
    }
    
    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model ) {
        
        CreatureDTO creature = creatureFacade.getCreature(id);
        model.addAttribute("creatureUpdate", creature);
        return "creature/edit";
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof CreatureDTO) {
            binder.addValidators(new CreatureDTOValidator());
        }
    }
    
    @ModelAttribute("types")
    public CreatureType[] types() {
        return CreatureType.values();
    }
    
    @ModelAttribute("weapons")
    public List<WeaponDTO> weapons() {
        return weaponFacade.getAllWeapons();
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("creatureCreate") CreatureDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                //log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "creature/new";
        }
        //create product
        Long id = creatureFacade.createCreature(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Creature " + formBean.getName() + " was created");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(
            @Valid @ModelAttribute("creatureUpdate") CreatureDTO formBean,
            BindingResult bindingResult,
            @PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
             for (ObjectError ge : bindingResult.getGlobalErrors()) {
                //log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "creature/edit";
        }
        
        creatureFacade.updateCreature(formBean);
        redirectAttributes.addFlashAttribute("alert_success", "Creature " + formBean.getName()+ " updated");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(id);
        creatureFacade.deleteCreature(id);
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }
    
    @RequestMapping(value = "/addWeapon/{wid}/to/{id}", method = RequestMethod.GET) 
    public String addWeapon(@PathVariable long id, @PathVariable long wid, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(id);
        WeaponDTO weapon = weaponFacade.getWeaponById(wid);
        weaponFacade.assignCreature(weapon, creature);
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" could be harmed by weapon \""+ weapon.getName() + "\".");
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

