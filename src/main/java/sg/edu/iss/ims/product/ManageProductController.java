package sg.edu.iss.ims.product;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.brand.BrandService;
import sg.edu.iss.ims.brand.BrandServiceImpl;
import sg.edu.iss.ims.category.CategoryService;
import sg.edu.iss.ims.category.CategoryServiceImpl;
import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemService;
import sg.edu.iss.ims.item.ItemServiceImpl;
import sg.edu.iss.ims.item.ItemState;
import sg.edu.iss.ims.item.Reorder;
import sg.edu.iss.ims.item.ReorderService;
import sg.edu.iss.ims.item.ReorderServiceImpl;
import sg.edu.iss.ims.item.ReorderStatus;
import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.supplier.SupplierService;
import sg.edu.iss.ims.supplier.SupplierServiceImpl;

@Controller
@RequestMapping("/product")
public class ManageProductController {
	
	private final ProductService prodService;
	private final SupplierService supplierService;
	private final ItemService itemService;
	private final ReorderService reorderService;
	private final BrandService brandService;
	private final CategoryService catService;
	
	public ManageProductController(ProductServiceImpl productImpl, SupplierServiceImpl supplierImpl,
								   ItemServiceImpl itemImpl, ReorderServiceImpl reorderImpl,
								   BrandServiceImpl brandImpl, CategoryServiceImpl catImpl) {
		supplierService = supplierImpl;
		prodService = productImpl;
		itemService = itemImpl;
		reorderService = reorderImpl;
		brandService = brandImpl;
		catService = catImpl;
	}
	
	@GetMapping("/add")
	public String showProdForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("item", new Item());
		model.addAttribute("errors", new HashMap<String, String>());
		model.addAttribute("suppliers", supplierService.list());
		model.addAttribute("brands", brandService.list());
		model.addAttribute("categories", catService.getCategories());
		model.addAttribute("subcategories", catService.getSubcategories());
		return "manage-product/create";
	}
	
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute @Valid Product product, BindingResult productBinding, @ModelAttribute @Valid Item item, BindingResult itemBinding,
							  String newSupplier, String newBrand, String newCategory, String newSubcategory, 
							  Model model, RedirectAttributes redirAttr) {
		HashMap<String, String> errors = prodService.validate(newSupplier, newBrand, newCategory, newSubcategory);
		
		model.addAttribute("errors", errors);
		model.addAttribute("suppliers", supplierService.list());
		model.addAttribute("brands", brandService.list());
		model.addAttribute("categories", catService.getCategories());
		model.addAttribute("subcategories", catService.getSubcategories());
		
		if (productBinding.hasErrors() || itemBinding.hasErrors() || ! errors.isEmpty())
		{
			return "manage-product/create";
		} else {
			prodService.createDynamicProduct(product, item, newSupplier, newBrand, newCategory, newSubcategory);
			
			redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated product!"));
			return "redirect:/product/list";
		}

	}
	
	@GetMapping("/list")
	public String showProdList(Model model) {
		List<Product> prodlist = prodService.list();
		model.addAttribute("prodlist", prodlist);
		return "manage-product/modify";
	}
	
	@GetMapping("/edit/{prodid}")
	public String editProdList(Model model, @PathVariable("prodid") Long id) {
		model.addAttribute("product", prodService.findProductById(id));
		model.addAttribute("item",itemService.findItemByProduct(prodService.findProductById(id)));
		model.addAttribute("brands",brandService.list());
		model.addAttribute("categories", catService.getCategories());
		model.addAttribute("subcategories", catService.getSubcategories());
		model.addAttribute("suppliers", supplierService.list());
		model.addAttribute("errors", new HashMap<String, String>());
		return "manage-product/create";
	}

	@GetMapping("/delete/{prodid}")
	public String deleteProdList(Model model, @PathVariable("prodid") Long id, RedirectAttributes redirAttr) {
		prodService.deleteProduct(id);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Product successfully deleted!"));
		return "redirect:/product/list";
	}

	@GetMapping("/reorder/{prodId}")
	public String reorder(Model model, @PathVariable Long prodId) {
		Product product = prodService.findProductById(prodId);
		Item item = itemService.findItemByProduct(product);
		model.addAttribute("product", product);
		model.addAttribute("item", item);
		return "form/staticstockreorder";
	}

	@PostMapping("/reorder")
	public String reorder(@RequestParam Long itemId, @RequestParam int qtyToReorder, RedirectAttributes redirAttr) {
		Item item = itemService.findItemById(itemId);

		if (item.getState() == ItemState.REORDER_PLACED) {
			redirAttr.addFlashAttribute("alert",
					new Alert("info", "Active reorder exists. Only one active reorder allowed."));
			return "redirect:/product/reorder/" + item.getProduct().getId();
		} else if (qtyToReorder < item.getReorderQuantity()) {
			redirAttr.addFlashAttribute("alert",
					new Alert("warning", "Need to order at least the reorder quantity amount of " + item.getReorderQuantity()));
			return "redirect:/product/reorder/" + item.getProduct().getId();
		}

		Reorder reorder = new Reorder();
		
		reorder.setQuantity(qtyToReorder);
		reorder.setItem(item);
		
		reorderService.createDelivery(reorder);

		redirAttr.addFlashAttribute("alert", new Alert("success", "Reorder Placed!"));

		return "redirect:/list/reorder";
	}
	
	@GetMapping("/reorder/cancel/{reorderId}")
	public String cancelReorder(@PathVariable Long reorderId, RedirectAttributes redirAttr) {
		reorderService.cancelReorder(reorderId);
		
		redirAttr.addFlashAttribute("alert", new Alert("success", "Reorder Cancelled!"));
		return "redirect:/";
	}
	
	@GetMapping("/reorder/getactive/{itemId}/{redirectTo}")
	public String getActiveReorder(@PathVariable Long itemId, @PathVariable String redirectTo) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("stockentry", "/form/stockentry/add/");
		map.put("cancel", "/product/reorder/cancel/");
		List<Reorder> reorders = reorderService.findAllReordersByItemIdAndStatus(itemId, ReorderStatus.PENDING_DELIVERY);
			
		return "redirect:" + map.get(redirectTo) + reorders.get(0).getId();
		
	}
}
