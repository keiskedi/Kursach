package com.sistersstones.jewelryshop.controller;

import com.sistersstones.jewelryshop.dto.CommentDto;
import com.sistersstones.jewelryshop.model.Jewelry;
import com.sistersstones.jewelryshop.service.CommentService;
import com.sistersstones.jewelryshop.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JewelryController {

    private final JewelryService jewelryService;
    private final CommentService commentService;

    @Autowired
    public JewelryController(JewelryService jewelryService, CommentService commentService) {
        this.jewelryService = jewelryService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String showJewelryPage(@RequestParam(value = "sort", required = false) String sort, Model model) {
        List<Jewelry> jewelryList;

        if ("priceAsc".equals(sort)) {
            jewelryList = jewelryService.getSortedByPriceAsc();
        } else if ("priceDesc".equals(sort)) {
            jewelryList = jewelryService.getSortedByPriceDesc();
        } else {
            jewelryList = jewelryService.getAllJewelry();
        }

        model.addAttribute("jewelryList", jewelryList);
        return "index";
    }

    // Метод GET для деталей прикраси
    @GetMapping("/jewelry/{id}")
    public String showJewelryDetails(@PathVariable Long id, Model model) {
        Jewelry jewelry = jewelryService.getJewelryById(id);
        model.addAttribute("jewelry", jewelry);
        model.addAttribute("comments", commentService.getCommentsForJewelry(id));
        model.addAttribute("comment", new CommentDto());
        return "jewelry/details";
    }

    // Метод POST для додавання коментаря
    @PostMapping("/jewelry/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute("comment") CommentDto commentDto,
                             Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            commentService.addComment(id, commentDto.getText(), auth.getName());
        }
        return "redirect:/jewelry/" + id;
    }
}
