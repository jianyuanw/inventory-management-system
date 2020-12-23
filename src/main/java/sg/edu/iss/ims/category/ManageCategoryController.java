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
@RequestMapping("/category")
public class ManageCategoryController {
	
	private final CategoryService catService;
	
	public ManageCategoryController(CategoryServiceImpl catImp) {
		
		catService = catImp;
	}

	
	@GetMapping("/add")
	public String showCatForm(@ModelAttribute Category category) {
		return "manage-category/create";
	}
	

	@PostMapping("/save")
	public String add(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirAttr) {
		if (catService.findCategoryByName(category.getName()) != null) {
			bindingResult.rejectValue("name", "error.category", "Category name already exists");
		}
		if (bindingResult.hasErrors()) 
		{
			return "manage-category/create";
		} 
		catService.saveCategory(category);
		redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully updated category!"));
		return "redirect:/category/list";
	}
	
	@GetMapping("/list")
	public String showcatlist(Model model) {
		List<Category> catlist = catService.getCategories();
		model.addAttribute("catlist", catlist);
		return "manage-category/modify";
	}
	
	@GetMapping("/edit/{catid}")
	public String editcatList(Model model, @PathVariable("catid") Long id) {
		model.addAttribute("category", catService.findCategoryById(id));
		return "manage-category/create";
	}
	
	@GetMapping("/delete/{catid}")
	public String deletecatList(Model model, @PathVariable("catid") Long id) {
		catService.deleteCategory(catService.findCategoryById(id));
		return "redirect:/category/list";
	}
}
