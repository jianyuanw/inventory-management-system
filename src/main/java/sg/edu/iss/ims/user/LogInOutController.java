package sg.edu.iss.ims.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;

@Controller
public class LogInOutController {

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping("/login/error")
	public String loginError(Model model, RedirectAttributes redirAttr) {
		model.addAttribute("user", new User());
		redirAttr.addFlashAttribute("alert", new Alert("warning", "Incorrect username/password"));
		return "redirect:/login";
	}

	@GetMapping("/login/success")
	public String loginSuccess(RedirectAttributes redirAttr) {
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully logged in!"));
		return "redirect:/";
	}

	@GetMapping("/login/expired")
	public String loginExpired(RedirectAttributes redirAttr) {
		redirAttr.addFlashAttribute("alert", new Alert("warning", "Login has expired, please log in again to continue"));
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}

	@GetMapping("/logout/success")
	public String logoutSuccess(RedirectAttributes redirAttr) {
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully logged out!"));
		return "redirect:/login";
	}
	
	
}
