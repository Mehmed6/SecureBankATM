package com.doganmehmet.app.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String dashboard(HttpSession session, Model model)
    {
        var fullName = session.getAttribute("fullName");
        if (fullName != null)
            model.addAttribute("fullName", fullName.toString().toUpperCase());
        else
            model.addAttribute("fullName", "admin".toUpperCase());
        return "dashboard/dashboard";
    }
}
