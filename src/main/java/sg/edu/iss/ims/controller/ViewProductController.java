package sg.edu.iss.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.service.ViewProductService;

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

}
