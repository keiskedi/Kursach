package com.sistersstones.jewelryshop.controller;

import com.sistersstones.jewelryshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{jewelryId}")
    public String addToCart(@PathVariable Long jewelryId, Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            cartService.addToCart(auth.getName(), jewelryId);
        }
        return "redirect:/jewelry/" + jewelryId;
    }

    @GetMapping
    public String viewCart(Authentication auth, Model model) {
        model.addAttribute("cartItems", cartService.getCartForUser(auth.getName()));
        return "cart/view";
    }

    @PostMapping("/remove/{jewelryId}")
    public String removeFromCart(@PathVariable Long jewelryId, Authentication auth) {
        cartService.removeFromCart(auth.getName(), jewelryId);
        return "redirect:/cart";
    }
}
