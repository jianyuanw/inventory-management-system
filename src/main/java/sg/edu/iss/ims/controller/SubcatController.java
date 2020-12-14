package sg.edu.iss.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.iss.ims.model.Category;
import sg.edu.iss.ims.model.Subcategory;
import sg.edu.iss.ims.repo.SubcategoryRepository;

@Controller
public class SubcatController {
	
	@Autowired
	private SubcategoryRepository subcatRepo;
	
	@GetMapping("cat")
	public String testCategories(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("subcategories", subcatRepo.findAll());
		return "cat";
	}
	
	@PostMapping("cat")
	public String formOut(Model model, Category category) {
	    model.addAttribute("category", category);
	    return "cat2";
	}
}
