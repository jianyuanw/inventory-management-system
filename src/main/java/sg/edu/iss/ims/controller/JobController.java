package sg.edu.iss.ims.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.model.Job;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.ItemList;
import sg.edu.iss.ims.service.ProductService;
import sg.edu.iss.ims.service.ProductServiceImpl;

@Controller
public class JobController {
	private ProductService productService;
	
	public JobController(ProductServiceImpl productServiceImpl) {
		productService = productServiceImpl;
	}

	@GetMapping("/forms/stockusage")
	public String generateSupplierForm(Model model, @ModelAttribute Job job, ItemList itemList) {
		itemList.addProduct();
		model.addAttribute("itemList", itemList);
		model.addAttribute("products", productService.list());
		return "forms/stockusage";
	}
	
	@PostMapping("/forms/stockusage")
	public String saveSupplierForm(Job job, ItemList itemList) {
		return "testform";
	}
	
	@RequestMapping(value="/forms/stockusage", params={"addRow"})
	public String addRow(Model model, Job job, ItemList itemList) {
	    itemList.addProduct();
	    model.addAttribute("products", productService.list());
	    return "forms/stockusage";
	}

	@RequestMapping(value="/forms/stockusage", params={"removeRow"})
	public String removeRow(Model model, Job job, ItemList itemList, HttpServletRequest req) {
	    Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    itemList.removeProduct(rowId);
	    model.addAttribute("products", productService.list());
	    return "forms/stockusage";
	}	
}
