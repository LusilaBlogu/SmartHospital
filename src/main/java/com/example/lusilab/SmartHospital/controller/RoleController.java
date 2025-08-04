package com.example.lusilab.SmartHospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {

    @GetMapping("/choose-role")
    public String showChooseRolePage(Model model) {
        model.addAttribute("pageTitle", "SmartHospital - Zgjidhni Rolin");

        return "choose-role";
    }

    @GetMapping("/")
    public String redirectToChooseRole() {
        return "redirect:/choose-role";
    }
}