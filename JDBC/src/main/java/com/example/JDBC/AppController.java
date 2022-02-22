package com.example.JDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private SaleDAO dao;

    @RequestMapping("/")
    public String viewHomePage(Model model){
        List<Sale> listSale = dao.list();
        model.addAttribute("listSale",listSale);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewForm(Model model){
        Sale sale = new Sale();
        model.addAttribute("sale",sale);
        return "new_form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("sale") Sale sale){
        dao.save(sale);
        return "redirect:/";
    }
}
