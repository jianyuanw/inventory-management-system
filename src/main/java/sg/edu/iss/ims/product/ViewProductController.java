package sg.edu.iss.ims.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.item.Item;

@Controller
@RequestMapping("/")
public class ViewProductController {

	private ViewProductService viewService;

	public ViewProductController(ViewProductImpl viewProductImpl) {
		viewService = viewProductImpl;
	}

	@GetMapping
	public String list(Model model) {
		List<Item> items = viewService.findAllItems();
		model.addAttribute("items", items);
		return "catalog/catalog";
	}

	@GetMapping("/product/details/{id}")
	public String showdetail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("product", viewService.findProductById(id));
		return "catalog/productdetails";
	}
}





