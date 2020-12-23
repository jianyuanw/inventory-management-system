package sg.edu.iss.ims.category;

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
@RequestMapping("/subcategory")
public class ManageSubcategoryController {
	
	private final CategoryService catService;
	
	public ManageSubcategoryController(CategoryServiceImpl catImp) {
		
		catService = catImp;
	}

	
	@GetMapping("/add")
	public String showCatForm(Model model, @ModelAttribute Subcategory subcategory) {
		model.addAttribute("categories", catService.getCategories());
		return "manage-subcategory/create";
	}
	

	@PostMapping("/save")
	public String add(@Valid @ModelAttribute("subcategory") Subcategory subcategory, BindingResult bindingResult, RedirectAttributes redirAttr, Model model) {
		if (catService.findSubcategoryByNameAndCategory_Id(subcategory.getName(), subcategory.getCategory().getId()) != null) {
			bindingResult.rejectValue("name", "error.subcategory", "Subcategory name already exists");
		}
		model.addAttribute("categories", catService.getCategories());
		if (bindingResult.hasErrors()) 
		{
			return "manage-subcategory/create";
		} 
		catService.saveSubcategory(subcategory);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated subcategory!"));
		return "redirect:/subcategory/list";
	}
	
	@GetMapping("/list")
	public String showsubcatlist(Model model) {
		List<Subcategory> subcatlist = catService.getSubcategories();
		model.addAttribute("subcatlist", subcatlist);
		return "manage-subcategory/modify";
	}
	
	@GetMapping("/edit/{subcatid}")
	public String editsubcatList(Model model, @PathVariable("subcatid") Long id) {
		model.addAttribute("subcategory", catService.findSubcategoryById(id));
		model.addAttribute("categories", catService.getCategories());
		return "manage-subcategory/create";
	}
	
	@GetMapping("/delete/{subcatid}")
	public String deletesubcatList(Model model, @PathVariable("subcatid") Long id) {
		catService.deleteSubcategory(catService.findSubcategoryById(id));
		return "redirect:/subcategory/list";
	}
}
