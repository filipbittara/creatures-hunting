package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.AreaFacade;
import cz.muni.fi.pa165.facade.CreatureFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cz.muni.fi.pa165.mvc.forms.AreaCreateDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @RequestMapping(value = {"/list/filter"}, method = RequestMethod.GET)
    public String emptyFilter(RedirectAttributes attributes) {
        return "redirect:/area/list";
    }

    @RequestMapping(value = {"/list/filter/{word}"}, method = RequestMethod.GET)
    public String filteredList(Model model, @PathVariable String word) {
        List<AreaDTO> areas = areaFacade.getAllAreas();
        List<AreaDTO> filteredAreas = new ArrayList<>();
        String creatures;
        Map<Long, String> creatureAreas = new HashMap<Long, String>();
        if (word != null) {
            for (AreaDTO a : areas) {
                if ((a.getLatitude() != null && a.getLatitude().toString().contains(word))
                        || (a.getLongitude() != null && a.getLongitude().toString().contains(word))
                        || (a.getId().toString().contains(word))
                        || (a.getDescription() != null && a.getDescription().toLowerCase().contains(word.toLowerCase()))
                        || (a.getName() != null && a.getName().toLowerCase().contains(word.toLowerCase()))) {
                    filteredAreas.add(a);
                }
            }
            model.addAttribute("filter", word);
        } else {
            filteredAreas = areas;
        }
        model.addAttribute("areas", filteredAreas);
        for (AreaDTO area : filteredAreas) {
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
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "/area/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return filteredList(model, null);
    }

    @ModelAttribute("creatures")
    public List<CreatureDTO> creatures() {
        return creatureFacade.getAllCreatures();
    }

    @RequestMapping(value = "/addCreature/{cid}/to/{id}", method = RequestMethod.GET)
    public String addCreature(@PathVariable long cid, @PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        try {
            CreatureDTO creature = creatureFacade.getCreature(cid);
            AreaDTO area = areaFacade.getArea(id);       
            areaFacade.addCreatureToArea(creature.getId(), area.getId());
            redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" is in area \"" + area.getName() + "\".");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_error", "Error. Unable to assign creature with id " + cid + " to area with id " + id + ".");
        }
        redirectAttributes.addFlashAttribute("shouldBeOpen", id);
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }

    @RequestMapping(value = "/removeCreature/{cid}/from/{id}", method = RequestMethod.GET)
    public String removeCreature(@PathVariable long cid, @PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        try {
            CreatureDTO creature = creatureFacade.getCreature(cid);
            AreaDTO area = areaFacade.getArea(id);
            areaFacade.removeCreatureFromArea(creature.getId(), area.getId());
            redirectAttributes.addFlashAttribute("alert_success", "Creature \"" + creature.getName() + "\" is no longer in area \"" + area.getName() + "\".");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_error", "Error. Unable to remove creature with id " + cid + " from area with id " + id + ".");
        }
        redirectAttributes.addFlashAttribute("shouldBeOpen", id);
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        try {
            AreaDTO area = areaFacade.getArea(id);
            areaFacade.deleteArea(area);
            redirectAttributes.addFlashAttribute("alert_success", "Product \"" + area.getName() + "\" was deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_error", "Error. Unable to remove area with id " + id + ".");
        }
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }

    @RequestMapping("/areaImage/{id}")
    public void areaImage(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        AreaDTO areaDTO = areaFacade.getArea(id);
        byte[] image = areaDTO.getImage();
        if (image == null) {
            response.sendRedirect(request.getContextPath() + "/no-image.svg");
        } else {
            response.setContentType(areaDTO.getImageMimeType());
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }

    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public String newArea(Model model) {
        model.addAttribute("areaCreate", new AreaDTO());
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "area/new";
    }

    @RequestMapping(value = "/admin/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("areaCreate") AreaDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                //log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "area/new";
        }

        MultipartFile image = formBean.getMultipartImage();

        if (image.getSize() > 0) {
            try {
                formBean.validateImage();
            } catch (RuntimeException re) {
                bindingResult.reject(re.getMessage());
                return "area/new";
            }
        }
        //create product
        Long id = areaFacade.createArea(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Area " + formBean.getName() + " was created");
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }

    @RequestMapping(value = {"/admin/update/{id}"}, method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {

        AreaDTO area = areaFacade.getArea(id);
        model.addAttribute("areaUpdate", area);
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            if (userFacade.isAdmin(user)) {
                model.addAttribute("authenticatedAdmin", user.getUsername());
            } else {
                model.addAttribute("authenticatedUser", user.getUsername());
            }
        }
        return "area/edit";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof CreatureDTO) {
            binder.addValidators(new AreaCreateDTOValidator());
        }
    }

    @RequestMapping(value = "/admin/update/{id}", method = RequestMethod.POST)
    public String update(
            @Valid @ModelAttribute("areaUpdate") AreaDTO formBean,
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
            return "area/edit";
        }

        MultipartFile image = formBean.getMultipartImage();

        if (image.getSize() > 0) {
            try {
                formBean.validateImage();
            } catch (RuntimeException re) {
                bindingResult.reject(re.getMessage());
                return "area/edit";
            }
        }
        // if no image was assigned, keep the previous image
        if (formBean.getImage() == null) {
            formBean.setImage(areaFacade.getArea(formBean.getId()).getImage());
        }
        areaFacade.updateArea(formBean);
        redirectAttributes.addFlashAttribute("alert_success", "Area " + formBean.getName() + " updated");
        return "redirect:" + uriBuilder.path("/area/list").toUriString();
    }
}
