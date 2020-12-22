package sg.edu.iss.ims.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.item.ItemService;
import sg.edu.iss.ims.item.ItemServiceImpl;
import sg.edu.iss.ims.item.Reorder;
import sg.edu.iss.ims.item.ReorderService;
import sg.edu.iss.ims.item.ReorderServiceImpl;
import sg.edu.iss.ims.item.ReorderStatus;
import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;
import sg.edu.iss.ims.transaction.TransactionType;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/form/stockentry")
public class StockEntryController {

    private final ReorderService reorderService;
    private final ItemService itemService;
    private final TransactionService transactionService;

	public StockEntryController(ReorderServiceImpl reorderImpl, ItemServiceImpl itemImpl,
                                TransactionServiceImpl transactionImpl) {
        reorderService = reorderImpl;
        itemService = itemImpl;
        transactionService = transactionImpl;
    }

    @GetMapping
    public String stockEntry(Model model, RedirectAttributes redirAttr) {
    	List<Reorder> reorders = reorderService.findAllByStatus(ReorderStatus.PENDING_DELIVERY);
    	if (reorders.size() == 0) {
    		redirAttr.addFlashAttribute("alert", new Alert("info", "No ongoing reorders to enter stock, please create a reorder first"));
    		return "redirect:/form/stockreorder";
    	}
	    model.addAttribute("reorders", reorders);	    
	    return "form/stockentry";
    }

    @GetMapping("/add/{reorderId}")
    public String createStock (Model model, @PathVariable Long reorderId){
        Reorder reorder = reorderService.findReorderById(reorderId);
        model.addAttribute("reorder", reorder);
        return "form/staticstockentry";
    }

    @PostMapping("/created")
    public String restock (@RequestParam Long reorderId, @RequestParam String deliveryDate,
                           RedirectAttributes redirAttr) {
        Reorder reorder = reorderService.findReorderById(reorderId);
        LocalDate receivedDate = reorderService.convertToDate(deliveryDate);
        if (reorder == null) {
        	redirAttr.addFlashAttribute("alert", new Alert("warning", "Reorder ID does not exist, please try again"));
        	return "redirect:/form/stockentry";
        } else {
        	reorderService.receiveDelivery(reorderId, receivedDate);
        }      

        redirAttr.addFlashAttribute("alert", new Alert("success", "Stock entry form submitted!"));

        return "redirect:/list/reorder";
    }
}
