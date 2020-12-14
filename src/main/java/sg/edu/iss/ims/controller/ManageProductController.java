package sg.edu.iss.ims.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.service.ProductService;
import sg.edu.iss.ims.service.ProductServiceImpl;
import sg.edu.iss.ims.service.SupplierService;
import sg.edu.iss.ims.service.SupplierServiceImpl;

@Controller
@RequestMapping("/product")
public class ManageProductController {
	
	private final ProductService prodService;
	private final SupplierService supplierService;
	
	public ManageProductController(ProductServiceImpl supImp, SupplierServiceImpl supplierImpl) {
		supplierService = supplierImpl;
		prodService = supImp;
	}
	
	@GetMapping("/add")
	public String showSupForm(Model model) {
		model.addAttribute("suppliers", supplierService.list());
		model.addAttribute("product", new Product());
		return "productform";
	}
	
	@GetMapping("/save")
	public String add(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model, RedirectAttributes redirAttr) {
//		if (bindingResult.hasErrors()) 
//		{
//			return "supplierform";
//		}
		prodService.saveProduct(product);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated product!"));
		return "forward:/product/list";
	}
	
	@GetMapping("/list")
	public String showsuplist(Model model) {
		List<Product> prodlist = prodService.list();
		model.addAttribute("prodlist", prodlist);
		return "productlist";
	}
	
	@GetMapping("/edit/{prodid}")
	public String editSupList(Model model, @PathVariable("prodid") Long id) {
		model.addAttribute("supplier", prodService.findProductById(id));
		return "productform";
	}
	
	@GetMapping("/delete/{prodid}")
	public String deleteSupList(Model model, @PathVariable("prodid") Long id) {
		prodService.deleteProduct(id);
		return "redirect:/product/list";
	}
}
