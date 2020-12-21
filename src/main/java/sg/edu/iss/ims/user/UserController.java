package sg.edu.iss.ims.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService uService;

    public UserController(UserServiceImpl uImp) {
        uService = uImp;
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "manage-user/create";
    }

    @PostMapping("/create")
    public String createUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirAttr) {
    	if (uService.readUser(user.getUsername()) != null) {
    		bindingResult.rejectValue("username", "error.user", "Username " + user.getUsername() + " already exists");
    	}
    	if (bindingResult.hasErrors()) {
    		return "manage-user/create";
    	} else {
            user.setPassword(uService.encode(user.getPassword()));
            uService.createUser(user);
            redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully created user: " + user.getUsername()));
            return "redirect:/user/modify";
        }
    }

    @GetMapping("/modify")
    public String modifyUser(Model model) {
        List<User> users = uService.getAllUsers();
        model.addAttribute("users", users);
        return "manage-user/modify";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        User oldUser = uService.readUser(id);
        User newUser = new User();
        model.addAttribute("oldUser", oldUser);
        model.addAttribute("user", newUser);
        return "manage-user/edit";
    }

    @PostMapping("/edit")
    public String editUser(Model model, User user, BindingResult bindingResult, RedirectAttributes redirAttr) {
        User currentUser = uService.readUser(user.getId());
        
        if (uService.noChange(user, currentUser)) {
            redirAttr.addFlashAttribute("alert", new Alert("info",
                    "User (" + currentUser.getUsername() + ") not updated as no changes were detected."));
            return "redirect:/user/edit/" + currentUser.getId();
        } else {
        	uService.validateUser(user, currentUser, bindingResult);
        }
        
        if (bindingResult.hasErrors()) {
        	model.addAttribute("oldUser", currentUser);
        	return "manage-user/edit";
        } else {
        	uService.updateUser(user, currentUser);
            redirAttr.addFlashAttribute("alert",
                    new Alert("success", "Successfully updated user: " + currentUser.getUsername()));
            return "redirect:/user/modify";        	
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        User user = uService.readUser(id);
        model.addAttribute("user", user);
        return "manage-user/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(User user, RedirectAttributes redirAttr) {
        User currentUser = uService.readUser(user.getId());
        uService.deleteUser(currentUser);

        uService.invalidateSessions(currentUser.getUsername());

        redirAttr.addFlashAttribute("alert",
                new Alert("success", "Successfully deleted user: " + currentUser.getUsername()));
        return "redirect:/user/modify";
    }
}
