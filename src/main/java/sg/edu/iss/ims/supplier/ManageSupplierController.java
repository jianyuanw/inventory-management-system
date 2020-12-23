package sg.edu.iss.ims.supplier;

import java.util.List;

import javax.validation.Valid;

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

@Controller
@RequestMapping("/supplier")
public class ManageSupplierController {
	
	private final SupplierService supService;
	
	public ManageSupplierController(SupplierServiceImpl supImp) {
		
		supService = supImp;
	}

	
	@GetMapping("/add")
	public String showSupForm(@ModelAttribute Supplier supplier) {
		return "manage-supplier/create";
	}
	

	@PostMapping("/save")
	public String add(@Valid @ModelAttribute("supplier") Supplier supplier, BindingResult bindingResult, RedirectAttributes redirAttr) {
		if (supService.findSupplierByName(supplier.getName()) != null) {
			bindingResult.rejectValue("name", "error.supplier", "Supplier name already exists");
		}
		if (bindingResult.hasErrors()) 
		{
			return "manage-supplier/create";
		} 
		supService.saveSupplier(supplier);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated supplier!"));
		return "redirect:/supplier/list";
	}
	
	@GetMapping("/list")
	public String showsuplist(Model model) {
		List<Supplier> suplist = supService.list();
		model.addAttribute("suplist", suplist);
		return "manage-supplier/modify";
	}
	
	@GetMapping("/edit/{supid}")
	public String editSupList(Model model, @PathVariable("supid") Long id) {
		model.addAttribute("supplier", supService.findSupplierById(id));
		return "manage-supplier/create";
	}
	
	@GetMapping("/delete/{supid}")
	public String deleteSupList(Model model, @PathVariable("supid") Long id) {
		supService.deleteSupplier(id);
		return "redirect:/supplier/list";
	}
}
