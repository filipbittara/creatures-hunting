package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.facade.WeaponFacade;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<WeaponDTO> weapons = weaponFacade.getAllWeapons();
        model.addAttribute("weapons", weapons);
        String creatures;
        Map<Long, String> creatureWeapon = new HashMap<Long, String>();
        for (WeaponDTO weapon : weapons) {
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
                model.addAttribute("authenticatedAdmin", user.getEmail());
            } else {
                model.addAttribute("authenticatedUser", user.getEmail());
            }
        }
        return "/weapon/list";
    }

    @RequestMapping(value = "/addCreature/{cid}/to/{id}", method = RequestMethod.GET)
    public String addCreature(@PathVariable long cid, @PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CreatureDTO creature = creatureFacade.getCreature(cid);
        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        weaponFacade.assignCreature(weapon, creature);
        redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" could be harmed by weapon \"" + weapon.getName() + "\".");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("weapon", weaponFacade.getWeaponById(id));
        return ("weapon/detail");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
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

}
