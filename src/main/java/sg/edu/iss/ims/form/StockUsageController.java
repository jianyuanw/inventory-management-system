package sg.edu.iss.ims.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.item.ItemList;
import sg.edu.iss.ims.item.ItemService;
import sg.edu.iss.ims.item.ItemServiceImpl;
import sg.edu.iss.ims.job.Job;
import sg.edu.iss.ims.job.JobService;
import sg.edu.iss.ims.job.JobServiceImpl;
import sg.edu.iss.ims.model.Alert;

@Controller
@RequestMapping("/form/stockusage")
public class StockUsageController {
	private JobService jobService;
	private ItemService itemService;
	
	public StockUsageController(JobServiceImpl jobServiceImpl, ItemServiceImpl itemServiceImpl) {
		jobService = jobServiceImpl;
		itemService = itemServiceImpl;
	}

	@GetMapping
	public String generateSupplierForm(Model model, @ModelAttribute Job job, ItemList itemList) {
		itemList.addProduct();
		model.addAttribute("itemList", itemList);
		model.addAttribute("inventory", itemService.listAvailable());
		return "form/stockusage";
	}
	
	@PostMapping
	public String saveSupplierForm(Model model, @Valid @ModelAttribute("job") Job job, BindingResult bindingResult, ItemList itemList, RedirectAttributes redirAttr) {
		itemList.compactList();
		List<Integer> errors = itemService.checkInventory(itemList.getList());
		model.addAttribute("inventory", itemService.listAvailable());
		if (bindingResult.hasErrors()) {
			return "form/stockusage";
		} else if (errors != null) {
			redirAttr.addFlashAttribute("alert", new Alert("warning", "Insufficient units of items requested"));	
			return "redirect:/form/stockusage";
		} else {
			jobService.createJob(job, itemList);
			redirAttr.addFlashAttribute("alert", new Alert("success", "Job successfully added!"));			
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(params={"addRow"})
	public String addRow(Model model, Job job, ItemList itemList) {
	    itemList.addProduct();
	    model.addAttribute("inventory", itemService.listAvailable());
	    return "form/stockusage";
	}

	@RequestMapping(params={"removeRow"})
	public String removeRow(Model model, Job job, ItemList itemList, HttpServletRequest req) {
	    Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    itemList.removeProduct(rowId);
	    model.addAttribute("inventory", itemService.listAvailable());
	    return "form/stockusage";
	}	
	
	@RequestMapping(params={"getTransaction"})
	public String  getTransaction(Model model, Job job, ItemList itemList, HttpServletRequest req, RedirectAttributes redirAttr) {
		Long itemId = Long.valueOf(req.getParameter("getTransaction"));
	    redirAttr.addAttribute("itemId", itemId);
	    redirAttr.addAttribute("fromDate", "");
	    redirAttr.addAttribute("toDate", "");
	    return "redirect:/report/usage/output";
	}		
}
