package sg.edu.iss.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.model.User;
import sg.edu.iss.ims.service.UserImplementation;
import sg.edu.iss.ims.service.UserInterface;

@Controller
public class UserController {

	private final UserInterface uService;
	
	public UserController(UserImplementation uImp) {
		uService = uImp;
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginAccount(Model model, User user, HttpServletRequest request, RedirectAttributes redirAttr) {
		// Note, user is not currently being passed to this action properly
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null && uService.authenticate(user)) {
			session.setAttribute("user", user);
			redirAttr.addFlashAttribute("alert", new Alert("primary", "Successfully logged in!"));
		}
		return "redirect:/";
	}	
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String createAccount(Model model, User user, HttpServletRequest request, RedirectAttributes redirAttr) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null && uService.readUser(user.getUsername()) == null) {
			uService.createUser(user);
			session.setAttribute("user", user);
			redirAttr.addFlashAttribute("alert", new Alert("primary", "Successfully registered!"));
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logoutAccount(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
