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
import sg.edu.iss.ims.service.BrandService;
import sg.edu.iss.ims.service.BrandServiceImpl;
import sg.edu.iss.ims.service.CategoryService;
import sg.edu.iss.ims.service.CategoryServiceImpl;
import sg.edu.iss.ims.service.ProductService;
import sg.edu.iss.ims.service.ProductServiceImpl;
import sg.edu.iss.ims.service.SupplierService;
import sg.edu.iss.ims.service.SupplierServiceImpl;

@Controller
@RequestMapping("/product")
public class ManageProductController {
	
	private final ProductService prodService;
	private final SupplierService supplierService;
	private final BrandService brandService;
	private final CategoryService catService;
	
	public ManageProductController(ProductServiceImpl supImp, SupplierServiceImpl supplierImpl, BrandServiceImpl brandImpl, CategoryServiceImpl catImpl) {
		supplierService = supplierImpl;
		prodService = supImp;
		brandService = brandImpl;
		catService = catImpl;
	}
	
	@GetMapping("/add")
	public String showProdForm(Model model) {
		model.addAttribute("suppliers", supplierService.list());
		model.addAttribute("product", new Product());
		model.addAttribute("brands",brandService.list());
		model.addAttribute("categories", catService.getCategories());
		model.addAttribute("subcategories", catService.getSubcategories());
		return "productform";
	}
	
	@GetMapping("/save")
	public String saveProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model, RedirectAttributes redirAttr) {
//		if (bindingResult.hasErrors()) 
//		{
//			return "supplierform";
//		}
		prodService.saveProduct(product);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated product!"));
		return "forward:/product/list";
	}
	
	@GetMapping("/list")
	public String showProdList(Model model) {
		List<Product> prodlist = prodService.list();
		model.addAttribute("prodlist", prodlist);
		return "productlist";
	}
	
	@GetMapping("/edit/{prodid}")
	public String editProdList(Model model, @PathVariable("prodid") Long id) {
		model.addAttribute("product", prodService.findProductById(id));
		model.addAttribute("brands",brandService.list());
		model.addAttribute("categories", catService.getCategories());
		model.addAttribute("subcategories", catService.getSubcategories());
		model.addAttribute("suppliers", supplierService.list());
		return "productform";
	}
	
	@GetMapping("/delete/{prodid}")
	public String deleteProdList(Model model, @PathVariable("prodid") Long id) {
		prodService.deleteProduct(id);
		return "redirect:/product/list";
	}
}
