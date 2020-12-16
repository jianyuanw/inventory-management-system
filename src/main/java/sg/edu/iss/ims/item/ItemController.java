package sg.edu.iss.ims.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.ims.model.Alert;
import sg.edu.iss.ims.product.Product;

import java.util.List;

@Controller
@RequestMapping("/Stock_Entry")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    public void setItem(ItemService item) {
        this.itemService = item;
    }

    // Add Item
    @GetMapping("/add")
    public String showsItemform (Model model) {
        model.addAttribute("item", new Item());
        return "itemform";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("item") Item item, BindingResult bindingResult, Model model, RedirectAttributes redirAttr) {
        itemService.addItem(item);
        redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully added item!"));
        return "redirect:/Stock_Entry/list";
    }

    //Remove Item
    @GetMapping("/delete/{itemid}")
    public String deletesuplist(Model model, @PathVariable("itemid") Long id, RedirectAttributes redirAttr) {
        itemService.deleteItem(id);
        redirAttr.addFlashAttribute("alert", new Alert("success", "Successfully removed item!"));
        return "redirect:/Stock_Entry/list";
    }

    //View Item
    @RequestMapping (value ="/list")
    public String list (Model model)
    {
        List<Item> item =itemService.list();
        model.addAttribute("item",item);
        return "Stock_Entry";
    }

}
