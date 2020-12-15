package sg.edu.iss.ims.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.ims.model.ItemList;
import sg.edu.iss.ims.model.Job;
import sg.edu.iss.ims.service.ItemService;
import sg.edu.iss.ims.service.ItemServiceImpl;

@Controller
public class UsageReportController {
	private final ItemService itemService;
	
	public UsageReportController(ItemServiceImpl itemServiceImpl) {
		itemService = itemServiceImpl;
	}
	
	@GetMapping("/report/usage")
	public String generateUsageQueryForm(Model model) {
		model.addAttribute("inventory", itemService.list());
		return "report/usagequery";
	}
	
	@PostMapping("/report/usage")
	public String generateUsageReport(Model model, @RequestParam String partNumber, @RequestParam String dateStart, @RequestParam String dateEnd) {
		model.addAttribute("partNumber", partNumber);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		return "testform";
	}
	
}
