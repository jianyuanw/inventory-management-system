package sg.edu.iss.ims.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemService;
import sg.edu.iss.ims.item.ItemServiceImpl;
import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;
import sg.edu.iss.ims.transaction.TransactionType;

@Controller
@RequestMapping("/form/stockreturn")
public class StockReturnController {
	private TransactionService transactionService;
	private ItemService itemService;
	
	public StockReturnController(TransactionServiceImpl transactionServiceImpl, ItemServiceImpl itemServiceImpl) {
		transactionService = transactionServiceImpl;
		itemService = itemServiceImpl;
	}

	@GetMapping
	public String generateReturnForm(Model model, @ModelAttribute Item item) {
		model.addAttribute("inventory", itemService.listAvailable());
		return "form/stockreturn";
	}
	
	@PostMapping
	public String saveReturnForm(Model model, @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirAttr) {
		model.addAttribute("inventory", itemService.listAvailable());
		if (bindingResult.hasErrors()) {
			return "form/stockreturn";
		} else if (item.getUnits() > itemService.findItemById(item.getId()).getUnits()) {
			redirAttr.addFlashAttribute("alert", new Alert("warning", "Insufficient units of items requested"));	
			return "redirect:/form/stockreturn";
		} else {
			Item dbItem = itemService.findItemById(item.getId());
			transactionService.changeStock(new Transaction(item, -(item.getUnits()), TransactionType.RETURN_STOCK), dbItem);
			redirAttr.addFlashAttribute("alert", new Alert("success", "Item return successful!"));
			return "redirect:/";
		}
	}
}
