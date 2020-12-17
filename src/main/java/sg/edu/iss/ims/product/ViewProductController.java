package sg.edu.iss.ims.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.edu.iss.ims.item.Item;

@Controller
@RequestMapping("/ViewProduct")
public class ViewProductController {

	private ViewProductService viewService;

	public ViewProductController(ViewProductImpl viewProductImpl) {
		viewService = viewProductImpl;
	}

	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Item> items = viewService.findAllItems();
		model.addAttribute("items", items);
		return "ViewProduct";
	}

	//show product detail
	@RequestMapping (value ="/showdetail/{id}")
	public String showdetail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("prod", viewService.findProductById(id));
		return "DetailProduct";
	}
}





