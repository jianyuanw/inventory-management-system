package sg.edu.iss.ims.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report/usage")
public class UsageReportController {
	private final ItemService itemService;
	
	public UsageReportController(ItemServiceImpl itemServiceImpl) {
		itemService = itemServiceImpl;
	}
	
	@GetMapping("/query")
	public String generateUsageQueryForm(Model model) {
		model.addAttribute("inventory", itemService.list());
		return "report/usagequery";
	}
	
	@PostMapping("/output")
	public String generateUsageReport(Model model, @RequestParam String partNumber, @RequestParam String dateStart, @RequestParam String dateEnd) {
		model.addAttribute("partNumber", partNumber);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);

		return "test";
	}
	
}
