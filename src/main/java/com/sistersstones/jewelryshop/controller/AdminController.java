package com.sistersstones.jewelryshop.controller;

import com.sistersstones.jewelryshop.model.Image;
import com.sistersstones.jewelryshop.model.Jewelry;
import com.sistersstones.jewelryshop.repository.CartRepository;
import com.sistersstones.jewelryshop.repository.ImageRepository;
import com.sistersstones.jewelryshop.repository.JewelryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private JewelryRepository jewelryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CartRepository cartRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    // 🟢 Форма додавання прикраси
    @GetMapping("/admin/add-jewelry")
    public String showAddJewelryForm(Model model) {
        model.addAttribute("jewelry", new Jewelry());
        return "admin/add-jewelry";
    }

    // 🟢 Збереження прикраси з фото
    @PostMapping("/admin/add-jewelry")
    public String addJewelry(@ModelAttribute Jewelry jewelry,
                              @RequestParam("imageFiles") MultipartFile[] imageFiles) throws IOException {

        List<Image> images = new ArrayList<>();
        boolean firstImageSet = false;

        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, imageFile.getBytes());

                Image image = new Image();
                image.setFileName(fileName);
                image.setJewelry(jewelry);
                image.setUrl("/images/" + fileName);
                images.add(image);

                if (!firstImageSet) {
                    jewelry.setImageUrl("/images/" + fileName);
                    firstImageSet = true;
                }
            }
        }

        jewelry.setImages(images);
        jewelryRepository.save(jewelry);
        imageRepository.saveAll(images);

        return "redirect:/";
    }

    // 🔴 Видалення прикраси
    @Transactional
    @GetMapping("/admin/delete-jewelry/{id}")
    public String deleteJewelry(@PathVariable Long id) {
        Jewelry jewelry = jewelryRepository.findById(id).orElse(null);
        if (jewelry != null) {

            // 🧹 Видаляємо з cart, якщо ця прикраса є у кошику
            cartRepository.deleteByJewelryId(jewelry.getId());

            // 🧹 Видаляємо зображення з диску
            for (Image image : jewelry.getImages()) {
                try {
                    Path imagePath = Paths.get("src/main/resources/static" + image.getUrl());
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 🧹 Видаляємо прикрасу з бази (cascade видалить зображення)
            jewelryRepository.delete(jewelry);
        }

        return "redirect:/";
    }
}
