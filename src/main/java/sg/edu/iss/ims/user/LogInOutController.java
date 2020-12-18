package sg.edu.iss.ims.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;

@Controller
public class LogInOutController {

	private final UserService uService;

	public LogInOutController(UserServiceImpl uImp) {
		uService = uImp;
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("imglink", "img/mitsubishi_air_filter.jpg");
		return "login";
	}

	@GetMapping("/login/error")
	public String loginError(Model model, RedirectAttributes redirAttr) {
		model.addAttribute("user", new User());
		redirAttr.addFlashAttribute("alert", new Alert("primary", "Incorrect username/password"));
		return "redirect:/login";
	}

	@GetMapping("/login/success")
	public String loginSuccess(RedirectAttributes redirAttr) {
		redirAttr.addFlashAttribute("alert", new Alert("primary", "Successfully logged in!"));
		return "redirect:/";
	}

//	@PostMapping("/login")
//	public String loginAccount(Model model, User user, HttpServletRequest request, RedirectAttributes redirAttr) {
//		// Note, user is not currently being passed to this action properly
//		HttpSession session = request.getSession();
//		if (session.getAttribute("user") == null && uService.authenticate(user)) {
//			session.setAttribute("user", user);
//			redirAttr.addFlashAttribute("alert", new Alert("primary", "Successfully logged in!"));
//		}
//		return "redirect:/";
//	}

//	@GetMapping("/logout")
//	public String logoutAccount(HttpSession session, RedirectAttributes redirAttr) {
//		session.invalidate();
//		redirAttr.addFlashAttribute("alert", new Alert("primary", "Successfully logged out!"));
//		return "redirect:/";
//	}

	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}

	@GetMapping("/logout/success")
	public String logoutSuccess(RedirectAttributes redirAttr) {
		redirAttr.addFlashAttribute("alert", new Alert("primary", "Successfully logged out!"));
		return "redirect:/";
	}
}
