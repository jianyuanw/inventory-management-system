package sg.edu.iss.ims.brand;

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
@RequestMapping("/brand")
public class ManageBrandController {
	
	private final BrandService brandService;
	
	public ManageBrandController(BrandServiceImpl brandImp) {
		
		brandService = brandImp;
	}

	
	@GetMapping("/add")
	public String showBrandForm(@ModelAttribute Brand brand) {
		return "manage-brand/create";
	}
	

	@PostMapping("/save")
	public String add(@Valid @ModelAttribute("brand") Brand brand, BindingResult bindingResult, RedirectAttributes redirAttr) {
		if (brandService.findBrandByName(brand.getName()) != null) {
			bindingResult.rejectValue("name", "error.brand", "Brand name already exists");
		}		
		if (bindingResult.hasErrors()) 
		{
			return "manage-brand/create";
		} 
		brandService.createBrand(brand.getName());
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated brand!"));
		return "redirect:/brand/list";
	}
	
	@GetMapping("/list")
	public String showBrandList(Model model) {
		List<Brand> brandlist = brandService.list();
		model.addAttribute("brandlist", brandlist);
		return "manage-brand/modify";
	}
	
	@GetMapping("/edit/{brandid}")
	public String editBrandList(Model model, @PathVariable("brandid") Long id) {
		model.addAttribute("brand", brandService.findBrandById(id));
		return "manage-brand/create";
	}
	
	@GetMapping("/delete/{brandid}")
	public String deleteBrandList(Model model, @PathVariable("brandid") Long id) {
		brandService.deleteBrand(brandService.findBrandById(id));
		return "redirect:/brand/list";
	}
}
