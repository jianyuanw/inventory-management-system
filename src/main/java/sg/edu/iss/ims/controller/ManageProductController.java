package sg.edu.iss.ims.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.*;
import sg.edu.iss.ims.service.*;

@Controller
@RequestMapping("/product")
public class ManageProductController {
	
	private final ProductService prodService;
	private final SupplierService supplierService;
	private final ItemService itemService;
	private final ReorderService reorderService;
	
	public ManageProductController(ProductServiceImpl supImp, SupplierServiceImpl supplierImpl,
								   ItemServiceImpl itemImpl, ReorderServiceImpl reorderImpl) {
		supplierService = supplierImpl;
		prodService = supImp;
		itemService = itemImpl;
		reorderService = reorderImpl;
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
		return "supplierlist";
	}
	
	@GetMapping("/edit/{prodid}")
	public String editSupList(Model model, @PathVariable("prodid") Long id) {
		model.addAttribute("supplier", prodService.findProductById(id));
		return "supplierform";
	}
	
	@GetMapping("/delete/{prodid}")
	public String deleteSupList(Model model, @PathVariable("prodid") Long id) {
		prodService.deleteProduct(id);
		return "redirect:/supplier/list";
	}

	@GetMapping("/reorder/{prodId}")
	public String reorder(Model model, @PathVariable Long prodId) {
		Product product = prodService.findProductById(prodId);
		Item item = itemService.findItemByProduct(product);
		model.addAttribute("product", product);
		model.addAttribute("item", item);
		return "product/reorderform";
	}

	@PostMapping("/reorder")
	public String reorder(@RequestParam Long itemId, @RequestParam int qtyToReorder, RedirectAttributes redirAttr) {
		Item item = itemService.findItemById(itemId);
		Reorder reorder = new Reorder();
		reorder.setQuantity(qtyToReorder);
		reorder.setDate(LocalDate.now());
		reorder.setStatus(ReorderStatus.PENDING_DELIVERY);
		reorder.setItem(item);
		reorderService.save(reorder);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Reorder Placed!"));
		return "redirect:/product/reorder/list";
	}

	@GetMapping("/reorder/list")
	public String reorderList(Model model) {
		List<Reorder> reorders = reorderService.findAllReorders();
		model.addAttribute("reorders", reorders);
		return "product/reorderlist";
	}
}
