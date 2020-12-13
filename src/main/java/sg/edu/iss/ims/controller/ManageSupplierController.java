package sg.edu.iss.ims.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.Supplier;
import sg.edu.iss.ims.service.SupplierServiceImpl;
import sg.edu.iss.ims.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class ManageSupplierController {
	
	private final SupplierService supService;
	
	public ManageSupplierController(SupplierServiceImpl supImp) {
		
		supService = supImp;
	}
	
	@GetMapping("/add")
	public String showsupform(Model model) {
		model.addAttribute("supplier", new Supplier());
		return "supplierform";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute("supplier") Supplier supplier, BindingResult bindingResult, Model model, RedirectAttributes redirAttr) {
		supService.createSupplier(supplier);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully added supplier!"));
		return "redirect:/supplier/list";
	}
	
	@GetMapping("/list")
	public String showsuplist(Model model) {
		List<Supplier> suplist = supService.list();
		model.addAttribute("suplist", suplist);
		return "supplierlist";
	}
	
	@GetMapping("/edit/{supid}")
	public String editsuplist(Model model, @PathVariable("supid") Long id) {
		model.addAttribute("supplier", supService.updateSupplier(id));
		return "supplierform";
	}
	
	@GetMapping("/delete/{supid}")
	public String deletesuplist(Model model, @PathVariable("supid") Long id) {
		supService.deleteSupplier(id);
		return "redirect:/supplier/list";
	}
}
