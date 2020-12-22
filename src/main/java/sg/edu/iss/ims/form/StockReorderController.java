package sg.edu.iss.ims.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemService;
import sg.edu.iss.ims.item.ItemServiceImpl;

@Controller
@RequestMapping("/form/stockreorder")
public class StockReorderController {

	private final ItemService itemService;
	
	public StockReorderController(ItemServiceImpl itemServiceImpl) {
		itemService = itemServiceImpl;
	}
	
	@GetMapping
	public String generateReorderForm(Model model, @ModelAttribute Item item) {
		model.addAttribute("inventory", itemService.listAvailable());
		return "form/stockreorder";
	}	
	
}
