package sg.edu.iss.ims.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;

@Controller
@RequestMapping("/report/usage")
public class UsageReportController {
	private final ItemService itemService;
	private final TransactionService transactionService;
	
	public UsageReportController(ItemServiceImpl itemServiceImpl, TransactionServiceImpl transactionServiceImpl) {
		itemService = itemServiceImpl;
		transactionService = transactionServiceImpl;
	}
	
	@GetMapping("/query")
	public String generateUsageQueryForm(Model model) {
		model.addAttribute("inventory", itemService.list());
		return "report/usagequery";
	}
	
	@PostMapping("/output")
	public String generateUsageReport(Model model, @RequestParam Long itemId, @RequestParam String dateStart, @RequestParam String dateEnd) {
		model.addAttribute("partNumber", itemService.findItemById(itemId).getProduct().getPartNumber());
		model.addAttribute("transactionList", transactionService.parseUsageReportQuery(itemId, dateStart, dateEnd));

		return "report/usageoutput";
	}
	
}
