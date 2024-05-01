package com.shop.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ex")
public class exController {

    @GetMapping(value = "/ex01")
    public String ex01(Model model){
        model.addAttribute("data", "타임리프");

        return "ex/ex01";
    }
}
