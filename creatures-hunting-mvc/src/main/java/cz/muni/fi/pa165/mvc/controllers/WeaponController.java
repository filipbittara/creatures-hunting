package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.AmmunitionType;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @Autowired
    private CreatureFacade creatureFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    HttpSession session;

    @ModelAttribute("creatures")
    public List<CreatureDTO> creatures() {
        return creatureFacade.getAllCreatures();
    }

    @RequestMapping(value = {"/list/filter"}, method = RequestMethod.GET)
    public String emptyFilter(RedirectAttributes attributes) {
        return "redirect:/weapon/list";
    }

    @RequestMapping(value = {"/list/filter/{word}"}, method = RequestMethod.GET)
    public String filteredList(Model model, @PathVariable String word) {
        List<WeaponDTO> weapons = weaponFacade.getAllWeapons();
        List<WeaponDTO> filteredWeapons = new ArrayList<>();
        String creatures;
        Map<Long, String> creatureWeapon = new HashMap<Long, String>();

        if (word != null) {
            for (WeaponDTO w : weapons) {
                if ((w.getGunReach() != null && w.getGunReach().toString().contains(word))
                        || (w.getAmmunition() != null && w.getAmmunition().toString().toLowerCase().contains(word.toLowerCase()))
                        || (w.getId().toString().contains(word))
                        || (w.getName() != null && w.getName().toLowerCase().contains(word.toLowerCase()))) {
                    filteredWeapons.add(w);
                }
            }
            model.addAttribute("filter", word);
        } else {
            filteredWeapons = weapons;
        }
        model.addAttribute("weapons", filteredWeapons);

        for (WeaponDTO weapon : filteredWeapons) {
            creatures = "";
            for (CreatureDTO creature : creatureFacade.getCreaturesByWeapon(weapon.getName())) {
                creatures += creature.getName() + ", ";
            }
            if ("".equals(creatures)) {
                creatures = "no creature";
            } else {
                creatures = creatures.substring(0, creatures.length() - 2);
            }
            creatureWeapon.put(weapon.getId(), creatures);
        }
        model.addAttribute("creatureWeapon", creatureWeapon);
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "/weapon/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return filteredList(model, null);
    }

    @RequestMapping(value = "/addCreature/{cid}/to/{id}", method = RequestMethod.GET)
    public String addCreature(@PathVariable long cid, @PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(cid);
        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        weaponFacade.assignCreature(weapon, creature);
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" could be harmed by weapon \"" + weapon.getName() + "\".");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

    @RequestMapping(value = "/removeCreature/{cid}/from/{id}", method = RequestMethod.GET)
    public String removeCreature(@PathVariable long cid, @PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(cid);
        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        weaponFacade.removeCreature(weapon, creature);
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" could not be harmed by weapon \"" + weapon.getName() + "\".");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("weapon", weaponFacade.getWeaponById(id));
        return ("weapon/detail");
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        weaponFacade.removeWeapon(weapon);
        redirectAttributes.addFlashAttribute("alert_success", "Weapon \"" + weapon.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

    @RequestMapping("/weaponImage/{id}")
    public void weaponImage(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        WeaponDTO weaponDTO = weaponFacade.getWeaponById(id);
        byte[] image = weaponDTO.getImage();
        if (image == null) {
            response.sendRedirect(request.getContextPath() + "/no-image.svg");
        } else {
            response.setContentType(weaponDTO.getImageMimeType());
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }

    @ModelAttribute("types")
    public AmmunitionType[] types() {
        return AmmunitionType.values();
    }

    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        model.addAttribute("weaponCreate", new WeaponDTO());
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "weapon/new";
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("weaponCreate") WeaponDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                //log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "weapon/new";
        }

        MultipartFile image = formBean.getMultipartImage();

        if (image.getSize() > 0) {
            try {
                formBean.validateImage();
            } catch (RuntimeException re) {
                bindingResult.reject(re.getMessage());
                return "weapon/new";
            }
        }
        //create product
        Long id = weaponFacade.addWeapon(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Weapon " + formBean.getName() + " was created");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

    @RequestMapping(value = {"/admin/update/{id}"}, method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {

        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        model.addAttribute("weaponUpdate", weapon);
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "weapon/edit";
    }

    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.POST)
    public String update(
            @Valid @ModelAttribute("weaponUpdate") WeaponDTO formBean,
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
            return "weapon/edit";
        }

        MultipartFile image = formBean.getMultipartImage();

        if (image.getSize() > 0) {
            try {
                formBean.validateImage();
            } catch (RuntimeException re) {
                bindingResult.reject(re.getMessage());
                return "weapon/edit";
            }
        }
        // if no image was assigned, keep the previous image
        if (formBean.getImage() == null) {
            formBean.setImage(weaponFacade.getWeaponById(formBean.getId()).getImage());
        }
        weaponFacade.updateWeapon(formBean);
        redirectAttributes.addFlashAttribute("alert_success", "Weapon " + formBean.getName() + " updated");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

}
