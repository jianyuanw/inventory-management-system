package sg.edu.iss.ims.item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.supplier.SupplierService;
import sg.edu.iss.ims.supplier.SupplierServiceImpl;

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

        String report = rService.generateReport(reorders, supplier, totalPrice);
        model.addAttribute("report", report);

        return "report/reorderoutput";
    }

    @PostMapping("/download")
    public void downloadReport(@RequestParam String report, HttpServletResponse response) throws IOException {
        File reportFile = File.createTempFile("report", ".dat");
        FileWriter writer = new FileWriter(reportFile);
        writer.write(report);
        writer.close();

        InputStream inputStream = new FileInputStream(reportFile);
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=report.dat");
        response.addHeader("Content-Length", String.valueOf(reportFile.length()));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        inputStream.close();
    }
}
