package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.facade.WeaponFacade;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

