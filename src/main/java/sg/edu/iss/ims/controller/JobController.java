package sg.edu.iss.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import sg.edu.iss.ims.model.Job;

@Controller
public class JobController {

	public String generateSupplierForm(@ModelAttribute Job job) {
		return "supplierform";
	}
}
