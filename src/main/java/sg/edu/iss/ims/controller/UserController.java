package sg.edu.iss.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.model.User;
import sg.edu.iss.ims.service.UserImplementation;
import sg.edu.iss.ims.service.UserInterface;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserInterface uService;

    public UserController(UserImplementation uImp) {
        uService = uImp;
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "manage-user/create";
    }

    @PostMapping("/create")
    public String createUser(User user, RedirectAttributes redirAttr) {
        if (uService.readUser(user.getUsername()) == null) {
            uService.createUser(user);
            redirAttr.addFlashAttribute("alert",
                    new Alert("primary", "Successfully created user: " + user.getUsername()));
            return "redirect:/user/view";
        } else {
            redirAttr.addFlashAttribute("alert",
                    new Alert("primary", "Username already exists: " + user.getUsername()));
            return "redirect:/user/create";
        }
    }

    @GetMapping("/modify")
    public String modifyUser(Model model) {
        List<User> users = uService.getAllUsers();
        model.addAttribute("users", users);
        return "manage-user/modify";
    }

    @GetMapping("/view")
    public String viewUsers(Model model) {
        List<User> users = uService.getAllUsers();
        model.addAttribute("users", users);
        return "manage-user/view";
    }
}
