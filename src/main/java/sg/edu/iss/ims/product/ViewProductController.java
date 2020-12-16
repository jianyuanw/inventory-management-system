package sg.edu.iss.ims.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.item.Item;

@Controller
@RequestMapping("/ViewProduct")
public class ViewProductController {

	@Autowired
	public void setViewProduct(ViewProductService view) {
		this.viewservice = view;
	}

	@Autowired
	private ViewProductService viewservice;

	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Product> prod = viewservice.list();
		model.addAttribute("prod", prod);
		return "ViewProduct";
	}


	//show product detail
	@RequestMapping (value ="/showdetail/{id}")
	public String showdetail (Model model, @PathVariable("id")Long Id){
		model.addAttribute("prod", viewservice.findProductById(Id));
		return "DetailProduct";
	}
}





