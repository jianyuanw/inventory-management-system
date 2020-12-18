package sg.edu.iss.ims.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.product.ProductService;
import sg.edu.iss.ims.product.ProductServiceImpl;
import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.supplier.SupplierService;
import sg.edu.iss.ims.supplier.SupplierServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report/reorder")
public class ReorderReportController {

    private final SupplierService sService;
    private final ReorderService rService;

    public ReorderReportController(SupplierServiceImpl sImpl, ReorderServiceImpl rImpl) {
        sService = sImpl;
        rService = rImpl;
    }

    @GetMapping("/query")
    public String reorderReportQuery(Model model) {
        List<Supplier> suppliers = sService.list();
        model.addAttribute("suppliers", suppliers);
        return "report/reorderquery";
    }

    @PostMapping("/processor")
    public String reorderReportProcessor(@RequestParam Long supplierId, @RequestParam String fromDate,
                                         @RequestParam String toDate, Model model) {
        LocalDate parsedFromDate = rService.convertToDate(fromDate);
        LocalDate parsedToDate = rService.convertToDate(toDate);
        List<Reorder> reorders = rService.findReordersByDateRange(supplierId, parsedFromDate, parsedToDate);
        Supplier supplier = sService.findSupplierById(supplierId);
        double totalPrice = rService.sumPrice(reorders);
        model.addAttribute("reorders", reorders);
        model.addAttribute("supplier", supplier);
        model.addAttribute("totalPrice", totalPrice);
        return "report/reorderoutput";
    }
}
