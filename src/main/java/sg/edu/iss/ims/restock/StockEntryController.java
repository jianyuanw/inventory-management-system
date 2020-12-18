package sg.edu.iss.ims.restock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.edu.iss.ims.item.*;
import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionService;
import sg.edu.iss.ims.transaction.TransactionServiceImpl;
import sg.edu.iss.ims.transaction.TransactionType;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/stockentry")
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
    public String stockEntry(RedirectAttributes redirAttr) {
	    redirAttr.addFlashAttribute("alert",
                new Alert("primary", "Select a reorder to create stock entry"));
	    return "redirect:/product/reorder/list";
    }

    @GetMapping("/add/{reorderId}")
    public String createStock (Model model, @PathVariable Long reorderId){
        Reorder reorder = reorderService.findReorderById(reorderId);
        model.addAttribute("reorder", reorder);
        return "product/StockEntryForm";
    }

    @PostMapping("/created")
    public String restock (@RequestParam Long reorderId, @RequestParam String deliveryDate,
                           @RequestParam int reorderQty, RedirectAttributes redirAttr) {
        Reorder reorder = reorderService.findReorderById(reorderId);
        LocalDate receivedDate = reorderService.convertToDate(deliveryDate);
        reorder.setReceivedDate(receivedDate);
        reorder.setStatus(ReorderStatus.DELIVERED);

        Item item = reorder.getItem();
        reorderService.updateItemQty(item, reorderQty);
        reorderService.updateItemState(item);
        itemService.update(item);

        Transaction transaction = new Transaction(item, reorderQty, TransactionType.RECEIVE_STOCK);
        transactionService.save(transaction);

        redirAttr.addFlashAttribute("alert",
                new Alert("success", "Stock entry form submitted!"));

        return "redirect:/stockentry/list";
    }

    @GetMapping("/list")
    public String stockEntryList(Model model) {
        List<Reorder> deliveredReorders = reorderService.findDeliveredOrders();
        model.addAttribute("stockEntries", deliveredReorders);
	    return "product/StockList";
    }
}
