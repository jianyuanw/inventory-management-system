package sg.edu.iss.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.service.ViewProductInterface;

import java.util.List;


@Controller
@RequestMapping("/ViewProduct")
public class ViewProductController {

@Autowired
public void setViewProduct(ViewProductInterface view) {
    this.viewservice = view;
}

@Autowired
private ViewProductInterface viewservice;

@RequestMapping (value ="/list")
    public String list (Model model)
{
    List<Product>prod=viewservice.list();
    model.addAttribute("prod",prod);
    return "ViewProduct";
}




}
