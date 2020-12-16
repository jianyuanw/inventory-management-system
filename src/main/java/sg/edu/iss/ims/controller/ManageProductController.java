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
		reorderService.create(reorder);

		item.setState(ItemState.REORDER_PLACED);
		itemService.update(item);

		redirAttr.addFlashAttribute("alert", new Alert("success", "Reorder Placed!"));

		return "redirect:/product/reorder/list";
	}

	@GetMapping("/reorder/list")
	public String reorderList(Model model) {
//		List<Reorder> undeliveredReorders = reorderService.findUndeliveredReorders();
//		List<Reorder> deliveredReorders = reorderService.findDeliveredOrders();
//		model.addAttribute("undeliveredReorders", undeliveredReorders);
//		model.addAttribute("deliveredReorders", deliveredReorders);
		List<Reorder> reorders = reorderService.findAllReorders();
		model.addAttribute("reorders", reorders);
		return "product/reorderlist";
	}
}
