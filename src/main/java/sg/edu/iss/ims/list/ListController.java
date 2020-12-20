package sg.edu.iss.ims.list;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.ims.item.Reorder;
import sg.edu.iss.ims.item.ReorderService;
import sg.edu.iss.ims.item.ReorderServiceImpl;

@Controller
@RequestMapping("/list")
public class ListController {
	
	private final ReorderService reorderService;
	
	public ListController(ReorderServiceImpl reorderServiceImpl) {
		reorderService = reorderServiceImpl;
	}
	
	@GetMapping("/reorder")
	public String generateReorderList(Model model) {
		List<Reorder> reorders = reorderService.findAllReorders();
		model.addAttribute("reorders", reorders);
		return "list/reorder";		
	}
	
	@GetMapping("/reorder/{itemId}")
	public String generateReorderListOfItem(Model model, @PathVariable Long itemId) {
		List<Reorder> reorders = reorderService.findAllReordersByItemId(itemId);
		model.addAttribute("reorders", reorders);		
		return "list/reorder";
	}
	
}
