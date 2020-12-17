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

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report/reorder")
public class ReorderReportController {

    private final ProductService pService;

    public ReorderReportController(ProductServiceImpl pImpl) {
        pService = pImpl;
    }

    @GetMapping("/query")
    public String reorderReportQuery(Model model) {
        List<Product> products = pService.list();
        model.addAttribute("products", products);
        return "report/reorderquery";
    }

    @PostMapping("/processor")
    public String reorderReportProcessor(@RequestParam Long productId, @RequestParam LocalDate fromDate,
                                         @RequestParam LocalDate toDate) {
//        TODO: Logic
        return "";
    }
}
