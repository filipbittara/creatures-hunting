package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Ondrej Klein
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserFacade userFacade;
    
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userFacade.getAllUsers());
        return "user/list";
    }

    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("user", userFacade.findUserById(id));
        return ("user/detail");
    }

//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
//    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
//        UserDTO user = userFacade.findUserById(id);
//        userFacade.deleteUser(id);
//        redirectAttributes.addFlashAttribute("alert_success", "Product \"" + user.getUsername() + "\" was deleted.");
//        return "redirect:" + uriBuilder.path("/user/list").toUriString();
//    }

    @RequestMapping("/userImage/{id}")
    public void userImage(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDTO userDTO = userFacade.findUserById(id);
        byte[] image = userDTO.getImage();
        if (image == null) {
            response.sendRedirect(request.getContextPath() + "/no-image.svg");
        } else {
            response.setContentType(userDTO.getImageMimeType());
            ServletOutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
        }
    }
}
