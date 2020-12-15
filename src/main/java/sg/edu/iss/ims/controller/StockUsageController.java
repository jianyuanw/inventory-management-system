package sg.edu.iss.ims.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.ItemList;
import sg.edu.iss.ims.model.Job;
import sg.edu.iss.ims.service.ItemService;
import sg.edu.iss.ims.service.ItemServiceImpl;
import sg.edu.iss.ims.service.JobService;
import sg.edu.iss.ims.service.JobServiceImpl;
import sg.edu.iss.ims.service.ProductService;
import sg.edu.iss.ims.service.ProductServiceImpl;

@Controller
public class StockUsageController {
	private ProductService productService;
	private JobService jobService;
	private ItemService itemService;
	
	public StockUsageController(ProductServiceImpl productServiceImpl, JobServiceImpl jobServiceImpl, ItemServiceImpl itemServiceImpl) {
		productService = productServiceImpl;
		jobService = jobServiceImpl;
		itemService = itemServiceImpl;
	}

	@GetMapping("/form/stockusage")
	public String generateSupplierForm(Model model, @ModelAttribute Job job, ItemList itemList) {
		itemList.addProduct();
		model.addAttribute("itemList", itemList);
		model.addAttribute("inventory", itemService.list());
		return "form/stockusage";
	}
	
	@PostMapping("/form/stockusage")
	public String saveSupplierForm(Job job, ItemList itemList) {
		jobService.createJob(job, itemList);
		return "index";
	}
	
	@RequestMapping(value="/form/stockusage", params={"addRow"})
	public String addRow(Model model, Job job, ItemList itemList) {
	    itemList.addProduct();
	    model.addAttribute("inventory", itemService.list());
	    return "form/stockusage";
	}

	@RequestMapping(value="/form/stockusage", params={"removeRow"})
	public String removeRow(Model model, Job job, ItemList itemList, HttpServletRequest req) {
	    Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    itemList.removeProduct(rowId);
	    model.addAttribute("inventory", itemService.list());
	    return "form/stockusage";
	}	
}
