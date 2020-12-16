package sg.edu.iss.ims.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.edu.iss.ims.model.Alert;

import java.util.List;

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
    public String createUser(User user, RedirectAttributes redirAttr) {
        if (uService.readUser(user.getUsername()) == null) {
            user.setPassword(uService.encode(user.getPassword()));
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

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        User oldUser = uService.readUser(id);
        User newUser = new User();
        model.addAttribute("oldUser", oldUser);
        model.addAttribute("newUser", newUser);
        return "manage-user/edit";
    }

    @PostMapping("/edit")
    public String editUser(Model model, User user, RedirectAttributes redirAttr) {
        User currentUser = uService.readUser(user.getId());
        if (user.getUsername() == "" && user.getPassword() == "" && user.getRole() == currentUser.getRole()) {
            redirAttr.addFlashAttribute("alert",
                    new Alert("primary", "User (" + currentUser.getUsername() +
                            ") not updated as no changes were detected."));
            return "redirect:/user/edit/" + currentUser.getId();
        } else {
            if (user.getUsername() != "") {
                currentUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != "") {
                currentUser.setPassword(uService.encode(user.getPassword()));
            }
            currentUser.setRole(user.getRole());
            uService.updateUser(currentUser);
            redirAttr.addFlashAttribute("alert",
                    new Alert("primary", "Successfully updated user: " + currentUser.getUsername()));
            return "redirect:/user/view";
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
        redirAttr.addFlashAttribute("alert",
                new Alert("primary", "Successfully deleted user: " + currentUser.getUsername()));
        return "redirect:/user/view";
    }

    @GetMapping("/view")
    public String viewUsers(Model model) {
        List<User> users = uService.getAllUsers();
        model.addAttribute("users", users);
        return "manage-user/view";
    }
}
