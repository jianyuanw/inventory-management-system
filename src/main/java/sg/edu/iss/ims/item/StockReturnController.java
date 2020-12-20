package sg.edu.iss.ims.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;
import sg.edu.iss.ims.transaction.TransactionType;

@Controller
public class StockReturnController {
	private TransactionService transactionService;
	private ItemService itemService;
	
	public StockReturnController(TransactionServiceImpl transactionServiceImpl, ItemServiceImpl itemServiceImpl) {
		transactionService = transactionServiceImpl;
		itemService = itemServiceImpl;
	}

	@GetMapping("/form/stockreturn")
	public String generateReturnForm(Model model, @ModelAttribute Item item) {
		model.addAttribute("inventory", itemService.listAvailable());
		return "form/stockreturn";
	}
	
	@PostMapping("/form/stockreturn")
	public String saveReturnForm(Model model, @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirAttr) {
		model.addAttribute("inventory", itemService.listAvailable());
		if (bindingResult.hasErrors()) {
			return "form/stockreturn";
		} else if (item.getUnits() > itemService.findItemById(item.getId()).getUnits()) {
			redirAttr.addFlashAttribute("alert", new Alert("warning", "Insufficient units of items requested"));	
			return "redirect:/form/stockreturn";
		} else {
			Item dbItem = itemService.findItemById(item.getId());
			if (transactionService.save(new Transaction(item, -(item.getUnits()), TransactionType.RETURN_STOCK))) {
				dbItem.setUnits(dbItem.getUnits() - item.getUnits());
			}				
			redirAttr.addFlashAttribute("alert", new Alert("primary", "Item return successful!"));
			return "index";
		}
	}
}
