package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import cz.muni.fi.pa165.mvc.forms.CreatureDTOValidator;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.persistence.entity.Creature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    
    @Autowired
    private AreaFacade areaFacade;
    
    @Autowired
    private UserFacade userFacade;
    
    @Autowired 
    private HttpSession session;

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        List<CreatureDTO> creatures = creatureFacade.getAllCreatures();
        Map<Long, String> creatureAreas = new HashMap<Long, String>();
        Map<Long, String> creatureWeapons = new HashMap<Long, String>();
        
        model.addAttribute("creatures", creatures);
        String areas;
        for(CreatureDTO creature : creatures) {           
            areas = "";
            for(AreaDTO area : areaFacade.getAreasForCreature(creature)) {
                areas += area.getName() + ", ";
            }
            if("".equals(areas)) {
                areas = "nowhere";
            } else {
                areas = areas.substring(0, areas.length() - 2);
            }
            creatureAreas.put(creature.getId(),areas);
        }
        String weapons;
        for(CreatureDTO creature : creatures) {           
            weapons = "";
            for(WeaponDTO weapon : weaponFacade.getWeaponsByCreature(creature)) {
                weapons += weapon.getName() + ", ";
            }
            if("".equals(weapons)) {
                 weapons = "nothing";
            } else {
                weapons = weapons.substring(0, weapons.length() - 2);
            }
            creatureWeapons.put(creature.getId(),weapons);
        }

            model.addAttribute("creatureAreas", creatureAreas);
            model.addAttribute("creatureWeapons", creatureWeapons);
            UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
            if (user != null) {
                if (userFacade.isAdmin(user)) {
                    model.addAttribute("authenticatedAdmin", user.getUsername());
                } else {
                    model.addAttribute("authenticatedUser", user.getUsername());
                }   
            }
        return "/creature/list";
    }
    
    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        model.addAttribute("creatureCreate", new CreatureDTO());
        return "creature/new";
    }
    
    @RequestMapping(value = {"/admin/update/{id}"}, method = RequestMethod.GET)
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
    
    @ModelAttribute("areas")
    public List<AreaDTO> areas() {
        return areaFacade.getAllAreas();
    }
    
    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("creatureCreate") CreatureDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, 
                         @RequestParam(value = "multipartImage", required = false) MultipartFile image) {
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

        if (!image.isEmpty()) {
            try {
                validateImage(image);
            } catch (RuntimeException re) {
                bindingResult.reject(re.getMessage());
                return "creature/new";
            }
            
        }
        try {
            formBean.setImage(image.getBytes());
        } catch (IOException e) {
            bindingResult.reject(e.getMessage());
            return "creature/new";
        }
        //create product
        Long id = creatureFacade.createCreature(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Creature " + formBean.getName() + " was created");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }
    
    private void validateImage(MultipartFile image) {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new RuntimeException("Only JPG images are accepted");
        }
    }
    
    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.POST)
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

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.POST)
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
    
    @RequestMapping(value = "/removeWeapon/{wid}/from/{id}", method = RequestMethod.GET) 
    public String removeWeapon(@PathVariable long id, @PathVariable long wid, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(id);
        WeaponDTO weapon = weaponFacade.getWeaponById(wid);
        weaponFacade.removeCreature(weapon, creature);
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" could not be harmed by weapon \""+ weapon.getName() + "\".");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }

    @RequestMapping(value = "/addArea/{aid}/to/{id}", method = RequestMethod.GET) 
    public String addArea(@PathVariable long id, @PathVariable long aid, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(id);
        AreaDTO area = areaFacade.getArea(aid);
        areaFacade.addCreatureToArea(creature.getId(), area.getId());
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" is in area \""+ area.getName() + "\".");
        return "redirect:" + uriBuilder.path("/creature/list").toUriString();
    }
    
    @RequestMapping(value = "/removeArea/{aid}/from/{id}", method = RequestMethod.GET) 
    public String removeArea(@PathVariable long id, @PathVariable long aid, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(id);
        AreaDTO area = areaFacade.getArea(aid);
        areaFacade.removeCreatureFromArea(creature.getId(), area.getId());
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" is no longer in area \""+ area.getName() + "\".");
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

