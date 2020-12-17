package sg.edu.iss.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.edu.iss.ims.model.*;
import sg.edu.iss.ims.service.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/stockentry")
public class StockEntryController {

    @Autowired
    private final ReorderService reorderService;
    private final ProductService productService;
    private final ItemService itemservice;
    private final RestockService restockService;


    @Autowired
	public StockEntryController(ReorderServiceImpl reorderImpl, ProductService productService, ItemService itemservice, RestockService restockService) {

        this.reorderService = reorderImpl;
        this.productService = productService;
        this.itemservice = itemservice;
        this.restockService = restockService;
    }

  //
        @GetMapping("/add/{reorderId}")
        public String createStock (Model model, @PathVariable Long reorderId){
	    Reorder reorder = reorderService.findReordersById (reorderId);
		model.addAttribute("reorder", reorder);

		Restock restock = new Restock();
		model.addAttribute("restock",restock);

		return "product/StockEntryForm";
        }

// for testing
    @PostMapping("/created")
    public String restock (Model model, @RequestParam Long reorderId, @RequestParam String receiveddate, RedirectAttributes redirAttr) {
        Reorder reorder = reorderService.findReordersById(reorderId);


        Restock restock = new Restock();

        restock.setReorder(reorder);
        LocalDate receiveddate2 = restockService.parseDate(receiveddate);
        restock.setReceivedDate(receiveddate2);

        restockService.create(restock);
        model.addAttribute("restock",restock);

        redirAttr.addFlashAttribute("alert", new Alert("success", "Restock Placed!"));

        return "redirect:/product/Stocklist";
    }




}
