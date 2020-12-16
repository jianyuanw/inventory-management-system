package sg.edu.iss.ims.item;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.job.Job;
import sg.edu.iss.ims.job.JobService;
import sg.edu.iss.ims.job.JobServiceImpl;
import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.product.ProductService;
import sg.edu.iss.ims.product.ProductServiceImpl;

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
		model.addAttribute("inventory", itemService.listAvailable());
		return "form/stockusage";
	}
	
	@PostMapping("/form/stockusage")
	public String saveSupplierForm(Model model, Job job, ItemList itemList, RedirectAttributes redirAttr) {
		List<Integer> errors = itemService.checkInventory(itemList.getList());
		if (errors != null) {
			redirAttr.addFlashAttribute("alert", new Alert("warning", "Insufficient units of items requested"));	
			return "redirect:/form/stockusage";
		} else {
			jobService.createJob(job, itemList);
			redirAttr.addFlashAttribute("alert", new Alert("primary", "Job successfully added!"));			
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/form/stockusage", params={"addRow"})
	public String addRow(Model model, Job job, ItemList itemList) {
	    itemList.addProduct();
	    model.addAttribute("inventory", itemService.listAvailable());
	    return "form/stockusage";
	}

	@RequestMapping(value="/form/stockusage", params={"removeRow"})
	public String removeRow(Model model, Job job, ItemList itemList, HttpServletRequest req) {
	    Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    itemList.removeProduct(rowId);
	    model.addAttribute("inventory", itemService.listAvailable());
	    return "form/stockusage";
	}	
}
