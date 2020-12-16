package sg.edu.iss.ims.product;

import java.time.LocalDate;
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
	
	public ManageProductController(ProductServiceImpl supImp, SupplierServiceImpl supplierImpl,
								   ItemServiceImpl itemImpl, ReorderServiceImpl reorderImpl,
								   BrandServiceImpl brandImpl, CategoryServiceImpl catImpl) {
		supplierService = supplierImpl;
		prodService = supImp;
		itemService = itemImpl;
		reorderService = reorderImpl;
		brandService = brandImpl;
		catService = catImpl;
	}
	
	@GetMapping("/add")
	public String showProdForm(Model model) {
		model.addAttribute("suppliers", supplierService.list());
		model.addAttribute("product", new Product());
		model.addAttribute("brands", brandService.list());
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