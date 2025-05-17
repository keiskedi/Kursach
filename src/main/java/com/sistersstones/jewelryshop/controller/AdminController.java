package com.sistersstones.jewelryshop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Сторінка входу як адміністратор (відкривається через /admin/login)
    @GetMapping("/login")
    public String showAdminLogin() {
        return "admin/login";  // login.html у папці templates/admin/
    }

    // Панель адміністратора (відкривається після входу)
    @GetMapping("/panel")
    public String showAdminPanel(Authentication auth, Model model) {
        model.addAttribute("adminName", auth.getName());
        return "admin/panel";  // панель керування
    }
}
