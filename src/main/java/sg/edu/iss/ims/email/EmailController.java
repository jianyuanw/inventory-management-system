package sg.edu.iss.ims.email;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;

@Controller
public class EmailController {
	private final EmailService eService;
	
	public EmailController(EmailServiceImpl eServiceImpl) {
		eService = eServiceImpl;
	}
	
	// Note this controller is for testing, it will be automated in the final build
	@GetMapping("/email")
	public String email(Model model) {
		model.addAttribute("email", new Email());
		return "email";
	}
	
	@PostMapping("/email")
	public String loginAccount(Model model, Email email, RedirectAttributes redirAttr) {
		try {
			eService.sendSimpleMessage(new String[] {"caiylderek@gmail.com"}, email.getSubject(), email.getMessage());
			redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully emailed to " + email.getRecipient()));
		} catch (MailException exception) {
			redirAttr.addFlashAttribute("alert", new Alert("danger", "MailException raised, email failed."));
        } 
		return "redirect:/";
	}	
}
