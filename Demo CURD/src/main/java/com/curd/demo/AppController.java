package com.curd.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private ItemService service;

    @RequestMapping("/")
    public String viewHomePage(Model model){
        List<Item> listItem = service.listAll();
        model.addAttribute("listItem",listItem);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewItemForm(Model model){
        Item item = new Item();
        model.addAttribute("item",item);
        return "new_item";
    }

    @RequestMapping(value = "/save" , method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") Item item){
        service.save(item);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditItemForm(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("edit_item");

        Item item = service.get(id);
        mav.addObject("item",item);

        return mav;
    }
}
