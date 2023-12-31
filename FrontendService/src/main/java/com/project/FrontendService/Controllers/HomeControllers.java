package com.project.FrontendService.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ekart")
public class HomeControllers {

    @GetMapping("/addStocks")
    public String stockAdd() {
        return "addStock";
    }

    @GetMapping("/reduceStocks")
    public String stockRemove() {
        return "removeStock";
    }

    @GetMapping("/showStocks")
    public String showStock() {
        return "showStock";
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

}
