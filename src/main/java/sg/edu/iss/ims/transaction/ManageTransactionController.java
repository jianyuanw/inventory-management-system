package sg.edu.iss.ims.transaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;

@Controller
@RequestMapping("/transaction")
public class ManageTransactionController {
	
	private final TransactionService transactionService;
	
	public ManageTransactionController(TransactionServiceImpl transactionServiceImpl) {
		transactionService = transactionServiceImpl;
	}
	
	@GetMapping("/list")
	public String listTransactions(Model model) {
		model.addAttribute("transactions", transactionService.findAll());
		return "manage-transaction/modify";
	}
	
	@GetMapping("/reverse/{transactionId}")
	public String reverseTransaction(@PathVariable Long transactionId, RedirectAttributes redirAttr) {
		if (transactionService.findById(transactionId).getTransactionType() == TransactionType.RECEIVE_STOCK) {
			redirAttr.addFlashAttribute("alert", new Alert("warning", "Receive stock transactions cannot be reversed"));
		} else {
			transactionService.reverse(transactionId);
			redirAttr.addFlashAttribute("alert", new Alert("success", "Transaction successfully reversed"));			
		}
		return "redirect:/transaction/list";
	}
}
